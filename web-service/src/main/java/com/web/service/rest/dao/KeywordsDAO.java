package com.web.service.rest.dao;

import com.web.service.hibernate.Factory;
import com.web.service.hibernate.Keywords;
import com.web.service.hibernate.KeywordsInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeywordsDAO implements KeywordsDAOInterface {

    public Keywords createKeyword(String keyword, int personID) {
        Keywords targetKeyword = new Keywords();
        targetKeyword.setName(keyword);
        targetKeyword.setPersonID(personID);
        try {
            Factory.getInstance().getKeywordsInterface().addKeyword(targetKeyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targetKeyword;
    }

    public boolean removeKeyword(int ID) throws SQLException {
        if (Factory.getInstance().getKeywordsInterface().deleteKeyword(ID)){
            return true;
        }
        else {
            return false;
        }
    }

    public Keywords updateKeyword(int ID, String keyword, int personID) {
        Keywords targetKeyword = new Keywords();
        targetKeyword.setName(keyword);
        targetKeyword.setPersonID(personID);
        try {
            Factory.getInstance().getKeywordsInterface().updateKeyword(ID, targetKeyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targetKeyword;
    }

    public List<Keywords> getAllKeywordsByPerson(int personID) {
        List<Keywords> keywordsList = new ArrayList<Keywords>();
        try {
            keywordsList = Factory.getInstance().getKeywordsInterface().getKeywordByPerson(personID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordsList;
    }
}
