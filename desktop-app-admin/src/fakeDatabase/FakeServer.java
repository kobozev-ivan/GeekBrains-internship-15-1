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
    private FakeData fakeData = new FakeData();
    JSONParser parser = new JSONParser();
    private JSONArray jsonArray = new JSONArray();

    private String nameTable;
    private String namePerson;


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
        JSONObject jsonAnswer = new JSONObject();
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
        nameTable = jsonObject.get("table").toString();
        JSONArray jsonArrayAdd = (JSONArray) jsonObject.get("add");
        JSONArray jsonArrayChangeDel = (JSONArray) jsonObject.get("changeDel");
        JSONArray jsonArrayChangeAdd = (JSONArray) jsonObject.get("changeAdd");
        JSONArray jsonArrayDel = (JSONArray) jsonObject.get("del");
        if (fakeData.persons.containsKey(nameTable)){
            if (jsonArrayAdd != null) toAdd(fakeData.persons, nameTable, jsonArrayAdd);
            if (jsonArrayChangeDel != null && jsonArrayChangeAdd != null) toChange(fakeData.persons, nameTable, jsonArrayChangeDel, jsonArrayChangeAdd);
            if (jsonArrayDel != null) toDel(fakeData.persons, nameTable, jsonArrayDel);
            System.out.println(fakeData.persons.get(nameTable));
        }
        if (fakeData.sites.containsKey(nameTable)){
            if (jsonArrayAdd != null) toAdd(fakeData.sites, nameTable, jsonArrayAdd);
            if (jsonArrayChangeDel != null && jsonArrayChangeAdd != null) toChange(fakeData.sites, nameTable, jsonArrayChangeDel, jsonArrayChangeAdd);
            if (jsonArrayDel != null) toDel(fakeData.sites, nameTable, jsonArrayDel);
            System.out.println(fakeData.sites.get(nameTable));
        }
        if (fakeData.keywords.containsKey(nameTable)){
            namePerson = jsonObject.get("Persons").toString();
            HashMap<String, ArrayList<String>> hashMapObj = fakeData.keywords.get(nameTable);
            if (!hashMapObj.containsKey(namePerson)) hashMapObj.put(namePerson, new ArrayList<>());
            if (jsonArrayAdd != null) toAdd(hashMapObj, namePerson, jsonArrayAdd);
            if (jsonArrayChangeDel != null && jsonArrayChangeAdd != null) toChange(hashMapObj, namePerson, jsonArrayChangeDel, jsonArrayChangeAdd);
            if (jsonArrayDel != null) toDel(hashMapObj, namePerson, jsonArrayDel);
            System.out.println(namePerson + " " + hashMapObj.get(namePerson));
        }
    }



    private void toAdd(HashMap<String, ArrayList<String>> hashMap, String stringKey, JSONArray jsonArray){
        for (int i = 0; i < jsonArray.size(); i++) {
            hashMap.get(stringKey).add(jsonArray.get(i).toString());
        }
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
