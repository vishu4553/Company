package com.example.ukarsha.hub.model;

import java.util.List;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class JbTypeModel{
    String status, success, message;
    List<JobTypeResult> jobtype_result;

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

    public List<JobTypeResult> getJobtype_result() {
        return jobtype_result;
    }

    public void setJobtype_result(List<JobTypeResult> jobtype_result) {
        this.jobtype_result = jobtype_result;
    }
}
