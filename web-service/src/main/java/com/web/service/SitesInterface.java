package com.web.service;

import com.web.service.Keywords;
import com.web.service.Pages;
import com.web.service.Persons;
import com.web.service.Sites;
import com.web.service.PersonPageRank;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface SitesInterface {
    public void addSite(Sites site) throws SQLException;
    public void deleteSite(Sites site) throws SQLException;
    public void updateSite(int ID, Sites site) throws SQLException;
    public List<Sites> getAllSites(int[] ID) throws SQLException;
}