package com.example.ukarsha.hub.model;

import java.io.Serializable;

/**
 * Created by Ukarsha on 14-Oct-19.
 */

public class JobResult implements Serializable {
    String job_appln_id, comp_id, student_id, applied_for, current_location, pastexperience, description,student_name, name;

    public String getJob_appln_id() {
        return job_appln_id;
    }

    public void setJob_appln_id(String job_appln_id) {
        this.job_appln_id = job_appln_id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getApplied_for() {
        return applied_for;
    }

    public void setApplied_for(String applied_for) {
        this.applied_for = applied_for;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public String getPastexperience() {
        return pastexperience;
    }

    public void setPastexperience(String pastexperience) {
        this.pastexperience = pastexperience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
