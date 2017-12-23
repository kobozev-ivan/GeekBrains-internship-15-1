package gb_in.mobile_app_admin.model;

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

    public String[] getPersons(){
        String[] s = new String[personData.size()];
        personData.values().toArray(s);
        return s;
    }

    void addPerson(String name){
        Integer[] array = new Integer[personData.size()];
        personData.keySet().toArray(array);
        if (array.length>0) {
            int  i = array[array.length - 1] + 1;
            personData.put(i, name);
            keywordData.put(i, new String[0]);
        }else {
            personData.put(0, name);
            keywordData.put(0, new String[0]);
        }
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
        Integer[] array = new Integer[siteData.size()];
        siteData.keySet().toArray(array);
        if (array.length>0)
            siteData.put(array[array.length-1]+1,name);
        else
            siteData.put(0,name);
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



    void addKeyword(int id, String newKeyword){
        String[] keywordsOld = keywordData.remove(id);
        String[] keywordsNew = Arrays.copyOf(keywordsOld, keywordsOld.length+1);
        keywordsNew[keywordsNew.length-1] = newKeyword;

        keywordData.put(id,keywordsNew);
    }

    void removeKeyword(int personId, int keyId){
        String[] oldKeywords = keywordData.remove(personId);
        String[] newKeywords = new String[oldKeywords.length - 1];
        int index = 0;
        for (int i = 0; i < oldKeywords.length; i++) {
            if (i!=keyId) {
                newKeywords[index] = oldKeywords[i];
                index++;
            }
        }
        keywordData.put(personId,newKeywords);
    }

    void updateKeyword(int personId, int keyId, String keyword) {
        String[] keywords = keywordData.remove(personId);
        keywords[keyId] = keyword;
        keywordData.put(personId,keywords);
    }

    public String[] getKeywords(int personId) {
        return keywordData.get(personId);
    }

}
