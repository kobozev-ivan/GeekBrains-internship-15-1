package com.web.service.rest.dao;

import com.web.service.hibernate.Keywords;
import com.web.service.hibernate.KeywordsInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeywordsDAO implements KeywordsDAOInterface {

    private KeywordsInterface keywordsInterface;

    public Keywords createKeyword(String keyword, int personID) {
        Keywords targetKeyword = new Keywords();
        targetKeyword.setName(keyword);
        targetKeyword.setPersonID(personID);
        try {
            keywordsInterface.addKeyword(targetKeyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targetKeyword;
    }

    public boolean removeKeyword(int ID) throws SQLException {
        if (keywordsInterface.deleteKeyword(ID)){
            return true;
        }
        else {
            return false;
        }
    }

    public Keywords updateKeyword(int ID, Keywords keyword) {
        try {
            keywordsInterface.updateKeyword(ID, keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Keywords> getAllKeywordsByPerson(int personID) {
        List<Keywords> keywordsList = new ArrayList<Keywords>();
        try {
            keywordsList = keywordsInterface.getKeywordByPerson(personID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordsList;
    }
}
