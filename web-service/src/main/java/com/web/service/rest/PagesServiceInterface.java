package com.web.service.rest;

import javax.ws.rs.core.Response;

public interface PagesServiceInterface {
    Response createPage(String name);
    Response removePage(int ID);
    Response updatePage(int ID, String name);
    Response getAllPages(int[] ID);
}
