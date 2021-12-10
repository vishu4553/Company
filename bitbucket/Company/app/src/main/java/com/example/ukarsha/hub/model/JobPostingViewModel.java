package com.example.ukarsha.hub.model;

/**
 * Created by Ukarsha on 14-Oct-19.
 */

public class JobPostingViewModel {
    String success, status, message;
    private JobPostingViw job_posting_view;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobPostingViw getJob_posting_view() {
        return job_posting_view;
    }

    public void setJob_posting_view(JobPostingViw job_posting_view) {
        this.job_posting_view = job_posting_view;
    }

    public class JobPostingViw{
        String comp_id, name, posted_date, job_title, technology, type_id, status, other, vacancies, end_date;

        public String getComp_id() {
            return comp_id;
        }

        public void setComp_id(String comp_id) {
            this.comp_id = comp_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosted_date() {
            return posted_date;
        }

        public void setPosted_date(String posted_date) {
            this.posted_date = posted_date;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getVacancies() {
            return vacancies;
        }

        public void setVacancies(String vacancies) {
            this.vacancies = vacancies;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
    }
}
