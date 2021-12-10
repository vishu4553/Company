package com.example.ukarsha.hub.activity;

/**
 * Created by Ukarsha on 15-Oct-19.
 */

public class NotifModel {
    String id, recipients, external_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }
}
