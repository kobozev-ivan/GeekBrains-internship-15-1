package com.web.service.rest;

import com.web.service.rest.dao.PersonPageRankDAOInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

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

    public Response createPageRank(int pageID, int personID, int rank) {
        return null;
    }

    public Response removePageRank(int ID) {
        return null;
    }

    public Response updatePageRank(int ID, int pageID, int personID, int rank) {
        return null;
    }

    public Response getAllRanks(int[] ID) {
        return null;
    }
}
