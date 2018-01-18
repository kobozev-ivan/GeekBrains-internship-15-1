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
public class SitesImpl implements SitesInterface {
    public Sites addSite(String URL) throws SQLException {
        Session session = null;
        Sites site = new Sites();
        site.setName(URL);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(site);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при добавлении сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return site;
    }

    public boolean deleteSite(int ID) throws SQLException {
        Session session = null;
        Sites site = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            site = session.load(Sites.class, ID);
            session.delete(site);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
            return false;
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return true;
    }

    public Sites updateSite(int ID, String url) throws SQLException {
        Session session = null;
        Sites sites = new Sites();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            sites = session.load(Sites.class, ID);
            session.update(url, sites);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при попытке изменения сайта " + url + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return sites;
    }

    public List<Sites> getAllSites(int[] ID) throws SQLException {
        Session session = null;
        List<Sites> sitesList = new ArrayList<Sites>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            int[] IDs = ID;
            for (int id : IDs){
                Query query = session.createQuery("SELECT s from Sites s where ID = " + id);
                sitesList.add((Sites) query.list());
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка извлечения списка сайтов!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return sitesList;
    }

    public Sites getSite(int ID) throws SQLException {
        Session session = null;
        Sites site = new Sites();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            site = session.load(Sites.class, ID);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при извлечении сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return site;
    }
}
