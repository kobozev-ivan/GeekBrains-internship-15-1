package fakeDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import reference.CUW;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/api/v1.0/")
public class WebService{

    private static final int CREATE = 201;
    private static final String ANSWER = "ОТВЕТ:\n";
    private static final String REQUEST = "ЗАПРОС:\n";
    private FakeData fakeData = FakeData.getInstance();
    private WindowServer textArea = WindowServer.getInstance();
    private JSONParser parser = new JSONParser();
    private Response response = Response.status(404, "Элемент в базе не найден").build();

    @GET
    @Path("/{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toUpDate(@PathParam("table") String nameTable, @DefaultValue("")@QueryParam("subtable") String name) {
        JSONObject jsonAnswer = new JSONObject();
        JSONArray jsonArray = null;
        if (name.isEmpty()){
            if (fakeData.persons.containsKey(nameTable)) jsonArray = getDataCollection(fakeData.persons, nameTable);
            if (fakeData.sites.containsKey(nameTable)) jsonArray = getDataCollection(fakeData.sites, nameTable);
        }else{
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
            if (hashMapObj.containsKey(name))  {
                jsonArray = getDataCollection(hashMapObj, name);
                nameTable = name;
            }
        }
        jsonAnswer.put(nameTable, jsonArray);
        Response response = Response.ok(jsonAnswer.toJSONString()).build();
        toViewAnswer(response);
        return response;
    }

    private JSONArray getDataCollection(HashMap<String, ArrayList<String>> hashMap, String stringKey){
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> stringArrayList = hashMap.get(stringKey);
        for (int i = 0; i < stringArrayList.size(); i++) {
            jsonArray.add(stringArrayList.get(i));
        }
        return jsonArray;
    }

    @POST
    @Path("/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAdd(@PathParam("table") String nameTable, @DefaultValue("")@QueryParam("subtable") String name, String message) {
        toViewingRequestMessage(message);
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(message);
            if (jsonObject.containsKey(nameTable)){
                JSONObject jobject = (JSONObject) jsonObject.get(nameTable);
                if (jobject.containsKey("add")){
                    JSONArray jsonArray = (JSONArray)jobject.get("add");
                    if (fakeData.persons.containsKey(nameTable)) addDataCollection(fakeData.persons, jsonArray, nameTable);
                    if (fakeData.sites.containsKey(nameTable)) addDataCollection(fakeData.sites, jsonArray, nameTable);
                    response = Response.status(CREATE).build();
                }
                if (jobject.containsKey("del")){
                    JSONArray jsonArray = (JSONArray)jobject.get("del");
                    if (fakeData.persons.containsKey(nameTable)) delDataCollection(fakeData.persons, jsonArray, nameTable);
                    if (fakeData.sites.containsKey(nameTable)) delDataCollection(fakeData.sites, jsonArray, nameTable);
                    response = Response.status(CREATE).build();
                }
            }
            if (jsonObject.containsKey(name)){
                JSONObject jobject = (JSONObject) jsonObject.get(name);
                if (jobject.containsKey("add")){
                    JSONArray jsonArray = (JSONArray)jobject.get("add");
                    HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
                    if (hashMapObj.containsKey(name)){
                        addDataCollection(hashMapObj, jsonArray, name);
                        response = Response.status(CREATE).build();
                    }
                    else {
                        hashMapObj.put(name, new ArrayList<>());
                        addDataCollection(hashMapObj, jsonArray, name);
                        response = Response.status(CREATE).build();
                    }
                }
                if (jobject.containsKey("del")){
                    JSONArray jsonArray = (JSONArray)jobject.get("del");
                    HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
                    if (hashMapObj.containsKey(name)){
                        delDataCollection(hashMapObj, jsonArray, name);
                        response = Response.status(CREATE).build();
                    }
                }
            }
        } catch (ParseException e) {
            response = Response.serverError().build();
            throw new WebApplicationException(e.getMessage());
        }finally {
            toViewAnswer(response);
        }
        return response;
    }

    private void addDataCollection(HashMap<String, ArrayList<String>> hashMap, JSONArray jsonArray, String nameTable){
        for (int i = 0; i < jsonArray.size(); i++) {
            hashMap.get(nameTable).add((String) jsonArray.get(i));
        }
    }

    private void delDataCollection(HashMap<String, ArrayList<String>> hashMap, JSONArray jsonArray, String nameTable){
        ArrayList<String> arrayList = hashMap.get(nameTable);
        for (int i = 0; i < jsonArray.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (jsonArray.get(i).equals(arrayList.get(j))) arrayList.remove(j);
            }
        }
    }

    @PUT
    @Path("/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setChange(@PathParam("table") String nameTable, @DefaultValue("")@QueryParam("subtable") String name, String stringChange) {
        toViewingRequestMessage(stringChange);
        try {
            if (name.isEmpty()){
                JSONArray jsonArray = parseToJSONString(nameTable, stringChange);
                if (fakeData.persons.containsKey(nameTable)) {
                    changeDataCollection(fakeData.persons, jsonArray, nameTable);
                    response = Response.status(CREATE).build();
                }
                if (fakeData.sites.containsKey(nameTable)){
                    changeDataCollection(fakeData.sites, jsonArray, nameTable);
                    response = Response.status(CREATE).build();
                }
            }else{
                JSONArray jsonArray = parseToJSONString(name, stringChange);
                HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
                if (hashMapObj.containsKey(name)) {
                    changeDataCollection(hashMapObj, jsonArray, name);
                    response = Response.status(CREATE).build();
                }
            }
        } catch (ParseException e) {
            response = Response.serverError().build();
            throw new WebApplicationException(e.getMessage());
        }finally {
            toViewAnswer(response);
        }
        return response;
    }

    private void changeDataCollection(HashMap<String, ArrayList<String>> hashMap, JSONArray jsonArray, String nameTable){
        ArrayList<String> arrayList = hashMap.get(nameTable);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            for (int j = 0; j < arrayList.size(); j++) {
                if (jsonObject.containsKey(arrayList.get(j))){
                    String key = arrayList.get(j);
                    arrayList.set(j, (String) jsonObject.get(key));
                }
            }
        }
    }

    private JSONArray parseToJSONString(String nameTable, String stringJSON) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(stringJSON);
        return (JSONArray) jsonObject.get(nameTable);
    }

    private void toViewingRequestMessage(String message){
        textArea.append(REQUEST + message +"\n\n");
    }

    private void toViewAnswer(Response response){
        if (response.getStatus() != CREATE){
            textArea.append(ANSWER + response.toString()+ "\n" + response.getEntity().toString()+ "\n\n");
            return;
        }
        textArea.append(ANSWER + String.valueOf(response.getStatus()) + "\t"+ response.getStatusInfo() + "\n\n");
    }
}
