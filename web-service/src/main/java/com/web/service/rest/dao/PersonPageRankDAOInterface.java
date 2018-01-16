package com.web.service.rest.dao;

import com.web.service.hibernate.PersonPageRank;

import java.sql.SQLException;
import java.util.List;

public interface PersonPageRankDAOInterface {
    PersonPageRank createPageRank(int pageID, int personID, int rank);
    boolean removePageRank(int ID) throws SQLException;
    PersonPageRank updatePageRank(int ID, int personID, int pageID,int rank);
    List<PersonPageRank> getAllRanksByPerson(int personID);
    List<PersonPageRank> getAllRanksByPage(int pageID);
}
