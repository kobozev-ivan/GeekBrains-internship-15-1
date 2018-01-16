package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface PersonsInterface {
    public Persons addPerson(Persons person) throws SQLException;
    public boolean deletePerson(int ID) throws SQLException;
    public void updatePerson(int ID, Persons person) throws SQLException;
    public List<Persons> getAllPersons(int[] ID) throws SQLException;
}