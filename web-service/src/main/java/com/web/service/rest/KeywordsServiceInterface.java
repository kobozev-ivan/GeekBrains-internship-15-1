package com.web.service.rest;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface KeywordsServiceInterface {
    Response createKeyword(String keyword, int personID);
    Response removeKeyword(int ID) throws SQLException;
    Response updateKeyword(int ID, String keyword, int personID);
    Response getAllKeywordsByPerson(int personID);
}
