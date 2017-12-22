package gb_in.mobile_app_admin.model;

import gb_in.mobile_app_admin.presenter.Presenter;

/**
 * Created by Kuzin on 22.12.2017.
 */

public class Model {
    private OfflineDataModel modelData;
    private Presenter presenter;

    public Model(Presenter presenter) {
        this.presenter = presenter;
        modelData = new OfflineDataModel();
    }

    public void loadPersons() {
        presenter.updateViewPersonData(modelData.getPersons());
    }

    public void loadKeywords(int person_id) {
        presenter.updateViewKeywordData(modelData.getKeywords(person_id));
    }

    public void loadSites() {
        presenter.updateViewSiteData(modelData.getSites());
    }



    public void updatePerson(int personId, String newPersonName) {
        modelData.updatePerson(personId,newPersonName);
    }

    public void addPerson(String personName) {
        modelData.addPerson(personName);
    }

    public void deletePerson(int personId) {
        modelData.removePerson(personId);
    }



    public void updateKeyword( int personId, int keywordId, String newKeywordName) {
        modelData.updateKeyword(personId,keywordId,newKeywordName);
    }

    public void addKeyword(int personId, String keywordName) {
        modelData.addKeyword(personId, keywordName);
    }

    public void deleteKeyword(int personId, int keywordId) {
        modelData.removeKeyword(personId, keywordId);
    }



    public void updateSite(int siteId, String newSiteName) {
        modelData.updateSite(siteId, newSiteName);
    }

    public void addSite(String siteName) {
        modelData.addSite(siteName);
    }

    public void deleteSite(int siteId) {
        modelData.removeSite(siteId);
    }
}

