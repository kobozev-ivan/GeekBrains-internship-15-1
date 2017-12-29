package com.web.service.hibernate;

import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class PagesImpl implements PagesInterface {
    public void addPage(Pages page) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(page);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке URL: " + page.getURL() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void deletePage(Pages page) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.load(Pages.class, page.getID());
            session.delete(page);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении URL: " + page.getURL() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updatePage(int ID, Pages page) throws SQLException {
        Session session = null;
        Pages pages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.load(Pages.class, ID);
            session.update(page);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка обновления URL " + page.getURL() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Pages> getAllPagesBySite(Sites site) throws SQLException {
        Session session = null;
        List<Pages> pagesList = new ArrayList<Pages>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            int SiteID = site.getID();
            Query query = session.createQuery("SELECT p from Pages p where SiteID = :SiteID").setParameter("SiteID", SiteID);
            pagesList = (List<Pages>)query.list();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при извлечении списка URL сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return pagesList;
    }
}
