package com.web.service.hibernate;

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
