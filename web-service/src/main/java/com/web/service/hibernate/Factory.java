package com.web.service.hibernate;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class Factory {
    private static Factory instance = null;
    private static KeywordsInterface keywordsInterface = null;
    private static PagesInterface pagesInterface = null;
    private static PersonPageRankInterface personPageRankInterface = null;
    private static PersonsInterface personsInterface = null;
    private static SitesInterface sitesInterface = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public static KeywordsInterface getKeywordsInterface(){
        if (keywordsInterface == null){
            keywordsInterface = new KeywordImpl();
        }
        return keywordsInterface;
    }

    public static SitesInterface getSitesInterface(){
        if (sitesInterface == null){
            sitesInterface = new SitesImpl();
        }
        return sitesInterface;
    }

    public static PersonsInterface getPersonsInterface(){
        if (personsInterface == null){
            personsInterface = new PersonsImpl();
        }
        return personsInterface;
    }

    public static PagesInterface getPagesInterface(){
        if (pagesInterface == null){
            pagesInterface = new PagesImpl();
        }
        return pagesInterface;
    }

    public static PersonPageRankInterface getPersonPageRankInterface(){
        if (personPageRankInterface == null){
            personPageRankInterface = new PersonPageRankImpl();
        }
        return personPageRankInterface;
    }
}
