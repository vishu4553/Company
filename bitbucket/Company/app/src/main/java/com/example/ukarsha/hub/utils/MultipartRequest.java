package com.example.ukarsha.hub.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MultipartRequest {
    private static MultipartRequest mInstance;
    private Context context;
    private String TAG;
    private AppCompatActivity mainContect;
    private ProgressDialog progressDialog;


    public MultipartRequest(AppCompatActivity context) {
        this.mainContect = context;
        mInstance = this;
        initProgress();
    }

    public MultipartRequest(Context context) {
        mInstance = this;
    }

    public MultipartRequest(Context context, String TAG) {
        this.context = context;
        mInstance = this;
        this.TAG = TAG;
    }

    public static synchronized MultipartRequest getInstance() {
        return mInstance;
    }

    /* For progressbar dialog*/
    public void initProgress() {
        if (mainContect == null) {
            progressDialog = new ProgressDialog(mainContect);
            progressDialog.setMessage(mainContect.getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(mainContect);
            progressDialog.setMessage(mainContect.getString(R.string.message_loading));
        }
    }

    public void showProgress(boolean show) {

        if (show) {
            if (progressDialog != null && !mainContect.isFinishing()) {
                progressDialog.show();
            }

        } else {
            if (progressDialog != null && progressDialog.isShowing() && !mainContect.isFinishing())
                progressDialog.dismiss();
        }

    }

    public void postDataMultipartSecure(String url, final Map<String, String> params, final Map<String, VolleyMultipartRequest.DataPart> paramDataPart, final VolleyCallback callback) {
        showProgress(true);
            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    showProgress(false);
                    JSONObject result = new JSONObject(resultResponse);
                    printLog("Response: " + result);
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                printLog("Error:" + error.getMessage());
                getServerError(error);
                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return paramDataPart;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("X-Access-Token", MyApplication.getInstance().getFromSharedPreference(AppConstants.USER_TOKEN));

                //headers.put("X-Access-Token", token);

                return headers;
            }

        };

        RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(policy);
        MyApplication.getInstance().addToRequestQueue(multipartRequest, "");
    }


    public void postDataMultipartSecureWithoutLoad(String url, final Map<String, String> params, final Map<String, VolleyMultipartRequest.DataPart> paramDataPart, final VolleyCallback callback) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    printLog("Response: " + result);
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLog("Error:" + error.getMessage());
                getServerError(error);
                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return paramDataPart;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("X-Access-Token", MyApplication.getInstance().getFromSharedPreference(AppConstants.USER_TOKEN));

                //headers.put("X-Access-Token", token);

                return headers;
            }

        };

        RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(policy);
        MyApplication.getInstance().addToRequestQueue(multipartRequest, "");
    }


    public void postDataMultipartSecureHideProgress(String url, final Map<String, String> params, final Map<String, VolleyMultipartRequest.DataPart> paramDataPart, final VolleyCallback callback) {
        showProgress(true);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    showProgress(false);
                    JSONObject result = new JSONObject(resultResponse);
                    printLog("Response: " + result);
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                printLog("Error:" + error.getMessage());
                getServerError(error);
                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return paramDataPart;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("X-Access-Token", MyApplication.getInstance().getFromSharedPreference(AppConstants.USER_TOKEN));

                //headers.put("X-Access-Token", token);

                return headers;
            }

        };

        RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(policy);
        MyApplication.getInstance().addToRequestQueue(multipartRequest, "");
    }


    private void printLog(String message) {
        Log.e("NetworkUtil", message);
    }


    public String getServerError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                printLog("ObjectError" + res);
                return res;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public void knowAboutError(VolleyError error) {
// As of f605da3 the following should work
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                printLog("ObjectError" + res);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        } else {
            printLog("Error is null");
        }
    }


/*     new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            NetworkResponse networkResponse = error.networkResponse;
            String errorMessage = "Unknown error";
            if (networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    errorMessage = "Request timeout";
                } else if (error.getClass().equals(NoConnectionError.class)) {
                    errorMessage = "Failed to connect server";
                }
            } else {
                String result = new String(networkResponse.data);
                try {
                    JSONObject response = new JSONObject(result);
                    String status = response.getString("status");
                    String message = response.getString("message");

                    Log.e("Error Status", status);
                    Log.e("Error Message", message);

                    if (networkResponse.statusCode == 404) {
                        errorMessage = "Resource not found";
                    } else if (networkResponse.statusCode == 401) {
                        errorMessage = message + " Please login again";
                    } else if (networkResponse.statusCode == 400) {
                        errorMessage = message + " Check your inputs";
                    } else if (networkResponse.statusCode == 500) {
                        errorMessage = message + " Something is getting wrong";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i("Error", errorMessage);
            error.printStackTrace();
        }
    })*/

}
