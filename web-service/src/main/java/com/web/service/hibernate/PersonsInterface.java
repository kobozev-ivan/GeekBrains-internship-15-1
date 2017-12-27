package com.web.service.hibernate;

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