package fakeDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class FakeData {

    HashMap<String, ArrayList<String>> persons = new HashMap<>();
    HashMap<String, ArrayList<String>> sites = new HashMap<>();
    HashMap<String, HashMap<String, ArrayList<String>>> keywords = new HashMap<>();

    private static volatile FakeData instance;

    private FakeData(){

        ArrayList<String> personsList = new ArrayList<>();
        personsList.add("Иванов");
        personsList.add("Петров");
        personsList.add("Сидоров");
        personsList.add("Медведев");
        ArrayList<String> sitesList = new ArrayList<>();
        sitesList.add("http://bvbi.ru");
        sitesList.add("http://vhebv.com");
        ArrayList<String> keywordsList = new ArrayList<>();
        keywordsList.add("Иванова");
        persons.put("Persons", personsList);
        sites.put("Sites", sitesList);
        HashMap<String, ArrayList<String>> keywordsPersons = new HashMap<>();
        keywordsPersons.put("Иванов", keywordsList);
        keywords.put("keywords", keywordsPersons);
    }

    static FakeData getInstance() {
        FakeData localInstance = instance;
        if (localInstance == null) {
            synchronized (FakeData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FakeData();
                }
            }
        }
        return localInstance;
    }
}
