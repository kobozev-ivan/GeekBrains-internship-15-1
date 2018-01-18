package com.web.service.rest;

import com.web.service.hibernate.Sites;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public interface StiesServiceInterface {
    Response createSite(String URL) throws SQLException;
    Response removeSite(int ID) throws SQLException;
    Response updateSite(String url, int ID);
    Response getAllSites(int[] ID);
    Response getSite(int id);
}
