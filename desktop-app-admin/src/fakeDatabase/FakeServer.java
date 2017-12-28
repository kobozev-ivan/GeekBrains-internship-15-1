package fakeDatabase;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Максим on 22.12.2017.
 */
public class FakeServer {


    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://LocalHost/").port(8080).build();
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, new ResourceConfig(WebService.class));
    }



    private void toChange(HashMap<String, ArrayList<String>> hashMap, String stringKey, JSONArray jsonArrayDel, JSONArray jsonArrayAdd){
        pass: for (int i = 0; i < jsonArrayDel.size(); i++) {
            for (int j = 0; j < hashMap.get(stringKey).size(); j++) {
                if (jsonArrayDel.get(i).equals(hashMap.get(stringKey).get(j))){
                    hashMap.get(stringKey).set(j, jsonArrayAdd.get(i).toString());
                    continue pass;
                }
            }
        }
    }

    private void toDel(HashMap<String, ArrayList<String>> hashMap, String stringKey, JSONArray jsonArray){
        pass: for (int i = 0; i < jsonArray.size(); i++) {
            for (int j = 0; j < hashMap.get(stringKey).size(); j++) {
                if (jsonArray.get(i).equals(hashMap.get(stringKey).get(j))){
                    hashMap.get(stringKey).remove(j);
                    continue pass;
                }
            }
        }
    }
}
