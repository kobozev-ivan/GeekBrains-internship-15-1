package gb_in.mobile_app_admin.presenter;

import java.io.Serializable;

import gb_in.mobile_app_admin.AddictionActivity;
import gb_in.mobile_app_admin.ReferenceFragment;
import gb_in.mobile_app_admin.StatisticFragment;
import gb_in.mobile_app_admin.model.Model;
import gb_in.mobile_app_admin.view.AbstractReferenceClass;

/**
 * Created by Kuzin on 22.12.2017.
 */

public class Presenter implements Serializable {
    private Model model;
    private AbstractReferenceClass view;

    private ReferenceFragment referenceFragment;
    private StatisticFragment statisticFragment;

    private AddictionActivity addictionActivity;

    public Presenter(AbstractReferenceClass view,
                     ReferenceFragment referenceFragment,
                     StatisticFragment statisticFragment) {
        this.model = new Model();
        this.view = view;
        this.referenceFragment = referenceFragment;
        this.statisticFragment = statisticFragment;
    }

    public void setAddictionActivity(AddictionActivity addictionActivity) {
        this.addictionActivity = addictionActivity;
    }

    public void proceedError(String msg){
        view.displayError(msg);
    }



    public void loadPersonData(){
        String[] data = model.loadPersons();
        referenceFragment.updateListAdapter(data);
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


    public void loadSiteData(){
        String[] data = model.loadSites();
        referenceFragment.updateListAdapter(data);
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


    public void loadPersonStatistics() {
        String[] persons = model.getPersonsStatistics();
        statisticFragment.updateChart(persons);
    }
}
