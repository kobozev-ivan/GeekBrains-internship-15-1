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
public interface KeywordsInterface {
    public void addKeyword(Keywords keyword) throws SQLException;
    public void deleteKeyword(Keywords keywords) throws SQLException;
    public void updateKeyword(int ID, Keywords keyword) throws SQLException;
    public List<Keywords> getKeywordByPerson(Persons person) throws SQLException;
}
