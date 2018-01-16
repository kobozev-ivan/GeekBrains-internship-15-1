package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface SitesInterface {
    Sites addSite(Sites site) throws SQLException;
    boolean deleteSite(int ID) throws SQLException;
    void updateSite(int ID, Sites site) throws SQLException;
    List<Sites> getAllSites(int[] ID) throws SQLException;
}