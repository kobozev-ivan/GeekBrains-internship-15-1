package com.web.service.rest.dao;

import com.web.service.hibernate.Persons;
import com.web.service.hibernate.PersonsInterface;

import java.sql.SQLException;
import java.util.ArrayList;
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
        if (personsInterface.deletePerson(ID)) {
            return true;
        }
        else {return false;}
    }

    public Persons updatePerson(int ID, String person) {
        Persons targetPerson = new Persons();
        targetPerson.setName(person);
        try {
            personsInterface.updatePerson(ID, targetPerson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targetPerson;
    }

    public List<Persons> getAllPersons(int[] ID) {
        List<Persons> personsList = new ArrayList<Persons>();
        try{
            personsList = personsInterface.getAllPersons(ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personsList;
    }
}
