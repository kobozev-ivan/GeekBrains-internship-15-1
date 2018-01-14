package com.web.service.rest.dao;

import com.web.service.hibernate.Pages;

import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public interface PagesDAOInterface {
    Pages createPage(String URL, int siteID, Date found, Date lastScan);
    boolean removePage(int ID) throws SQLException;
    Pages updatePage(int ID, String URL, int siteID, Date found, Date lastScan);
    List<Pages> getAllPagesBySite(int siteID);
}
