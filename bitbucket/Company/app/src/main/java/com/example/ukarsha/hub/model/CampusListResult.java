package com.example.ukarsha.hub.model;

import java.io.Serializable;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class CampusListResult implements Serializable{
    String campus_id, comp_id, posted_date, registration_link, position, drive_info, name;

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getRegistration_link() {
        return registration_link;
    }

    public void setRegistration_link(String registration_link) {
        this.registration_link = registration_link;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDrive_info() {
        return drive_info;
    }

    public void setDrive_info(String drive_info) {
        this.drive_info = drive_info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
