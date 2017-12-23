package gb_in.mobile_app_admin.presenter;

import gb_in.mobile_app_admin.model.Model;
import gb_in.mobile_app_admin.view.AbstractView;

/**
 * Created by Kuzin on 22.12.2017.
 */

public class Presenter {
    private Model model;
    private AbstractView view;

    public Presenter(AbstractView view) {
        this.model = new Model(this);
        this.view = view;
    }

    public void proceedError(String msg){
        view.displayError(msg);
    }



    public void loadPersonData(){
        model.loadPersons();
    }

    public void updatePersonData(int personId,String newPersonName){
        model.updatePerson(personId,newPersonName);
    }

    public void addPersonData(String personName){
        model.addPerson(personName);
    }

    public void deletePersonData(int personId){
        model.deletePerson(personId);
    }

    public void updateViewPersonData(String[] data){
        view.updateViewPersonData(data);
    }



    public void loadKeywordData(int person_Id){
        model.loadKeywords(person_Id);
    }

    public void updateKeywordData(int personId, int keywordId, String newKeywordName){
        model.updateKeyword(personId, keywordId, newKeywordName);
    }

    public void addKeywordData(int personId, String keywordName){
        model.addKeyword(personId,keywordName);
    }

    public void deleteKeywordData(int personId, int keywordId){
        model.deleteKeyword(personId ,keywordId);
    }

    public void updateViewKeywordData(String[] data){
        view.updateViewKeywordData(data);
    }



    public void loadSiteData(){
        model.loadSites();
    }

    public void updateSiteData(int siteId,String newSiteName){
        model.updateSite(siteId,newSiteName);
    }

    public void addSiteData(String siteName){
        model.addSite(siteName);
    }

    public void deleteSiteData(int siteId){
        model.deleteSite(siteId);
    }

    public void updateViewSiteData(String[] data){
        view.updateViewSiteData(data);
    }
}
