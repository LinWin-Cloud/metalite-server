package MetaLiteEngine;

import main.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CreateDatabase {
    public void createDatabase(String code ,MetaLiteEngine metaLiteEngine) throws Exception
    {
        String db = code.substring(code.indexOf("create database ")+"create database ".length()).replace(" ","");

        File create = new File(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (create.isFile()) {
            throw new Exception("target database was exists");
        }
        else {
            if (create.createNewFile()) {
                Map<String , Object> map = new HashMap<>();
                metaLiteEngine.writeHashMapToFile(map , create.getAbsolutePath());
                metaLiteEngine.RunMessage = "create successful";
            }else {
                metaLiteEngine.RunMessage = "create error";
            }
        }
    }
}
