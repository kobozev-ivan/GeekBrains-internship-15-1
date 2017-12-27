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
    public void addSite(Sites site) throws SQLException {
        Session session = null;
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
    }

    public void deleteSite(Sites site) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.load(Sites.class, site.getName());
            session.delete(site);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }

    }

    public void updateSite(int ID, Sites site) throws SQLException {
        Session session = null;
        Sites sites = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            sites = session.load(Sites.class, ID);
            session.update(site.getName(), sites);
            session.getTransaction().commit();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при попытке изменения сайта " + site.getName() + "!", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }

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
}
