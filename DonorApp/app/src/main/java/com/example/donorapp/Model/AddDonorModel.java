package com.mitroz.bloodbank.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDonorModel {

    @SerializedName("DonorName")
    @Expose
    private String DonorName;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Contact")
    @Expose
    private String Contact;
    @SerializedName("DonationDate")
    @Expose
    private String DonationDate;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Dob")
    @Expose
    private String Dob;
    @SerializedName("Age")
    @Expose
    private Integer Age;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Weight")
    @Expose
    private Integer Weight;
    @SerializedName("Height")
    @Expose
    private String Height;
    @SerializedName("BloodGroup")
    @Expose
    private String BloodGroup;
    @SerializedName("BloodPressure")
    @Expose
    private String BloodPressure;
    @SerializedName("CampCode")
    @Expose
    private String CampCode;
    @SerializedName("Sign")
    @Expose
    private String Sign;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("httpStatusCode")
    @Expose
    private Integer httpStatusCode;

    public String getDonorName() {
        return DonorName;
    }

    public String getAddress() {
        return Address;
    }

    public String getContact() {
        return Contact;
    }

    public String getDonationDate() {
        return DonationDate;
    }

    public String getEmail() {
        return Email;
    }

    public String getDob() {
        return Dob;
    }

    public Integer getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public Integer getWeight() {
        return Weight;
    }

    public String getHeight() {
        return Height;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public String getBloodPressure() {
        return BloodPressure;
    }

    public String getCampCode() {
        return CampCode;
    }

    public String getSign() {
        return Sign;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setDonorName(String donorName) {
        DonorName = donorName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void setDonationDate(String donationDate) {
        DonationDate = donationDate;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setWeight(Integer weight) {
        Weight = weight;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public void setBloodPressure(String bloodPressure) {
        BloodPressure = bloodPressure;
    }

    public void setCampCode(String campCode) {
        CampCode = campCode;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
