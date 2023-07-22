package MetaLiteEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SelectDB {
    public void select(String code , MetaLiteEngine metaLiteEngine) throws Exception {
        String regex = Pattern.quote(code.substring(code.indexOf(" ")+1,code.lastIndexOf("from ")).trim());
        String db = code.substring(code.indexOf("from ")+5);

        File target = new File(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (target.isFile()) {
            Map<String , Object> objectsHashMap = metaLiteEngine.readHashMapFromFile(target.getAbsolutePath());
            StringBuilder stringBuffer = new StringBuilder();
            for (Map.Entry<String, Object> entry : objectsHashMap.entrySet()) {
                String key = entry.getKey();

                // 匹配键
                if (key.matches(regex)) {
                    stringBuffer.append(key);
                }
            }
            metaLiteEngine.RunMessage = stringBuffer.toString();
        }else {
            throw new RuntimeException("can not connect to target database");
        }
    }
}
