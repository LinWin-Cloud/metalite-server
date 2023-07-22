package MetaLiteEngine;

import java.io.File;

public class CreateDatabase {
    public void createDatabase(String code ,MetaLiteEngine metaLiteEngine) throws Exception
    {
        String db = code.substring(code.indexOf("create database ")+"create database ".length());

        File create = new File(metaLiteEngine.select_dir+"/"+db+".mdb");
        if (create.isFile()) {
            throw new Exception("target database was exists");
        }
        else {
            if (create.createNewFile()) {
                metaLiteEngine.RunMessage = "create successful";
            }else {
                metaLiteEngine.RunMessage = "create error";
            }
        }
    }
}
