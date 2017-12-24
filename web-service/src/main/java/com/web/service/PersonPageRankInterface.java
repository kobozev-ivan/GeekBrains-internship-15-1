package com.web.service;

import com.web.service.Keywords;
import com.web.service.Pages;
import com.web.service.Persons;
import com.web.service.Sites;
import com.web.service.PersonPageRank;

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