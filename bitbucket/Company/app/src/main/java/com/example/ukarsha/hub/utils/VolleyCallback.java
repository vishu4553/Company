package com.example.ukarsha.hub.utils;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject response);

    void onError(VolleyError error);
}