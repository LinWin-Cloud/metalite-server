package MetaLiteEngine;

import java.util.HashMap;
import java.util.Map;

public class CreateKey {
    public void createKey(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String key_value = code.substring("create key ".length() , code.lastIndexOf("to ")).trim();
        String db_all = code.substring(code.indexOf("to ")+3).trim();

        String database = db_all.substring(0,db_all.indexOf("."));
        String data = db_all.substring(db_all.indexOf(".")+1);

        Map<String , Object> objectHashMap = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+database+".mdb");
        if (objectHashMap.containsKey(data)) {
            Map<String , Object> map = (Map<String, Object>) objectHashMap.get(data);

            String replacedStr = key_value.replaceAll("\"([^\"]*)\"", "REPLACED");

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
            map.putAll(hashMap);
            metaLiteEngine.writeHashMapToFile(map , metaLiteEngine.select_dir+"/"+database+".mdb");
            metaLiteEngine.RunMessage = "create key successful";
        }
        else {
            throw new Exception("no data: "+data);
        }
    }
}
