package com.example.ukarsha.hub.model;

import java.io.Serializable;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class IndustrialVisit1 implements Serializable {
    String industrial_id, comp_id, posted_date, training_info, visit_date, name;

    public String getIndustrial_id() {
        return industrial_id;
    }

    public void setIndustrial_id(String industrial_id) {
        this.industrial_id = industrial_id;
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

    public String getTraining_info() {
        return training_info;
    }

    public void setTraining_info(String training_info) {
        this.training_info = training_info;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
