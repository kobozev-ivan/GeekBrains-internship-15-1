package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface SitesInterface {
    Sites addSite(String URL) throws SQLException;
    boolean deleteSite(int ID) throws SQLException;
    Sites updateSite(int ID, String url) throws SQLException;
    List<Sites> getAllSites(int[] ID) throws SQLException;
    Sites getSite(int ID) throws SQLException;
}