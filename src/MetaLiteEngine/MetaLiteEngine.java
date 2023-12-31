package MetaLiteEngine;

import main.Main;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MetaLiteEngine {
    private String username;
    private String password;
    public String select_dir;
    public String RunMessage = "";
    public void setUserName(String userName) {
        this.username = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean login() {
        File file = new File(Main.database_dir+username+"/"+password);
        select_dir = Main.database_dir+username+"/"+password+"/";
        return file.isDirectory();
    }
    public void exec(String code) throws Exception {
        code = code.trim();

        String[] token = code.split(" ");

        if (code.isEmpty()) {
            throw new Exception("message empty");
        }
        else if (token[0].equals("select")) {
            // select [""] from main
            new SelectDB().select(code , this);
        }
        else if (code.startsWith("create database ")) {
            AllowWriteData();
            // create database hello
            new CreateDatabase().createDatabase(code , this);
        }
        else if (code.startsWith("create key")) {
            AllowWriteData();
            // create key a=1,b=2,c=3 to db.test_value
            new CreateKey().createKey(code , this);
        }
        else if (code.startsWith("insert into ")) {
            AllowWriteData();
            // insert into hello test_value a=1,b=2,c="hello world"
            new Insert().insert(code , this);
        }
        else if (code.startsWith("list ")) {
            String db = code.substring(5);
            Map<String, Object> hashMap = readHashMapFromFile(this.select_dir+"/"+db+".mdb");
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : hashMap.keySet()) {
                stringBuilder.append(key);
                stringBuilder.append("\n");
            }
            this.RunMessage = stringBuilder.toString();
        }
        else if (code.equals("show databases")) {
            File[] listDir = new File(this.select_dir).listFiles();
            StringBuilder stringBuilder = new StringBuilder();
            for (File file : listDir) {
                String name = this.getLastName(file.getName());
                if (name.equals(".mdb")) {
                    stringBuilder.append(file.getName().substring(0,file.getName().lastIndexOf(".")));
                    stringBuilder.append("\n");
                }
            }
            this.RunMessage = stringBuilder.toString();
        }
        else if (code.startsWith("drop ")) {
            File target = new File(this.select_dir+"/"+code.substring(5)+".mdb");
            if (target.delete()) {
                this.RunMessage = "delete database successful";
            }else {
                this.RunMessage = "delete database error";
            }
        }
        else if (code.startsWith("show ")) {
            //show student from db
            new Show().show(code,this);
        }
        else if (code.startsWith("get ")) {
            // get student.age from db
            new Get().get(code,this);
        }
        else if (code.startsWith("delete data ")) {
            // delete data a from db
            new DeleteData().deleteData(code , this);
        }
        else if (code.startsWith("delete key ")) {
            // delete key test_value.key1 from db
            new DeleteKey().deleteKey(code , this);
        }
        else if (code.startsWith("update key ")) {
            // update key test_value.key1=114514 from db
            AllowWriteData();
            new UpdateKey().updateKey(code , this);
        }
        else if (code.startsWith("index ")) {
            // index db
            String indexDB = code.substring(6).trim();
            StringBuilder stringBuilder = new StringBuilder();

            for (File file : Objects.requireNonNull(new File(this.select_dir).listFiles()))
            {
                if (this.getLastName(file.getName()).equals(".mdb")
                && this.getName(file.getName()).contains(indexDB)) {
                    stringBuilder.append(this.getName(file.getName()));
                    stringBuilder.append("\n");
                    continue;
                }
            }
            this.RunMessage = stringBuilder.toString();
        }
        else {
            throw new Exception("script error");
        }
    }
    public void AllowWriteData() throws Exception {
        try {
            Map<String,Object> hashMap = readJMap(this.select_dir+"/../info.jmap");
            int allow_size = (int) hashMap.get("max_size");
            int database_size = (int) calculateFolderSize(new File(this.select_dir));

            if (database_size >= allow_size) {
                throw new Exception("can not write more data to the database");
            }
        }catch (Exception exception) {
            //exception.printStackTrace();
            if (exception.getMessage().equals("can not write more data to the database"))
            {
                throw new Exception("can not write more data to the database");
            }else {
                throw new Exception("user config error");
            }
        }
    }

    public String getRunMessage() {
        return this.RunMessage;
    }

    public String getLastName(String name) {
        try {
            return name.substring(name.lastIndexOf("."));
        }catch (Exception exception) {
            return name;
        }
    }

    public String getName(String name)  {
        try {
            return name.substring(0,name.lastIndexOf("."));
        }catch (Exception exception) {
            return name;
        }
    }

    // 写入HashMap到文件
    public void writeHashMapToFile(Map<String, Object> hashMap, String filename) throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(hashMap);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new Exception(e.getMessage());
            //e.printStackTrace();
        }
    }

    // 从文件中读取HashMap
    public synchronized Map<String, Object> readHashMapFromFile(String filename) throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            hashMap = (Map<String, Object>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            throw new Exception(e.getMessage());
            //e.printStackTrace();
        }
        return hashMap;
    }

    public static long calculateFolderSize(File folder) {
        long size = 0;
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += calculateFolderSize(file);
                    }
                }
            }
        } else {
            size = folder.length();
        }
        return size;
    }

    private synchronized static Map<String, Object> readJMap(String filename) {
        Map<String, Object> hashMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    hashMap.put(key, parseValue(value));
                }
            }
            //System.out.println("HashMap has been read from file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
    private static Object parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                return value;
            }
        }
    }
}
