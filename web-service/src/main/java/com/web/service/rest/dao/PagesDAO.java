package com.web.service.rest.dao;

import com.web.service.hibernate.Pages;
import com.web.service.hibernate.PagesInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PagesDAO implements PagesDAOInterface{

    private PagesInterface pagesInterface;

    public Pages createPage(String URL, int siteID, Date found, Date lastScan) {
        Pages page = new Pages();
        page.setURL(URL);
        page.setSiteID(siteID);
        page.setFound(found.toString());
        page.setLastScan(lastScan.toString());
        try {
            pagesInterface.addPage(page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removePage(int ID) throws SQLException {
        if (pagesInterface.deletePage(ID)){
            return true;
        }
        else {
            return false;
        }
    }

    public void updatePage(int ID, Pages page) {
        try {
            pagesInterface.updatePage(ID, page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pages> getAllPagesBySite(int siteID) {
        List<Pages> pagesList = new ArrayList<Pages>();
        try {
            pagesList = pagesInterface.getAllPagesBySite(siteID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagesList;
    }
}
