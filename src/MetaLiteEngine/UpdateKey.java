package MetaLiteEngine;

import java.util.Map;

public class UpdateKey {
    public void updateKey(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String search = code.substring("update key ".length() , code.lastIndexOf("from ")).trim();
        String db = code.substring(code.lastIndexOf("from ")+5).trim();

        String data = search.substring(0,search.indexOf("."));
        String key = search.substring(search.indexOf(".")+1);

        String value = key.substring(key.indexOf("=")+1);

        Map<String , Object> map = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (map.containsKey(data)) {
            Map<String , Object> objectMap = (Map<String, Object>) map.get(data);

            if (objectMap.containsKey(key)) {
                objectMap.put(key , value);
                metaLiteEngine.writeHashMapToFile(objectMap ,metaLiteEngine.select_dir+"/"+db+".mdb" );
                metaLiteEngine.RunMessage = "update key successful";
            }
            else {
                throw new Exception("no key: "+key);
            }
        }
        else {
            throw new Exception("no data: "+data);
        }
    }
}
