package com.example.ukarsha.hub.model;

import java.util.List;

/**
 * Created by Ukarsha on 27-Sep-19.
 */

public class JobPostingListModel {
    String status, success, message;
    List<JobPosting1> job_posting;

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

    public List<JobPosting1> getJob_posting() {
        return job_posting;
    }

    public void setJob_posting(List<JobPosting1> job_posting) {
        this.job_posting = job_posting;
    }
}
