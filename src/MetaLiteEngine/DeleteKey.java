package MetaLiteEngine;

import java.util.Map;

public class DeleteKey {
    public void deleteKey(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String search = code.substring("delete key ".length() , code.lastIndexOf("from ")).trim();
        String db = code.substring(code.lastIndexOf("from ")+5).trim();

        String data = search.substring(0,search.indexOf("."));
        String key = search.substring(search.indexOf(".")+1);

        Map<String , Object> map = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (map.containsKey(data)) {
            Map<String , Object> objectMap = (Map<String, Object>) map.get(data);
            objectMap.remove(key);
            metaLiteEngine.writeHashMapToFile(objectMap ,metaLiteEngine.select_dir+"/"+db+".mdb" );
            metaLiteEngine.RunMessage = "delete key successful";
        }
        else {
            throw new Exception("no data: "+data);
        }
    }
}
