package com.web.service.rest.dao;

import com.web.service.hibernate.PersonPageRank;

import java.util.List;

public class PersonPageRankDAO implements PersonPageRankDAOInterface{
    public PersonPageRank createPageRank(int pageID, int personID, int rank) {
        return null;
    }

    public boolean removePageRank(int ID) {
        return false;
    }

    public PersonPageRank updatePageRank(int ID, int pageID, int personID, int rank) {
        return null;
    }

    public List<PersonPageRank> getAllRanks(int[] ID) {
        return null;
    }
}
