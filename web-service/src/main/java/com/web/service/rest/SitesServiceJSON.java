package com.web.service.rest;

import com.web.service.hibernate.Sites;
import com.web.service.rest.dao.SitesDAOInterface;
import com.web.service.rest.response.ResponseCreator;
import com.web.service.rest.exceptions.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

public class SitesServiceJSON implements StiesServiceInterface {
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
        return requestHeaders.getRequestHeader("version").get(0);
    }

    // create row representing sites and returns created sites as
    // object->JSON structure
    @POST
    @Path(value = "/api/v1/sites")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSite(@QueryParam("URL") String URL) throws SQLException {
        System.out.println("POST");
        Sites crsites = sitesDAOInterface.createSite(URL);
        if (crsites != null) {
            return ResponseCreator.success(getHeaderVersion(), crsites);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    // remove row from the sites table according with passed id and returned
    // status message in body
    @DELETE
//    @Path(value = "/api/v1/sites/{id}")
    @Path(value = "/api/v1/sites")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removeSite(@PathParam("id") int id) throws SQLException {
    public Response removeSite(@QueryParam("id") int id) throws SQLException {
        System.out.println("DELETE");
        if (sitesDAOInterface.removeSite(id)) {
            return ResponseCreator.success(getHeaderVersion(), "removed");
        } else {
            return ResponseCreator.success(getHeaderVersion(), "no such id");
        }
    }

    // update row and return previous version of row representing sites as
    // object->JSON structure
    @PUT
    @Path(value = "/api/v1/sites")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSite(Sites sites, int ID) {
        System.out.println("PUT");
        Sites udsite = sitesDAOInterface.updateSite(sites, ID);
        if (udsite != null) {
            return ResponseCreator.success(getHeaderVersion(), udsite);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    // returns list of sites meeting query params
    @GET
    @Path(value = "/api/v1/sites")
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
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
        }
    }
}
