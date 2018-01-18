package com.web.service.rest;

import com.web.service.hibernate.Sites;
import com.web.service.rest.dao.SitesDAOInterface;
import com.web.service.rest.response.ResponseCreator;
import com.web.service.rest.exceptions.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SitesServiceJSON implements StiesServiceInterface {

    Logger logger = Logger.getLogger(SitesServiceJSON.class.getName());
    // link to our dao object
    private SitesDAOInterface sitesDAOInterface;

    public SitesDAOInterface getSitesInterface() {
        return sitesDAOInterface;
    }

    public void setSitesInterface(SitesDAOInterface sitesDAOInterface) {
        this.sitesDAOInterface = sitesDAOInterface;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("Date").get(0);
    }

    // create row representing sites and returns created sites as
    // object->JSON structure
    @POST
    @Path(value = "/api/v1")
//    @Consumes({MediaType.APPLICATION_JSON})
    @Consumes(value={"application/json"})
//    @Produces(value={"text/xml", "application/json"})
    public Response createSite(@QueryParam("URL") String URL) throws SQLException {
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.WARNING);
        logger.log(Level.WARNING, "POST: " + URL);
        Sites crsites = sitesDAOInterface.createSite(URL);
        if (crsites != null) {
            return ResponseCreator.success(crsites);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode());
        }
    }

    // remove row from the sites table according with passed id and returned
    // status message in body
    @DELETE
//    @Path(value = "/api/v1/{id}")
    @Path(value = "/api/v1")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removeSite(@PathParam("id") int id) throws SQLException {
    public Response removeSite(@QueryParam("id") int id) throws SQLException {
        System.out.println("DELETE");
        if (sitesDAOInterface.removeSite(id)) {
            return ResponseCreator.success("removed");
        } else {
            return ResponseCreator.success("no such id");
        }
    }

    // update row and return previous version of row representing sites as
    // object->JSON structure
    @PUT
    @Path(value = "/api/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSite(String url, int ID) {
        System.out.println("PUT");
        Sites udsite = sitesDAOInterface.updateSite(url, ID);
        if (udsite != null) {
            return ResponseCreator.success(udsite);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode());
        }
    }

    // returns list of sites meeting query params
    @GET
    @Path(value = "/api/v1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSites(int[] ID)
    //                                 @QueryParam("pagenum") Integer pageNum,
    //                                 @QueryParam("pagesize") Integer pageSize)
    {
        System.out.println("GET");
        List<Sites> listSites = sitesDAOInterface.getAllSites(ID);
        if (listSites != null) {
            GenericEntity<List<Sites>> entity = new GenericEntity<List<Sites>>(listSites) {
            };
            return ResponseCreator.success(entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode());
        }
    }

    // returns list of sites meeting query params
    @GET
    @Path(value = "/api/v1/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSite(@PathParam("id") int ID) {
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.WARNING);
        logger.log(Level.WARNING, "GET: " + ID);
        Sites site = sitesDAOInterface.getSite(ID);
        if (site != null) {
            return ResponseCreator.success(site);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode());
        }
    }
}
