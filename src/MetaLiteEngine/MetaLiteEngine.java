package MetaLiteEngine;

import main.Main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
            new CreateDatabase().createDatabase(code , this);
        }
        else {
            this.RunMessage = "script error";
        }
    }

    public String getRunMessage() {
        return this.RunMessage;
    }


    // 写入HashMap到文件
    public void writeHashMapToFile(Map<String, Object> hashMap, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(hashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中读取HashMap
    public Map<String, Object> readHashMapFromFile(String filename) {
        Map<String, Object> hashMap = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            hashMap = (Map<String, Object>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
