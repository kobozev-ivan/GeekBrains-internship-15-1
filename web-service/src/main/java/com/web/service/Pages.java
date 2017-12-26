package com.web.service;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DRSPEED-PC on 17.12.2017.
 */
@Entity
public class Pages {

    @Id
    public int ID;

    public String SiteID;

    public String URL;

    public String Found;

    public String LastScan;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSiteID() {
        return SiteID;
    }

    public void setSiteID(String siteID) {
        SiteID = siteID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getFound() {
        return Found;
    }

    public void setFound(String found) {
        Found = found;
    }

    public String getLastScan() {
        return LastScan;
    }

    public void setLastScan(String lastScan) {
        LastScan = lastScan;
    }
}
