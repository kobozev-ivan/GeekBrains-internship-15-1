package com.web.service.rest;

import com.web.service.rest.dao.KeywordsDAOInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

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

    public Response createKeyword(String keyword) {
        return null;
    }

    public Response removeKeyword(int ID) {
        return null;
    }

    public Response updateKeyword(int ID, String keyword) {
        return null;
    }

    public Response getAllKeywords(int[] ID) {
        return null;
    }
}
