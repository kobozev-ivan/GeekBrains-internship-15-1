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
public interface PersonsInterface {
    public void addPerson(Persons person) throws SQLException;
    public void deletePerson(Persons person) throws SQLException;
    public void updatePerson(int ID, Persons person) throws SQLException;
    public List<Persons> getAllPersons() throws SQLException;
}