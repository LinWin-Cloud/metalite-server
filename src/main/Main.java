package main;

import MetaLiteEngine.MetaLiteEngine;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Map<String , Object> config;
    public static void main(String[] args) {
        try {
            /*
            HashMap<String , Object> hashMap = new HashMap<>();
            hashMap.put("port",8080);
            hashMap.put("name","metalite/1.7");
            */

            config = readHashMapFromFile("../config/service.jmap");
            System.out.println(config.toString());

            int port = (int) config.get("port");
            HttpServer server = HttpServer.create(new InetSocketAddress(String.valueOf(config.get("host")), port ), 0);

            // 创建处理请求的HttpHandler
            HttpHandler handler = new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    // 处理请求

                    String getRequestsUrl = exchange.getRequestURI().toString();
                    String[] token = getRequestsUrl.split("/");

                    try {
                        String username = token[1];
                        String password = token[2];
                        String commands = token[3];

                        try {
                             MetaLiteEngine metaLiteEngine = new MetaLiteEngine();
                             metaLiteEngine.setPassword(password);
                             metaLiteEngine.setUserName(username);
                             metaLiteEngine.exec(commands);

                            String response = metaLiteEngine.getRunMessage();
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream outputStream = exchange.getResponseBody();
                            outputStream.write(response.getBytes());
                            outputStream.close();
                        }catch (Exception exception) {
                            String response = exception.getMessage();
                            exchange.sendResponseHeaders(500, response.length());
                            OutputStream outputStream = exchange.getResponseBody();
                            outputStream.write(response.getBytes());
                            outputStream.close();
                        }

                    }catch (Exception exception) {
                        String response = "client message error";
                        exchange.sendResponseHeaders(400, response.length());
                        OutputStream outputStream = exchange.getResponseBody();
                        outputStream.write(response.getBytes());
                        outputStream.close();
                    }
                }
            };

            // 将处理请求的HttpHandler与指定的路径关联
            server.createContext("/", handler);

            // 启动服务器
            server.start();

            System.out.println("Server started on port 8000");
        }catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    // 将HashMap以文本形式写入文件
    private static void writeHashMapToFile(Map<String, Object> hashMap, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            //System.out.println("HashMap has been written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中读取HashMap
    private static Map<String, Object> readHashMapFromFile(String filename) {
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
            System.out.println("HashMap has been read from file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    // 解析值的数据类型
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
