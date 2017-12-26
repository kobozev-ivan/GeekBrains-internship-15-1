package com.web.service;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DRSPEED-PC on 18.12.2017.
 */
@Entity
public class Sites {

    @Id
    public int ID;
    
    public String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
