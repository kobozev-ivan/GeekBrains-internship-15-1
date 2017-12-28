package fakeDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Максим on 18.12.2017.
 */
class FakeData {

    HashMap<String, ArrayList<String>> persons = new HashMap<>();
    HashMap<String, ArrayList<String>> sites = new HashMap<>();
    HashMap<String, HashMap<String, ArrayList<String>>> keywords = new HashMap<>();

    FakeData(){
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


}
