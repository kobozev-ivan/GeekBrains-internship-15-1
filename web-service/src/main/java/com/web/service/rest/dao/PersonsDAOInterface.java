package com.web.service.rest.dao;

import com.web.service.hibernate.Persons;

import java.sql.SQLException;
import java.util.List;

public interface PersonsDAOInterface {
    Persons createPerson(String name);
    boolean removePerson(int ID) throws SQLException;
    Persons updatePerson(int ID, String person);
    List<Persons> getAllPersons(int[] ID);
}
