package gb_in.mobile_app_admin.model;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kuzin on 22.12.2017.
 */

public class Model {
    private OfflineDataModel modelData;


    public Model() {
        modelData = new OfflineDataModel();

    }

    public String[] loadPersons() {
        return modelData.getPersons();
    }

    public String[] loadKeywords(String person_name) {
        return modelData.getKeywords(person_name);
    }

    public String[] loadSites() {
        return modelData.getSites();
    }



    public void updatePerson(int personId, String newPersonName) {
        if (modelData.isPersonPresent(personId))
            modelData.updatePerson(personId,newPersonName);
    }

    public void addPerson(String personName) {
        if (!modelData.isPersonPresent(personName))
            modelData.addPerson(personName);
    }

    public void deletePerson(int personId) {
        if (modelData.isPersonPresent(personId))
            modelData.removePerson(personId);
    }



    public void updateKeyword( String personName, int keywordId, String newKeywordName) {
        modelData.updateKeyword(personName,keywordId,newKeywordName);
    }

    public void addKeyword(String personName, String keywordName) {
        modelData.addKeyword(personName, keywordName);
    }

    public void deleteKeyword(String personName, int keywordId) {
        modelData.removeKeyword(personName, keywordId);
    }



    public void updateSite(int siteId, String newSiteName) {
        if (modelData.isSitePresent(siteId))
            modelData.updateSite(siteId, newSiteName);
    }

    public void addSite(String siteName) {
        modelData.addSite(siteName);
    }

    public void deleteSite(int siteId) {
        if (modelData.isSitePresent(siteId))
            modelData.removeSite(siteId);
    }

    public String[] getPersonsStatistics(){
        return modelData.getPersons();
    }
}

