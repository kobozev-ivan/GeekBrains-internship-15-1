package com.web.service.rest.dao;

import com.web.service.hibernate.Sites;

import java.sql.SQLException;
import java.util.List;

public interface SitesDAOInterface {
    Sites createSite(String URL);
    Boolean removeSite(int ID) throws SQLException;
    Sites updateSite(Sites site, int ID);
    List<Sites> getAllSites(int[] ID);
}
