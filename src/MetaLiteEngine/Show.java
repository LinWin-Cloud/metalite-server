package MetaLiteEngine;

import java.util.HashMap;
import java.util.Map;

public class Show {
    public void show(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String search = code.split(" ")[1].trim();
        String db = code.substring(code.lastIndexOf("from ")+5);

        Map<String , Object> map = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (map.containsKey(search)) {
            Map<String , Object> dataMap = (Map<String, Object>) map.get(search);
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : dataMap.keySet())
            {
                stringBuilder.append(key);
                stringBuilder.append("\n");
            }
            metaLiteEngine.RunMessage = stringBuilder.toString();
        }else {
            throw new Exception("no value: "+search);
        }
    }
}
