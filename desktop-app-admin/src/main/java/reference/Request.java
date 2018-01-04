package reference;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import gia.SheetReference;
import gia.SheetReferenceKeywords;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

public class Request implements Requestable {
    private static final int OK = 200;
    private static final int CREATED_OK = 201;
    private JSONArray addWords = new JSONArray();
    private JSONArray changeWords = new JSONArray();
    private JSONArray delWords = new JSONArray();

    private String host;
    private String port;
    private String title;

    @Override
    public ArrayList<String> toUpDate(SheetReference sheetReference) throws WebServiceException{
        WebTarget target = getPath(sheetReference);
        Response answer = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        if (answer.getStatus() != OK){
            answer.close();
            throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus());
        }
        return toExtractData(answer.readEntity(String.class),title);
    }

    @Override
    public void toSave(SheetReference sheetReference)throws WebServiceException{
        WebTarget target = getPath(sheetReference);
        if (!addWords.isEmpty()){
            String postRequest =  (new Message(title, addWords)).toJSONString();
            Response answer = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(postRequest, MediaType.APPLICATION_JSON_TYPE),Response.class);
            if (answer.getStatus() != CREATED_OK) {
                addWords.clear();
                throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus() + "\n" + answer.getStatusInfo());
            }
        }
        if (!changeWords.isEmpty()){
            String putRequest =  (new Message(title, changeWords)).toJSONString();
            Response answer = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.entity(putRequest, MediaType.APPLICATION_JSON_TYPE),Response.class);
            if (answer.getStatus() != OK) {
                changeWords.clear();
                throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus() + "\n" + answer.getStatusInfo());
            }
        }
        if (!delWords.isEmpty()){
            String delRequest =  (new Message(title, delWords)).toJSONString();
            Response answer = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .delete();
            if (answer.getStatus() != OK) {
                delWords.clear();
                throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus() + "\n" + answer.getStatusInfo());
            }
        }
        addWords.clear();
        changeWords.clear();
        delWords.clear();
    }

    private WebTarget getPath(SheetReference sheetReference){
        title = sheetReference.getName();
        System.out.println(title);
        if (title.equals(CUW.KEYWORDS)){
            title = ((SheetReferenceKeywords) sheetReference).selectComboBoxModel;
            return getTarget().path(CUW.KEYWORDS).path(title);
        }
        return getTarget().path(title);
    }

    private WebTarget getTarget(){
        return ClientBuilder.newClient(new ClientConfig()).target("http://LocalHost:8989/");
    }

    void toAdditionOfWords(String stringInput) {
        addWords.add(stringInput);
        if (!delWords.isEmpty()) isMatchWord(delWords, stringInput);
    }

    void toChangeWords(String stringSelect, String stringInput) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)){
                addWords.add(stringInput);
                return;
            }
        }
        changeWords.add(new JSONObject().put(stringSelect, stringInput));
    }

    void toRemovingWords(String stringSelect) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)) return;
        }
        if (!changeWords.isEmpty()){
            if (isMatchWord(changeWords, stringSelect)) return;
        }
        delWords.add(stringSelect);
    }

        private boolean isMatchWord(JSONArray jsonArray, String string){
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i) instanceof  JSONObject){
                    if (((JSONObject) jsonArray.get(i)).containsValue(string)){
                        jsonArray.remove(i);
                        return true;
                    }
                }
                if (jsonArray.get(i).equals(string)){
                    jsonArray.remove(i);
                    return true;
                }
            }
            return false;
        }


        private ArrayList<String> toExtractData(String answer, String nameTable) {
        ArrayList<String> arrayList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(answer);
            JSONArray jsonArray = (JSONArray) jsonObject.get(nameTable);
            if (!jsonArray.isEmpty()){
                for (int i = 0; i < jsonArray.size(); i++) {
                    arrayList.add((String) jsonArray.get(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private class Message extends JSONObject{

        Message(String title, JSONArray jsonArray){
            put(title, jsonArray);
        }
    }
}
