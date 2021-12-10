package com.example.ukarsha.hub.model;

import java.io.Serializable;

/**
 * Created by Ukarsha on 25-Sep-19.
 */

public class UserResult implements Serializable {
    String id, lat, longg, cmpy_name, email, mobile, address, pwsd, city, website, cmpy_img, user_type, status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCmpy_name() {
        return cmpy_name;
    }

    public void setCmpy_name(String cmpy_name) {
        this.cmpy_name = cmpy_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwsd() {
        return pwsd;
    }

    public void setPwsd(String pwsd) {
        this.pwsd = pwsd;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
