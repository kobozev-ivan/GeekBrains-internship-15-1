package com.web.service.hibernate;

import org.apache.abdera.model.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class PersonsImpl implements PersonsInterface {
    public Persons addPerson(Persons person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при добавлении " + person.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return person;
    }

    public boolean deletePerson(int ID) throws SQLException {
        Session session = null;
        Persons person = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            person = session.load(Persons.class, ID);
            session.delete(person);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении " + person.getName() + "!", JOptionPane.OK_OPTION);
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    public void updatePerson(int ID, Persons person) throws SQLException {
        Session session = null;
        Persons persons = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            persons = session.load(Persons.class, ID);
            session.update(person.getName(), persons);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка обновления " + person.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Persons> getAllPersons(int[] ID) throws SQLException {
        Session session = null;
        List<Persons> personsList = new ArrayList<Persons>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            int[] IDs = ID;
            for (int id : IDs){
                Query query = session.createQuery("SELECT p from Persons p where ID = " + id);
                personsList.add((Persons) query.list());
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка извлечения списка персон!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return personsList;
    }
}
