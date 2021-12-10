package com.example.ukarsha.hub.callback;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by satyam on 10/07/2017.
 */

public interface VolleyCallBackArray {
    void onSuccess(JSONArray response);

    void onError(VolleyError error);
}
