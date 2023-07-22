package MetaLiteEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Insert {
    public void insert(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String deal = code.substring("insert into ".length());
        String[] split = deal.split(" ");

        String intoDB = split[0].trim();
        String intoData = split[1].trim();

        String head = "insert into "+split[0]+" "+split[1]+" ";
        String set = code.substring(head.length());

        String replacedStr = set.replaceAll("\"([^\"]*)\"", "REPLACED");

        // 按逗号分割字符串
        String[] parts = replacedStr.split(",");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].replaceAll("REPLACED", ",");
        }

        HashMap<String , Object> hashMap = new HashMap<>();

        for (String keyValuePair : parts) {
            String[] part = keyValuePair.split("=");
            String key = part[0].trim();
            String value = part[1].trim();
            hashMap.put(key, value);
        }
        Map<String , Object> objectHashMap = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+intoDB+".mdb");
        if (objectHashMap.containsKey(intoData)) {
            throw new Exception("the data was exists");
        }
        objectHashMap.put(intoData , hashMap);
        metaLiteEngine.writeHashMapToFile(objectHashMap , metaLiteEngine.select_dir+"/"+intoDB+".mdb");
        metaLiteEngine.RunMessage = "insert data successful";
    }
}
