package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface PersonPageRankInterface {
    public void addRank(PersonPageRank personPageRank) throws SQLException;
    public void deleteRank(int ID) throws SQLException;
    public void updateRank(int ID, PersonPageRank personPageRank) throws SQLException;
    public List<PersonPageRank> getAllRanksByPersons(Persons person) throws SQLException;
    public List<PersonPageRank> getAllRanksByPage(Pages page) throws SQLException;
}