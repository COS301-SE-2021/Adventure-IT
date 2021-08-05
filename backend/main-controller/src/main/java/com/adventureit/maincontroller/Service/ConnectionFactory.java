package com.adventureit.maincontroller.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ConnectionFactory {
    private double API_VERSION = 0;
    private String API = "";
    private String METHOD = "POST";
    private String TYPE = "application/json";
    private String USER_AGENT ="Mozilla/5.0";
    private String data = "";
    private URL connection;
    private HttpURLConnection finalConnection;

    private HashMap<String,String> fields = new HashMap<String,String>();

    public ConnectionFactory(String[] endpoint, String url, double version){
        this.API_VERSION = version;
        this.API = url;
        fields.put("version",String.valueOf(version));
        for(int i = 0; i< endpoint.length;i++){
            String[] points = endpoint[i].split(";");
            for(int y = 0; y < points.length;y++){
                fields.put(points[y].split(":")[0], points[y].split(":")[1]);
            }
        }

    }

    public String getData(){
        if(!this.getEndpoints().equalsIgnoreCase("")&&!this.getEndpoints().isEmpty()) {
            String vars = "";
            String vals = "";
            try {
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    vars = entry.getKey();
                    vals = entry.getValue();
                    data += ("&" + vars + "=" + vals);
                    System.out.println(data);
                }
                if (data.startsWith("&")) {
                    data = data.replaceFirst("&", "");
                }
                return data;
            }catch(Exception e){

            }
        }
        return "";
    }

    public String buildConnection(){
        StringBuilder content = new StringBuilder();
        if(!this.getEndpoints().equalsIgnoreCase("")&&!this.getEndpoints().isEmpty()){
            String vars = "";
            String vals = "";
            try {
                for(Map.Entry<String,String> entry : fields.entrySet()) {
                    vars = entry.getKey();
                    vals = entry.getValue();
                    data += ("&" + vars + "=" + vals);

                }
                if(data.startsWith("&")){
                    data = data.replaceFirst("&","");
                }
                connection = new URL(API);
                BufferedReader reader = new BufferedReader(new InputStreamReader(readWithAccess(connection,data)));
                String line;
                while((line=reader.readLine())!=null){
                    content.append(line+ "\n");
                }
                reader.close();
                return content.toString();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }else{
            return null;
        }
        return null;
    }

    private InputStream readWithAccess(URL url,String data){
        try{
            byte[] out = data.getBytes();
            finalConnection = (HttpURLConnection) url.openConnection();
            finalConnection.setRequestMethod(METHOD);
            finalConnection.setDoOutput(true);
            finalConnection.setRequestProperty("User-Agent", USER_AGENT);
            finalConnection.addRequestProperty("Content-Type", TYPE);
            finalConnection.connect();
            try {
                OutputStream os = finalConnection.getOutputStream();
                os.write(out);
            }catch(Exception e){
                System.err.println(e.getMessage());
                return null;
            }
            return finalConnection.getInputStream();
        }catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }

    }
    public String getApiVersion(){
        return String.valueOf(API_VERSION);
    }

    public String getEndpoints(){
        return  fields.toString();
    }

    public String getEndpointValue(String key){
        return fields.get(key);
    }

    public void setUserAgent(String userAgent){
        this.USER_AGENT = userAgent;
    }

    public void setMethod(String method){
        this.METHOD = method;
    }

    public void setSubmissionType(String type){
        this.TYPE =type;
    }
}
