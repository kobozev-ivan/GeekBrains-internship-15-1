package com.web.service.rest.dao;

import com.web.service.hibernate.Factory;
import com.web.service.hibernate.Pages;
import com.web.service.hibernate.PagesInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PagesDAO implements PagesDAOInterface{

    public Pages createPage(String URL, int siteID, Date found, Date lastScan) {
        Pages page = new Pages();
        page.setURL(URL);
        page.setSiteID(siteID);
        page.setFound(found.toString());
        page.setLastScan(lastScan.toString());
        try {
            Factory.getInstance().getPagesInterface().addPage(page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removePage(int ID) throws SQLException {
        if (Factory.getInstance().getPagesInterface().deletePage(ID)){
            return true;
        }
        else {
            return false;
        }
    }

    public Pages updatePage(int ID, String URL, int siteID, Date found, Date lastScan) {
        Pages page = new Pages();
        page.setURL(URL);
        page.setSiteID(siteID);
        page.setFound(found.toString());
        page.setLastScan(lastScan.toString());
        try {
            Factory.getInstance().getPagesInterface().updatePage(ID, page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return page;
    }

    public List<Pages> getAllPagesBySite(int siteID) {
        List<Pages> pagesList = new ArrayList<Pages>();
        try {
            pagesList = Factory.getInstance().getPagesInterface().getAllPagesBySite(siteID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagesList;
    }
}
