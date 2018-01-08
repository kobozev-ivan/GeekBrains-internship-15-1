package com.web.service.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by DRSPEED-PC on 18.12.2017.
 */
@XmlRootElement(name = "keywords")

@Entity
public class Keywords {

    @Id
    public int ID;

    public int PersonID;

    public String Name;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
