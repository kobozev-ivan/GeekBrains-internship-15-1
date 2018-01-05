package fakeDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import reference.CUW;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

@Path("/")
public class WebService{

    private static final int CREATE = 201;
    private static final String ANSWER = "ОТВЕТ:\n";
    private FakeData fakeData = FakeData.getInstance();
    private WindowServer textArea = WindowServer.getInstance();
    private JSONArray jsonArray;

    @GET
    @Path("/{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toUpDate(@PathParam("table") String nameTable) {
        JSONObject jsonAnswer = new JSONObject();
        if (fakeData.persons.containsKey(nameTable)) jsonArray = getDataCollection(fakeData.persons, nameTable);
        if (fakeData.sites.containsKey(nameTable)) jsonArray = getDataCollection(fakeData.sites, nameTable);
        jsonAnswer.put(nameTable, jsonArray);
        Response response = Response.ok(jsonAnswer.toJSONString()).build();
        toViewAnswer(response);
        return response;
    }

    @GET
    @Path("/keywords/{Person}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toUpDateKeywords(@PathParam("Person") String namePerson) {
        JSONObject jsonAnswer = new JSONObject();
        HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(CUW.KEYWORDS);
        if (hashMapObj.containsKey(namePerson))  jsonArray = getDataCollection(hashMapObj, namePerson);
        jsonAnswer.put(namePerson, jsonArray);
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
    public Response setAdd(@PathParam("table") String nameTable, String stringAdd) {
        Response response = Response.status(CREATE).build();;
        try {
            JSONArray jsonArray = parseToJSONString(nameTable, stringAdd);
            if (fakeData.persons.containsKey(nameTable)) addDataCollection(fakeData.persons, jsonArray, nameTable);
            if (fakeData.sites.containsKey(nameTable)) addDataCollection(fakeData.sites, jsonArray, nameTable);
        } catch (ParseException e) {
            response = Response.serverError().build();
            throw new WebApplicationException(e.getMessage());
        }finally {
            toViewAnswer(response);
        }
        return response;
    }

    @POST
    @Path ("/keywords/{Person}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAddKeywords(@PathParam("Person") String namePerson, String stringAdd) {
        Response response = Response.status(CREATE).build();
        try {
            JSONArray jsonArray = parseToJSONString(namePerson, stringAdd);
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(CUW.KEYWORDS);
            if (hashMapObj.containsKey(namePerson)) addDataCollection(hashMapObj, jsonArray, namePerson);
            else {
                hashMapObj.put(namePerson, new ArrayList<>());
                addDataCollection(hashMapObj, jsonArray, namePerson);
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

    @PUT
    @Path("/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setChange(@PathParam("table") String nameTable, String stringChange) {
        System.out.println(stringChange);
        Response response = Response.status(404, "Элемент в базе не найден").build();
        try {
            JSONArray jsonArray = parseToJSONString(nameTable, stringChange);
            if (fakeData.persons.containsKey(nameTable)) {
                changeDataCollection(fakeData.persons, jsonArray, nameTable);
                response = Response.status(CREATE).build();
            }
            if (fakeData.sites.containsKey(nameTable)){
                changeDataCollection(fakeData.sites, jsonArray, nameTable);
                response = Response.status(CREATE).build();
            }
        } catch (ParseException e) {
            response = Response.serverError().build();
            throw new WebApplicationException(e.getMessage());
        }finally {
            toViewAnswer(response);
        }
        return response;
    }

    @PUT
    @Path("/keywords/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setChangeKeywords(@PathParam("table") String nameTable, String stringChange) {
        Response response = Response.status(404, "Элемент в базе не найден").build();
        try {
            JSONArray jsonArray = parseToJSONString(nameTable, stringChange);
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(CUW.KEYWORDS);
            if (hashMapObj.containsKey(nameTable)) {
                changeDataCollection(hashMapObj, jsonArray, nameTable);
                response = Response.status(CREATE).build();
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
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(stringJSON);
        return (JSONArray) jsonObject.get(nameTable);
    }

    private void toViewAnswer(Response response){
        if (response.getStatus() != CREATE){
            textArea.append(ANSWER + response.toString()+ "\n" + response.getEntity().toString()+ "\n");
            return;
        }
        textArea.append(ANSWER + String.valueOf(response.getStatus()) + "\t"+ response.getStatusInfo() + "\n");
    }


}
