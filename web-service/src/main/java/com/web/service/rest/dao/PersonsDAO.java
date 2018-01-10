package com.web.service.rest.dao;

import com.web.service.hibernate.Persons;
import com.web.service.hibernate.PersonsInterface;

import java.sql.SQLException;
import java.util.List;

public class PersonsDAO implements PersonsDAOInterface{

    private PersonsInterface personsInterface;

    public Persons createPerson(String name) {
        Persons persons = new Persons();
        persons.setName(name);
        try {
            personsInterface.addPerson(persons);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public boolean removePerson(int ID) throws SQLException {
        Persons persons = new Persons();
        persons.setID(ID);
        if (personsInterface.deletePerson(persons)){
            return true;
        }
        else {
            return false;
        }
    }

    public Persons updatePerson(int ID, Persons person) {
        return null;
    }

    public List<Persons> getAllPersons(int[] ID) {
        return null;
    }
}
