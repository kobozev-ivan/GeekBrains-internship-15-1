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
        if (modelData.isPersonPresent(personId))
            modelData.updatePerson(personId,newPersonName);
        else
            presenter.proceedError("person not exist!");
    }

    public void addPerson(String personName) {
        if (!modelData.isPersonPresent(personName))
            modelData.addPerson(personName);
        else
            presenter.proceedError("person already exist!");
    }

    public void deletePerson(int personId) {
        if (modelData.isPersonPresent(personId))
            modelData.removePerson(personId);
        else
            presenter.proceedError("person not exist!");
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
        if (modelData.isSitePresent(siteId))
            modelData.updateSite(siteId, newSiteName);
        else
            presenter.proceedError("site not exist!");
    }

    public void addSite(String siteName) {
        modelData.addSite(siteName);
    }

    public void deleteSite(int siteId) {
        if (modelData.isSitePresent(siteId))
            modelData.removeSite(siteId);
        else
            presenter.proceedError("site not exist!");
    }
}

