package com.web.service.rest.dao;

import com.web.service.hibernate.Keywords;

import java.sql.SQLException;
import java.util.List;

public interface KeywordsDAOInterface {
    Keywords createKeyword(String keyword, int personID);
    boolean removeKeyword(int ID) throws SQLException;
    Keywords updateKeyword(int ID, String keyword, int personID);
    List<Keywords> getAllKeywordsByPerson(int personID);
}
