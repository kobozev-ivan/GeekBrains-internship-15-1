package com.web.service.hibernate;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public interface KeywordsInterface {
    public Keywords addKeyword(Keywords keyword) throws SQLException;
    public boolean deleteKeyword(int ID) throws SQLException;
    public void updateKeyword(int ID, Keywords keyword) throws SQLException;
    public List<Keywords> getKeywordByPerson(int personID) throws SQLException;
}
