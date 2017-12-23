package fakeDatabase;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Максим on 22.12.2017.
 */
public class FakeServer {
    FakeData fakeData = new FakeData();
    JSONParser parser = new JSONParser();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonAnswer = new JSONObject();

    String nameTable;
    String namePerson;


    public JSONObject toUpDate(JSONObject jsonObject) {
        nameTable = jsonObject.get("table").toString();
        if (fakeData.persons.containsKey(nameTable)){
            getDataCollection(fakeData.persons, nameTable);
        }
        if (fakeData.sites.containsKey(nameTable)){
            getDataCollection(fakeData.sites, nameTable);
        }
        if (fakeData.keywords.containsKey(nameTable)){
            namePerson = jsonObject.get("Persons").toString();
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
            if (hashMapObj.containsKey(namePerson)) {
                getDataCollection(hashMapObj, namePerson);
            }
        }
        jsonAnswer.clear();
        jsonAnswer.put("table", nameTable);
        if (nameTable.equalsIgnoreCase("keywords")) jsonAnswer.put("Persons", namePerson);
        jsonAnswer.put("namesString", jsonArray);
        return jsonAnswer;

    }

    private void getDataCollection(HashMap<String, ArrayList<String>> hashMap, String stringKey){
        ArrayList<String> stringArrayList = hashMap.get(stringKey);
        for (int i = 0; i < stringArrayList.size(); i++) {
            jsonArray.add(stringArrayList.get(i));
        }
    }


    public void toSave(JSONObject jsonObject) {

    }

}
