package com.web.service.rest.dao;

import com.web.service.hibernate.PersonPageRank;

import java.util.List;

public interface PersonPageRankDAOInterface {
    PersonPageRank createPageRank(int pageID, int personID, int rank);
    boolean removePageRank(int ID);
    PersonPageRank updatePageRank(int ID, PersonPageRank personPageRank);
    List<PersonPageRank> getAllRanks(int[] ID);
}
