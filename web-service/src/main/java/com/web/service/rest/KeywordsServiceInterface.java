package com.web.service.rest;

import javax.ws.rs.core.Response;

public interface KeywordsServiceInterface {
    Response createKeyword(String keyword);
    Response removeKeyword(int ID);
    Response updateKeyword(int ID, String keyword);
    Response getAllKeywords(int[] ID);
}
