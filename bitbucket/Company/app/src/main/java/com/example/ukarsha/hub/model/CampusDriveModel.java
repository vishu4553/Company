package com.example.ukarsha.hub.model;

import java.util.List;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class CampusDriveModel {
    String status, success, message;
    List<CampusListResult> campus_list_result;

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

    public List<CampusListResult> getCampus_list_result() {
        return campus_list_result;
    }

    public void setCampus_list_result(List<CampusListResult> campus_list_result) {
        this.campus_list_result = campus_list_result;
    }
}
