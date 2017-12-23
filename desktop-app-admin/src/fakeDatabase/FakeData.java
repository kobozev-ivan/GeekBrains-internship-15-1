package fakeDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Максим on 18.12.2017.
 */
public class FakeData {

    ArrayList<String> personsList = new ArrayList<>();
    ArrayList<String> sitesList = new ArrayList<>();
    ArrayList<String> keywordsList = new ArrayList<>();
    HashMap<String, ArrayList<String>> persons = new HashMap<>();
    HashMap<String, ArrayList<String>> sites = new HashMap<>();
    HashMap<String, HashMap<String, ArrayList<String>>> keywords = new HashMap<>();

    FakeData(){
        personsList.add("Иванов");
        personsList.add("Петров");
        personsList.add("Сидоров");
        personsList.add("Медведев");
        sitesList.add("bvbi.ru");
        sitesList.add("vhebv.com");
        keywordsList.add("Иванова");
        persons.put("Persons", personsList);
        sites.put("Sites", sitesList);
        HashMap<String, ArrayList<String>> keywordsPersons = new HashMap<>();
        keywordsPersons.put("Иванов", keywordsList);
        keywords.put("keywords", keywordsPersons);
    }


}
