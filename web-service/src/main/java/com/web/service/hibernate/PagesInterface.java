package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface PagesInterface {
    public void addPage(Pages page) throws SQLException;
    public void deletePage(Pages page) throws SQLException;
    public void updatePage(int ID, Pages page) throws SQLException;
    public List<Pages> getAllPagesBySite(Sites site) throws SQLException;
}