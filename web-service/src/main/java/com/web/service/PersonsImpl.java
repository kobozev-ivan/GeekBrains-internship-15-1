package com.web.service;

import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class PersonsImpl implements PersonsInterface {
    public void addPerson(Persons person) throws SQLException {
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
    }

    public void deletePerson(Persons person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.load(Keywords.class, person.getName());
            session.delete(person);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении " + person.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
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

    public List<Persons> getAllPersons() throws SQLException {
        return null;
    }
}
