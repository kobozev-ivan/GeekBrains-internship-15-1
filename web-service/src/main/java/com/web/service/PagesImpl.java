package com.web.service;

import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
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

    }

    public void updatePage(int ID, Pages page) throws SQLException {

    }

    public List<Pages> getAllPagesBySite(Sites site) throws SQLException {
        return null;
    }
}
