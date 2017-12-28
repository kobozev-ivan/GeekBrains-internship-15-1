package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface SitesInterface {
    public Sites addSite(Sites site) throws SQLException;
    public Boolean deleteSite(int ID) throws SQLException;
    public void updateSite(int ID, Sites site) throws SQLException;
    public List<Sites> getAllSites(int[] ID) throws SQLException;
}