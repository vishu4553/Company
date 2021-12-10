package com.example.ukarsha.hub.model;

/**
 * Created by Ukarsha on 14-Oct-19.
 */

public class CampusViewModel {
    String success, status, message;
    private ViewCampus view_campus_drive;

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

    public ViewCampus getView_campus_drive() {
        return view_campus_drive;
    }

    public void setView_campus_drive(ViewCampus view_campus_drive) {
        this.view_campus_drive = view_campus_drive;
    }

    public class ViewCampus{
        String comp_id, name, posted_date, contact_no, registration_link, position, drive_info, end_date, status;

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

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getRegistration_link() {
            return registration_link;
        }

        public void setRegistration_link(String registration_link) {
            this.registration_link = registration_link;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getDrive_info() {
            return drive_info;
        }

        public void setDrive_info(String drive_info) {
            this.drive_info = drive_info;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
