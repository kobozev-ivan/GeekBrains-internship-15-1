package com.web.service.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class PersonPageRankImpl implements PersonPageRankInterface {
    public PersonPageRank addRank(PersonPageRank personPageRank) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(personPageRank);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при добавлении страницы " + personPageRank.getPageID() + " для пользователя " + personPageRank.getPersonID() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return personPageRank;
    }

    public boolean deleteRank(int ID) throws SQLException {
        Session session = null;
        PersonPageRank personPageRank = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            personPageRank = session.load(PersonPageRank.class, ID);
            session.delete(personPageRank);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении PersonPageRank" + personPageRank.getID() + "!", JOptionPane.OK_OPTION);
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    public void updateRank(int ID, PersonPageRank personPageRank) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("UPDATE PERSON_PAGE_RANK set personID = " + personPageRank.getPersonID() + ", pageID = "+ personPageRank.getPageID() + " where ID = " + ID);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при попытке изменения PersonPageRank " + ID + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public List<PersonPageRank> getAllRanksByPersons(int personID) throws SQLException {
        Session session = null;
        List<PersonPageRank> personPageRankList = new ArrayList<PersonPageRank>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Integer> IDs  = session.createQuery("SELECT ID from PERSON_PAGE_RANK where personID = " + personID).list();
            for (int id : IDs){
                Query query = session.createQuery("SELECT ppr from PERSON_PAGE_RANK ppr where ID = " + id);
                personPageRankList.add((PersonPageRank) query.list());
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка извлечения списка сайтов!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return personPageRankList;
    }

    public List<PersonPageRank> getAllRanksByPage(int pageID) throws SQLException {
        Session session = null;
        List<PersonPageRank> personPageRankList = new ArrayList<PersonPageRank>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Integer> IDs  = session.createQuery("SELECT ID from PERSON_PAGE_RANK where pageID = " + pageID).list();
            for (int id : IDs){
                Query query = session.createQuery("SELECT ppr from PERSON_PAGE_RANK ppr where ID = " + id);
                personPageRankList.add((PersonPageRank) query.list());
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка извлечения списка сайтов!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return personPageRankList;
    }
}
