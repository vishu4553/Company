package com.mitroz.bloodbank.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class QueAnsModel  implements Serializable {
    int queId;
    String question;
    String ans;
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

    public int getQueId() {
        return queId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return ans;
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

    public void setQueId(int queId) {
        this.queId = queId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.ans = answer;
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
