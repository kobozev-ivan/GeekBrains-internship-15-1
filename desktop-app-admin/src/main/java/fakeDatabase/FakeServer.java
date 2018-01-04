package fakeDatabase;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.simple.JSONArray;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class FakeServer {


    public static void main(String[] args) {
        baseUri = UriBuilder.fromUri("http://LocalHost/").port(8989).build();
//        ResourceConfig resourceConfig = new ResourceConfig(WebService.class);
//        resourceConfig.register(FakeData.class);
//        resourceConfig.packages("fakeDatabase");
//        resourceConfig.register(org.glassfish.jersey.server.filter.UriConnegFilter.class);
//        resourceConfig.register(org.glassfish.jersey.jdkhttp.JdkHttpHandlerContainerProvider.class);
//
//        resourceConfig.property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);

//        httpServer = JdkHttpServerFactory.createHttpServer(baseUri, new ResourceConfig(WebService.class));
        httpServer = getHttpServer();

    }

    private static HttpServer httpServer;
    private static URI baseUri;

    static HttpServer getHttpServer() {
        if(httpServer == null) httpServer = JdkHttpServerFactory.createHttpServer(baseUri, new ResourceConfig(WebService.class),true) ;
        return httpServer;
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
