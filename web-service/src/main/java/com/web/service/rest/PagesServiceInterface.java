package com.web.service.rest;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;

public interface PagesServiceInterface {
    Response createPage(String URL, int siteID, Date found, Date lastScan);
    Response removePage(int ID) throws SQLException;
    Response updatePage(int ID, String URL, int siteID, Date found, Date lastScan);
    Response getAllPagesBySite(int siteID);
}
