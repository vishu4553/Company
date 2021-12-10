package com.example.ukarsha.hub.model;

import java.io.Serializable;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class JobTypeResult implements Serializable{
    private String type_id, type;

    public JobTypeResult(String type_id, String type) {
        this.type_id = type_id;
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return getType();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof JobTypeResult){
            JobTypeResult c = (JobTypeResult) obj;
            if(c.getType_id().equals(type_id) && c.getType().equals(type))
                return true;
        }
        return false;
    }
}
