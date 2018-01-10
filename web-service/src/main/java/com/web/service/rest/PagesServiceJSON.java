package com.web.service.rest;

import com.web.service.rest.dao.PagesDAOInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class PagesServiceJSON implements PagesServiceInterface {
    // link to our dao object
    private PagesDAOInterface pagesDAOInterface;

    public PagesDAOInterface getPagesInterface() {
        return pagesDAOInterface;
    }

    public void setPagesInterface(PagesDAOInterface pagesDAOInterface) {
        this.pagesDAOInterface = pagesDAOInterface;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    public Response createPage(String name) {
        return null;
    }

    public Response removePage(int ID) {
        return null;
    }

    public Response updatePage(int ID, String name) {
        return null;
    }

    public Response getAllPages(int[] ID) {
        return null;
    }
}
