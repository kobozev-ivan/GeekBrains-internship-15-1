package com.web.service;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class KeywordImpl implements KeywordsInterface {

    public void addKeyword(Keywords keywords){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(keywords);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при добавлении ключевого слова " + keywords.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void deleteKeyword(Keywords keywords) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.load(Keywords.class, keywords.getName());
            session.delete(keywords);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении ключевого слова " + keywords.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateKeyword(int ID, Keywords keyword) throws SQLException {
        Session session = null;
        Keywords keywords = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            keywords = session.load(Keywords.class, ID);
            session.update(keyword.getName(), keywords);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка обновления ключевого слова " + keyword.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Keywords> getKeywordByPerson(Persons person) throws SQLException {
        Session session = null;
        List<Keywords> keywordsList= new ArrayList<Keywords>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            int personID = person.getID();
            Query query = session.createQuery("select k from Keywords k where personID = :personID").setHint("personID", personID);
            keywordsList = (List<Keywords>)query.list();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при извлечении списка ключевых слов для " + person.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return keywordsList;
    }
}