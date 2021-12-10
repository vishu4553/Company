package com.example.ukarsha.hub.model;

import java.util.List;

/**
 * Created by Ukarsha on 14-Oct-19.
 */

public class JobAppList {
    String status, success, message;
    List<JobResult> job_result;

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

    public List<JobResult> getJob_result() {
        return job_result;
    }

    public void setJob_result(List<JobResult> job_result) {
        this.job_result = job_result;
    }
}
