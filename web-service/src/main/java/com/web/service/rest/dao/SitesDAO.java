package com.web.service.rest.dao;

import com.web.service.hibernate.Factory;
import com.web.service.hibernate.Sites;
import com.web.service.hibernate.SitesInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitesDAO implements SitesDAOInterface {

    public Sites createSite(String URL) {
        Sites site = new Sites();
        try {
            site = Factory.getInstance().getSitesInterface().addSite(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return site;
    }

    public Boolean removeSite(int ID) throws SQLException {
        if (Factory.getInstance().getSitesInterface().deleteSite(ID)) {
           return true;
        }
        else {return false;}
    }

    public Sites updateSite(String url, int ID) {
        Sites site = new Sites();
        try {
            site = Factory.getInstance().getSitesInterface().updateSite(ID, url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return site;
    }

    public List<Sites> getAllSites(int[] ID) {
        List<Sites> sitesList = new ArrayList<Sites>();
        try {
            sitesList = Factory.getInstance().getSitesInterface().getAllSites(ID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitesList;
    }

    public Sites getSite(int ID) {
        Sites site = new Sites();
        try {
            site = Factory.getInstance().getSitesInterface().getSite(ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return site;
    }
}
