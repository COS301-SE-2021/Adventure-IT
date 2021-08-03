package com.adventureit.maincontroller.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ConnectionFactory {
    private double API_VERSION = 0;
    private String API = "";
    private String METHOD = "POST";
    private String TYPE = "application/x-www-form-urlencoded";
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
            for(int y =0; y < points.length;y++){
                fields.put(points[y].split(":")[0], points[y].split(":")[1]);
            }
        }
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
                    data = data.replaceFirst("$","");
                }
                connection = new URL(API);

            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
    }

    private InputStream readWithAccess(URL url,String data){
        try{
            byte[] out = data.toString().getBytes();

        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    public String getApiVersion(){
        return String.valueOf(API_VERSION);
    }

    public String getEndpoints(){
        return  fields.toString();
    }
}
