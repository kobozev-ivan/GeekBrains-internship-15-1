package fakeDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import reference.CUW;
import view.SheetReference;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Максим on 25.12.2017.
 */
@Path("/")
public class WebService{

    private FakeData fakeData = FakeData.getInstance();
    private JSONArray jsonArray = new JSONArray();
    private JSONParser parser = new JSONParser();

    @GET
    @Path("/{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toUpDate(@PathParam("table") String nameTable) {
            JSONObject jsonAnswer = new JSONObject();
        if (fakeData.persons.containsKey(nameTable)) getDataCollection(fakeData.persons, nameTable);
        if (fakeData.sites.containsKey(nameTable)) getDataCollection(fakeData.sites, nameTable);
        jsonAnswer.put(nameTable, jsonArray);
        return Response.ok(jsonAnswer.toString()).build();
    }

    @GET
    @Path("/keywords/{Person}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toUpDateKeywords(@PathParam("Person") String namePerson) {
        JSONObject jsonAnswer = new JSONObject();
        HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(CUW.KEYWORDS);
        if (hashMapObj.containsKey(namePerson))  getDataCollection(hashMapObj, namePerson);
        jsonAnswer.put(namePerson, jsonArray);
        return Response.ok(jsonAnswer.toString()).build();
    }

    private void getDataCollection(HashMap<String, ArrayList<String>> hashMap, String stringKey){
        ArrayList<String> stringArrayList = hashMap.get(stringKey);
        for (int i = 0; i < stringArrayList.size(); i++) {
            jsonArray.add(stringArrayList.get(i));
        }
    }

    @POST
    @Path("/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAdd(@PathParam("table") String nameTable, String stringAdd) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(stringAdd);
            JSONArray jsonArray = (JSONArray) jsonObject.get(nameTable);
            if (fakeData.persons.containsKey(nameTable)){
                addDataCollection(fakeData.persons, jsonArray, nameTable);
                return Response.status(201).build();
            }
            if (fakeData.sites.containsKey(nameTable)){
                addDataCollection(fakeData.sites, jsonArray, nameTable);
                return Response.status(201).build();
            }
        } catch (ParseException e) {
            throw new WebApplicationException(e.getMessage());
        }
        return Response.serverError().build();
    }

    @POST
    @Path ("/keywords/{Person}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAddKeywords(@PathParam("Person") String namePerson, String stringAdd) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(stringAdd);
            JSONArray jsonArray = (JSONArray) jsonObject.get(namePerson);
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(CUW.KEYWORDS);
            if (hashMapObj.containsKey(namePerson)){
                addDataCollection(hashMapObj, jsonArray, namePerson);
                return Response.status(201).build();
            }else {
                hashMapObj.put(namePerson, new ArrayList<>());
                addDataCollection(hashMapObj, jsonArray, namePerson);
                return Response.status(201).build();
            }
        } catch (ParseException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    private void addDataCollection(HashMap<String, ArrayList<String>> hashMap, JSONArray jsonArray, String nameTable){
        for (int i = 0; i < jsonArray.size(); i++) {
            hashMap.get(nameTable).add((String) jsonArray.get(i));
        }
    }
}
