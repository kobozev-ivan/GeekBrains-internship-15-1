package com.web.service.hibernate;

import com.web.service.rest.adapters.JaxBDateAdapter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by DRSPEED-PC on 17.12.2017.
 */
@XmlRootElement(name = "pages")

@Entity
public class Pages {

    @Id
    public int id;

    public int siteid;

    public String url;

    public String found;

    public String lastscan;

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteID) {
        siteid = siteID;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

    @XmlJavaTypeAdapter(JaxBDateAdapter.class)

    public String getFound() {
        return found;
    }

    public void setFound(String Found) {
        found = Found;
    }

    public String getLastScan() {
        return lastscan;
    }

    public void setLastScan(String lastScan) {
        lastscan = lastScan;
    }
}
