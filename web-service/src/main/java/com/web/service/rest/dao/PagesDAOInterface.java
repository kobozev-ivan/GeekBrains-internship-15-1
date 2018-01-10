package com.web.service.rest.dao;

import com.web.service.hibernate.Pages;

import java.util.List;

public interface PagesDAOInterface {
    Pages createPage(String URL);
    boolean removePage(int ID);
    Pages updatePage(int ID, Pages page);
    List<Pages> getAllPages(int[] ID);
}
