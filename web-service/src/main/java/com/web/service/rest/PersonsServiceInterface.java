package com.web.service.rest;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface PersonsServiceInterface {
    Response createPerson(String name);
    Response removePerson(int ID) throws SQLException;
    Response updatePerson(int ID, String name);
    Response getAllPersons(int[] ID);
}
