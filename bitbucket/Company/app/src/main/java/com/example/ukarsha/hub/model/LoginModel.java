package com.example.ukarsha.hub.model;

/**
 * Created by Ukarsha on 16-Sep-19.
 */

public class LoginModel {
    String status, success, message;
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        String comp_id, lat, longg, name, email, contact_no, address, password, city, website, cmpy_img;

        public String getComp_id() {
            return comp_id;
        }

        public void setComp_id(String comp_id) {
            this.comp_id = comp_id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLongg() {
            return longg;
        }

        public void setLongg(String longg) {
            this.longg = longg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getCmpy_img() {
            return cmpy_img;
        }

        public void setCmpy_img(String cmpy_img) {
            this.cmpy_img = cmpy_img;
        }
    }
}
