package com.web.service.rest.dao;

import com.web.service.hibernate.Keywords;

import java.util.List;

public interface KeywordsDAOInterface {
    Keywords createKeyword(String keyword);
    boolean removeKeyword(int ID);
    Keywords updateKeyword(int ID, Keywords keyword);
    List<Keywords> getAllKeywords(int[] ID);
}
