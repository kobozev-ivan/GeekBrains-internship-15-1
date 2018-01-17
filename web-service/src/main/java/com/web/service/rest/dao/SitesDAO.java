package com.web.service.rest.dao;

import com.web.service.hibernate.Sites;
import com.web.service.hibernate.SitesInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitesDAO implements SitesDAOInterface {

    private SitesInterface sitesInterface;

    public Sites createSite(String URL) {
        try {
            sitesInterface.addSite(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean removeSite(int ID) throws SQLException {
        if (sitesInterface.deleteSite(ID)) {
           return true;
        }
        else {return false;}
    }

    public Sites updateSite(Sites site, int ID) {
        try {
            sitesInterface.updateSite(ID, site);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return site;
    }

    public List<Sites> getAllSites(int[] ID) {
        List<Sites> sitesList = new ArrayList<Sites>();
        try {
            sitesList = sitesInterface.getAllSites(ID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sitesList;
    }
}
