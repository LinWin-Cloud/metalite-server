package MetaLiteEngine;


import java.util.Map;

public class DeleteData {
    public void deleteData(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String data = code.substring("delete data ".length() , code.lastIndexOf("from ")).trim();
        String db = code.substring(code.indexOf("from ")+5).trim();

        Map<String , Object> objectMap = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (objectMap.containsKey(data)) {
            objectMap.remove(data);
            metaLiteEngine.writeHashMapToFile(objectMap,metaLiteEngine.select_dir+"/"+db+".mdb");
            metaLiteEngine.RunMessage = "delete data successful";
        }else {
            throw new Exception("no data: "+data);
        }
    }
}
