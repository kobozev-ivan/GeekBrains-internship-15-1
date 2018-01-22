package gb_in.mobile_app_admin.model;

import android.content.Intent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuzin on 22.12.2017.
 */

class OfflineDataModel {
    private Map<Integer,String> personData = new HashMap<>();
    private Map<Integer,String> siteData = new HashMap<>();
    private Map<Integer,String[]> keywordData = new HashMap<>();


    public OfflineDataModel() {
        personData.put(0,"Личность 1");
        personData.put(1,"Личность 2");
        siteData.put(0, "site 1");
        siteData.put(1,"site 2");

        keywordData.put(0,new String[]{"Л1","л1"});
        keywordData.put(1,new String[]{"Л2","л2"});
    }

    public String[] getPersons(){
        String[] s = new String[personData.size()];
        personData.values().toArray(s);
        return s;
    }

    void addPerson(String name){
        int id = 0;
        for (Map.Entry s : personData.entrySet()) {
            if ((int)s.getKey() >= id) {
                id = ((int) s.getKey()) + 1;
            }
        }
        personData.put(id, name);
        keywordData.put(id, new String[]{});

    }

    boolean isPersonPresent(String name){
        return personData.containsValue(name);
    }
    boolean isPersonPresent(int id){
        return personData.containsKey(id);
    }

    void removePerson(int id){
        personData.remove(id);
        keywordData.remove(id);
    }

    void updatePerson(int id, String name) {
        personData.remove(id);
        personData.put(id,name);
    }



    void addSite(String name){
        int id = 0;
        for (Map.Entry s : siteData.entrySet()) {
            if ((int)s.getKey() >= id) {
                id = ((int) s.getKey()) + 1;
            }
        }

        siteData.put(id,name);
    }

    void removeSite(int id){
        siteData.remove(id);
    }

    void updateSite(int id, String name) {
        String s = siteData.remove(id);
        siteData.put(id,name);
    }

    public String[] getSites() {
        String[] s = new String[siteData.size()];
        siteData.values().toArray(s);
        return s;
    }

    public boolean isSitePresent(int siteId) {
        return siteData.containsKey(siteId);
    }



    void addKeyword(String personName, String newKeyword){
        int id = 0;
        for (Map.Entry s : personData.entrySet()) {
            if (s.getValue().equals(personName)) {
                id = (Integer) s.getKey();
            }
        }
        String[] keywordsOld = keywordData.remove(id);
        String[] keywordsNew = Arrays.copyOf(keywordsOld, keywordsOld.length+1);
        keywordsNew[keywordsNew.length-1] = newKeyword;

        keywordData.put(id,keywordsNew);
    }

    void removeKeyword(String personName, int keyId){
        int id = 0;
        for (Map.Entry s : personData.entrySet()) {
            if (s.getValue().equals(personName)) {
                id = (Integer) s.getKey();
            }
        }

        String[] oldKeywords = keywordData.remove(id);
        String[] newKeywords = new String[oldKeywords.length - 1];
        int index = 0;
        for (int i = 0; i < oldKeywords.length; i++) {
            if (i!=keyId) {
                newKeywords[index] = oldKeywords[i];
                index++;
            }
        }
        keywordData.put(id,newKeywords);
    }

    void updateKeyword(String personName, int keyId, String keyword) {
        int id = 0;
        for (Map.Entry s : personData.entrySet()) {
            if (s.getValue().equals(personName)) {
                id = (Integer) s.getKey();
            }
        }

        String[] keywords = keywordData.remove(id);
        keywords[keyId] = keyword;
        keywordData.put(id,keywords);
    }


    public String[] getKeywords(String personName) {
        for (Map.Entry s : personData.entrySet()) {
            if (s.getValue().equals(personName)) {
                return keywordData.get(s.getKey());
            }
        }
        return new String[]{};
    }

}
