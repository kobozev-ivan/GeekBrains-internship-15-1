package com.web.service.rest;

import com.web.service.rest.dao.PersonsDAOInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class PersonsServiceJSON implements PersonsServiceInterface {
    // link to our dao object
    private PersonsDAOInterface personsDAOInterface;

    public PersonsDAOInterface getPersonsInterface() {
        return personsDAOInterface;
    }

    public void setPersonsInterface(PersonsDAOInterface personsDAOInterface) {
        this.personsDAOInterface = personsDAOInterface;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    public Response createPerson(String name) {
        return null;
    }

    public Response removePerson(int ID) {
        return null;
    }

    public Response updatePerson(int ID, String name) {
        return null;
    }

    public Response getAllPersons(int[] ID) {
        return null;
    }
}
