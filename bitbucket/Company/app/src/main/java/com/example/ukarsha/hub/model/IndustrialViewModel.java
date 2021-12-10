package com.example.ukarsha.hub.model;

/**
 * Created by Ukarsha on 14-Oct-19.
 */

public class IndustrialViewModel {
    String success, status, message;
    private IndustrialView industrial_visit_view;

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

    public IndustrialView getIndustrial_visit_view() {
        return industrial_visit_view;
    }

    public void setIndustrial_visit_view(IndustrialView industrial_visit_view) {
        this.industrial_visit_view = industrial_visit_view;
    }

    public class IndustrialView{
        String comp_id, name, posted_date, training_info, visit_date, status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
