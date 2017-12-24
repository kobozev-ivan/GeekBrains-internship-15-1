package com.web.service;

import com.web.service.Keywords;
import com.web.service.Pages;
import com.web.service.Persons;
import com.web.service.Sites;
import com.web.service.PersonPageRank;

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