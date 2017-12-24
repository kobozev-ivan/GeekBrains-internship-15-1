package com.web.service;

import java.util.Set;

/**
 * Created by DRSPEED-PC on 18.12.2017.
 */
public class PersonPageRank {
    public int ID;
    public int PersonID;
    public int PageID;
    public int Rank;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public int getPageID() {
        return PageID;
    }

    public void setPageID(int pageID) {
        PageID = pageID;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }
}
