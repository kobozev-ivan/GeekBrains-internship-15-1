package com.web.service.rest;

import com.web.service.hibernate.PersonPageRank;
import com.web.service.rest.dao.PersonPageRankDAOInterface;
import com.web.service.rest.exceptions.Error;
import com.web.service.rest.response.ResponseCreator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

public class PersonPageRankServiceJSON implements PersonPageRankServiceInterface {
    // link to our dao object
    private PersonPageRankDAOInterface personPageRankDAOInterface;

    public PersonPageRankDAOInterface getPersonPageRankInterface() {
        return personPageRankDAOInterface;
    }

    public void setPersonPageRankInterface(PersonPageRankDAOInterface personPageRankDAOInterface) {
        this.personPageRankDAOInterface = personPageRankDAOInterface;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    @POST
    @Path(value = "/api/v1/personpagerank")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPageRank(@QueryParam("pageID") int pageID,
                                   @QueryParam("personID") int personID,
                                   @QueryParam("rank") int rank) {
        System.out.println("POST");
        PersonPageRank crPageRank = personPageRankDAOInterface.createPageRank(pageID, personID, rank);
        if (crPageRank != null) {
            return ResponseCreator.success(getHeaderVersion(), crPageRank);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @DELETE
//    @Path(value = "/api/v1/personpagerank/{id}")
    @Path(value = "/api/v1/personpagerank")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removePageRank(@PathParam("id") int ID) throws SQLException {
    public Response removePageRank(@QueryParam("id") int ID) throws SQLException {
        System.out.println("DELETE");
        if (personPageRankDAOInterface.removePageRank(ID)) {
            return ResponseCreator.success(getHeaderVersion(), "removed");
        } else {
            return ResponseCreator.success(getHeaderVersion(), "no such id");
        }
    }

    @PUT
    @Path(value = "/api/v1/personpagerank")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePageRank(@QueryParam("id")int ID,
                                   @QueryParam("pageID") int pageID,
                                   @QueryParam("personID") int personID,
                                   @QueryParam("rank") int rank) {
        System.out.println("PUT");
        PersonPageRank udPageRank = personPageRankDAOInterface.updatePageRank(ID, pageID, personID, rank);
        if (udPageRank != null) {
            return ResponseCreator.success(getHeaderVersion(), udPageRank);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @GET
//    @Path(value = "/api/v1/personpagerank/{personID}")
    @Path(value = "/api/v1/personpagerank")
    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllRanks(@PathParam("personID") int personID) {
    public Response getAllRanksByPerson(@QueryParam("personID") int personID) {
        System.out.println("GET");
        List<PersonPageRank> personPageRankList = personPageRankDAOInterface.getAllRanksByPerson(personID);
        if (personPageRankList != null) {
            GenericEntity<List<PersonPageRank>> entity = new GenericEntity<List<PersonPageRank>>(personPageRankList) {
            };
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
        }
    }

    @GET
//    @Path(value = "/api/v1/personpagerank/{pageID}")
    @Path(value = "/api/v1/personpagerank")
    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllRanks(@PathParam("pageID") int personID) {
    public Response getAllRanksByPage(@QueryParam("pageID") int pageID) {
        System.out.println("GET");
        List<PersonPageRank> personPageRankList = personPageRankDAOInterface.getAllRanksByPage(pageID);
        if (personPageRankList != null) {
            GenericEntity<List<PersonPageRank>> entity = new GenericEntity<List<PersonPageRank>>(personPageRankList) {
            };
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
        }
    }
}
