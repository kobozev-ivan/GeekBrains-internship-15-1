package com.web.service.rest;

import com.web.service.hibernate.Keywords;
import com.web.service.rest.dao.KeywordsDAOInterface;
import com.web.service.rest.exceptions.Error;
import com.web.service.rest.response.ResponseCreator;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

public class KeywordsServiceJSON implements KeywordsServiceInterface {
    // link to our dao object
    private KeywordsDAOInterface keywordsDAOInterface;

    public KeywordsDAOInterface getKeywordsInterface() {
        return keywordsDAOInterface;
    }

    public void setKeywordsInterface(KeywordsDAOInterface keywordsDAOInterface) {
        this.keywordsDAOInterface = keywordsDAOInterface;
    }

    // for retrieving request headers from context
    // an injectable interface that provides access to HTTP header information.
    @Context
    private HttpHeaders requestHeaders;

    private String getHeaderVersion() {
        return requestHeaders.getRequestHeader("version").get(0);
    }

    @POST
    @Path(value = "/api/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createKeyword(@QueryParam("keyword") String keyword, @QueryParam("personID") int personID) {
        System.out.println("POST");
        Keywords crKeyword = keywordsDAOInterface.createKeyword(keyword, personID);
        if (crKeyword != null) {
            return ResponseCreator.success(crKeyword);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode());
        }
    }

    @DELETE
//    @Path(value = "/api/v1/{id}")
    @Path(value = "/api/v1")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response removeKeyword(@PathParam("id") int id) throws SQLException {
    public Response removeKeyword(@QueryParam("id") int id) throws SQLException {
        System.out.println("DELETE");
        if (keywordsDAOInterface.removeKeyword(id)) {
            return ResponseCreator.success("removed");
        } else {
            return ResponseCreator.success("no such id");
        }
    }

    @PUT
    @Path(value = "/api/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateKeyword(@QueryParam("id") int ID,
                                  @QueryParam("keyword") String keyword,
                                  @QueryParam("personID") int personID) {
        System.out.println("PUT");
        Keywords udKeyword = keywordsDAOInterface.updateKeyword(ID, keyword, personID);
        if (udKeyword != null) {
            return ResponseCreator.success(udKeyword);
        } else {
            return ResponseCreator.error(500, Error.SERVER_ERROR.getCode());
        }
    }

    @GET
//    @Path(value = "/api/v1/{personID}")
    @Path(value = "/api/v1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKeywordsByPerson(@QueryParam("personID") int personID) {
//    public Response getAllKeywordsByPerson(@PathParam("personID") int personID) {
        System.out.println("GET");
        List<Keywords> keywordsList = keywordsDAOInterface.getAllKeywordsByPerson(personID);
        if (keywordsList != null) {
            GenericEntity<List<Keywords>> entity = new GenericEntity<List<Keywords>>(keywordsList) {
            };
            return ResponseCreator.success(entity);
        } else {
            return ResponseCreator.error(404, Error.NOT_FOUND.getCode());
        }
    }
}
