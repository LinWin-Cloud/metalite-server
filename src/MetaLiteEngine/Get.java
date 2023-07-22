package MetaLiteEngine;

import java.util.Map;

public class Get {
    public void get(String code , MetaLiteEngine metaLiteEngine) throws Exception
    {
        String search = code.split(" ")[1].trim();
        String db = code.substring(code.lastIndexOf("from ")+5);

        String data = search.substring(0,search.indexOf("."));
        String value = search.substring(search.indexOf(".")+1);

        Map<String , Object> map = metaLiteEngine.readHashMapFromFile(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (map.containsKey(data)) {
            Map<String , Object> valueMap = (Map<String, Object>) map.get(data);
            if (valueMap.containsKey(value)) {
                metaLiteEngine.RunMessage = valueMap.get(value).toString();
            }
            else {
                throw new Exception("no value: "+data+"."+value);
            }
        }
        else {
            throw new Exception("no value: "+data);
        }
    }
}
