package com.web.service.rest.dao;

import com.web.service.hibernate.Factory;
import com.web.service.hibernate.PersonPageRank;
import com.web.service.hibernate.PersonPageRankInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonPageRankDAO implements PersonPageRankDAOInterface{

    public PersonPageRank createPageRank(int pageID, int personID, int rank) {
        PersonPageRank personPageRank = new PersonPageRank();
        personPageRank.setPageID(pageID);
        personPageRank.setPersonID(personID);
        personPageRank.setRank(rank);
        try {
            Factory.getInstance().getPersonPageRankInterface().addRank(personPageRank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removePageRank(int ID) throws SQLException {
        if (Factory.getInstance().getPersonPageRankInterface().deleteRank(ID)) {
            return true;
        }
        else {return false;}
    }

    public PersonPageRank updatePageRank(int ID, int personID, int pageID, int rank) {
        PersonPageRank personPageRank = new PersonPageRank();
        personPageRank.setPersonID(personID);
        personPageRank.setPageID(pageID);
        personPageRank.setRank(rank);
        try {
            Factory.getInstance().getPersonPageRankInterface().updateRank(ID, personPageRank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personPageRank;
    }

    public List<PersonPageRank> getAllRanksByPerson(int personID) {
        List<PersonPageRank> personPageRankList = new ArrayList<PersonPageRank>();
        try {
            personPageRankList = Factory.getInstance().getPersonPageRankInterface().getAllRanksByPersons(personID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personPageRankList;
    }

    public List<PersonPageRank> getAllRanksByPage(int pageID) {
        List<PersonPageRank> personPageRankList = new ArrayList<PersonPageRank>();
        try {
            personPageRankList = Factory.getInstance().getPersonPageRankInterface().getAllRanksByPage(pageID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personPageRankList;
    }
}
