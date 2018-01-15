package com.web.service.rest;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface PersonPageRankServiceInterface {
    Response createPageRank(int pageID, int personID, int rank);
    Response removePageRank(int ID) throws SQLException;
    Response updatePageRank(int ID, int pageID, int personID, int rank);
    Response getAllRanksByPerson(int personID);
    Response getAllRanksByPage(int pageID);
}
