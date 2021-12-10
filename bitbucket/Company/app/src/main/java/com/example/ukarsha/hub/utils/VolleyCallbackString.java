package com.example.ukarsha.hub.utils;

import com.android.volley.VolleyError;

/**
 * Created by devanand on 15/07/2017.
 */

public interface VolleyCallbackString {
    void onSuccess(String response);

    void onError(VolleyError error);
}
