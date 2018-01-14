package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface PersonPageRankInterface {
    public PersonPageRank addRank(PersonPageRank personPageRank) throws SQLException;
    public boolean deleteRank(int ID) throws SQLException;
    public void updateRank(int ID, PersonPageRank personPageRank) throws SQLException;
    public List<PersonPageRank> getAllRanksByPersons(int personID) throws SQLException;
    public List<PersonPageRank> getAllRanksByPage(int pageID) throws SQLException;
}