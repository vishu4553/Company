package com.example.ukarsha.hub.model;

import java.util.List;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class IndustrialListModel {
    String status, success, message;
    List<IndustrialVisit1> industrial_visit;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<IndustrialVisit1> getIndustrial_visit() {
        return industrial_visit;
    }

    public void setIndustrial_visit(List<IndustrialVisit1> industrial_visit) {
        this.industrial_visit = industrial_visit;
    }
}
