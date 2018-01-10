package com.web.service.rest;

import javax.ws.rs.core.Response;

public interface PersonPageRankServiceInterface {
    Response createPageRank(int pageID, int personID, int rank);
    Response removePageRank(int ID);
    Response updatePageRank(int ID, int pageID, int personID, int rank);
    Response getAllRanks(int[] ID);
}
