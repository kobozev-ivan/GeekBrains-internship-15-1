package reference;

import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.SheetReference;
import view.SheetReferenceKeywords;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
    /**
 * Created by Максим on 19.12.2017.
 */
public class Request implements Requestable{
    private static final int OK = 200;
    private static final int CREATED_OK = 201;
    private JSONArray addWords = new JSONArray();
    private JSONArray changeDelWords = new JSONArray();
    private JSONArray changeAddWords = new JSONArray();
    private JSONArray delWords = new JSONArray();

    private String host;
    private String port;
    private String title;

        @Override
    public ArrayList<String> toUpDate(SheetReference sheetReference) throws WebServiceException{
        WebTarget target = getPath(sheetReference);
        Response answer = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        if (answer.getStatus() != OK) throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus());
        return toExtractData(answer.readEntity(String.class),title);
    }

    public void toSave(SheetReference sheetReference)throws WebServiceException{
        WebTarget target = getPath(sheetReference);
        if (!addWords.isEmpty()){
            String postRequest = new Message(title, addWords).toJSONString();
            Response answer = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(postRequest, MediaType.APPLICATION_JSON_TYPE),Response.class);
            if (answer.getStatus() != CREATED_OK) {
                addWords.clear();
                throw new WebServiceException("Failed : HTTP error code : " + answer.getStatus() + "\n" + answer.getStatusInfo());
            }
            addWords.clear();
        }


        changeDelWords.clear();
        changeAddWords.clear();
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
        return ClientBuilder.newClient(new ClientConfig()).target("http://LocalHost:8080");
    }

    private boolean isMatchWord(JSONArray jsonArray, String string){
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i).equals(string)){
                jsonArray.remove(i);
                return true;
            }
        }
        return false;
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
        changeDelWords.add(stringSelect);
        changeAddWords.add(stringInput);
    }

    void toRemovingWords(String stringSelect) {
        if (!addWords.isEmpty()){
            if (isMatchWord(addWords, stringSelect)) return;
        }
        if (!changeAddWords.isEmpty()){
            if (isMatchWord(changeAddWords, stringSelect)) return;
        }
        delWords.add(stringSelect);
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
}