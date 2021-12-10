package com.example.ukarsha.hub.callback;

import com.android.volley.VolleyError;

/**
 * Created by satyam on 15/07/2017.
 */

public interface VolleyCallbackString {
    void onSuccess(String response);

    void onError(VolleyError error);
}
