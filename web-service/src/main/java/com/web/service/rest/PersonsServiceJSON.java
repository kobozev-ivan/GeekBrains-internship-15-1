package com.web.service.rest;

import com.web.service.hibernate.Persons;
import com.web.service.rest.dao.PersonsDAOInterface;
import com.web.service.rest.exceptions.Error;
import com.web.service.rest.response.ResponseCreator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

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

    @POST
    @Path(value = "/api/v1/persons")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(@QueryParam("name") String name) {
        System.out.println("POST");
        Persons crPerson = personsDAOInterface.createPerson(name);
        if (crPerson != null) {
            return ResponseCreator.success(getHeaderVersion(), crPerson);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @DELETE
//    @Path(value = "/api/v1/persons/{id}")
    @Path(value = "/api/v1/persons")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removePerson(@PathParam("id") int ID) throws SQLException {
    public Response removePerson(@QueryParam("id") int ID) throws SQLException {
        System.out.println("DELETE");
        if (personsDAOInterface.removePerson(ID)) {
            return ResponseCreator.success(getHeaderVersion(), "removed");
        } else {
            return ResponseCreator.success(getHeaderVersion(), "no such id");
        }
    }

    @PUT
    @Path(value = "/api/v1/persons")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@QueryParam("id") int ID,
                                 @QueryParam("name") String name) {
        System.out.println("PUT");
        Persons udPerson = personsDAOInterface.updatePerson(ID, name);
        if (udPerson != null) {
            return ResponseCreator.success(getHeaderVersion(), udPerson);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(),getHeaderVersion());
        }
    }

    @GET
    @Path(value = "/api/v1/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons(int[] ID) {
        System.out.println("GET");
        List<Persons> personsList = personsDAOInterface.getAllPersons(ID);
        if (personsList != null) {
            GenericEntity<List<Persons>> entity = new GenericEntity<List<Persons>>(personsList) {
            };
            return ResponseCreator.success(getHeaderVersion(), entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
        }
    }
}
