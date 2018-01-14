package com.web.service.rest;

import com.web.service.hibernate.Pages;
import com.web.service.rest.dao.PagesDAOInterface;
import com.web.service.rest.exceptions.Error;
import com.web.service.rest.response.ResponseCreator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

    @POST
    @Path(value = "/api/v1/pages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPage(@QueryParam("url") String URL,
                               @QueryParam("siteID") int siteID,
                               @QueryParam("dateFound") Date found,
                               @QueryParam("lastScan") Date lastScan) {
        System.out.println("POST");
        Pages crPage = pagesDAOInterface.createPage(URL, siteID, found, lastScan);
        if (crPage != null) {
            return ResponseCreator.success(getHeaderVersion(), crPage);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @DELETE
//    @Path(value = "/api/v1/pages/{id}")
    @Path(value = "/api/v1/pages")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removePage(@PathParam("id") int ID) throws SQLException {
    public Response removePage(@QueryParam("id") int ID) throws SQLException {
        System.out.println("DELETE");
        if (pagesDAOInterface.removePage(ID)) {
            return ResponseCreator.success(getHeaderVersion(), "removed");
        } else {
            return ResponseCreator.success(getHeaderVersion(), "no such id");
        }
    }

    @PUT
    @Path(value = "/api/v1/pages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePage(@QueryParam("id") int ID,
                               @QueryParam("url") String URL,
                               @QueryParam("siteID") int siteID,
                               @QueryParam("dateFound") Date found,
                               @QueryParam("lastScan") Date lastScan) {
        System.out.println("PUT");
        Pages udPage = pagesDAOInterface.updatePage(ID, URL, siteID, found, lastScan);
        if (udPage != null) {
            return ResponseCreator.success(getHeaderVersion(), udPage);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @GET
//    @Path(value = "/api/v1/pages/{siteID}")
    @Path(value = "/api/v1/pages")
    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllPagesBySite(@PathParam("siteID") int siteID) {
    public Response getAllPagesBySite(@QueryParam("siteID") int siteID) {
        System.out.println("GET");
        List<Pages> pagesList = pagesDAOInterface.getAllPagesBySite(siteID);
        if (pagesList != null) {
            GenericEntity<List<Pages>> entity = new GenericEntity<List<Pages>>(pagesList) {
            };
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
        }
    }
}
