package com.example.ukarsha.hub.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by satyam
 */
public class NetworkRequestUtil {
    private static NetworkRequestUtil mInstance;
    private AppCompatActivity context;
    private String TAG;
    private Context mainContect;
    private ProgressDialog progressDialog;
    private boolean isLoading;



  /*  public NetworkRequestUtil(DashboardMain context) {
        this.mainContect = context;
        mInstance = this;
        initProgress();
    }*/

    // for using no loading feature in request;
    public NetworkRequestUtil(AppCompatActivity context, boolean noLoading) {
        this.context = context;
        mInstance = this;
        this.isLoading = noLoading;
    }

    public NetworkRequestUtil(AppCompatActivity context) {
        this.context = context;
        mInstance = this;
        this.TAG = TAG;
        initProgress();
    }

    public static synchronized NetworkRequestUtil getInstance() {
        return mInstance;
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError || error instanceof AuthFailureError);
    }

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError || error instanceof NoConnectionError);
    }

    /* For progressbar dialog*/
    public void initProgress() {
        if (context == null) {
            progressDialog = new ProgressDialog(mainContect);
            progressDialog.setMessage(mainContect.getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.message_loading));
        }
    }

    public void setProgressMessage(String message) {
        progressDialog.setMessage(message);
    }

    public void setCancelable(boolean cancelable) {
        progressDialog.setCancelable(cancelable);
    }

    public void showProgress(ProgressDialog progressDialogTemp, boolean show) {

        if (show) {
            if (progressDialogTemp != null && !context.isFinishing()) {
                progressDialogTemp.show();
            }

        } else {
            if (progressDialogTemp != null && progressDialogTemp.isShowing() && !context.isFinishing())
                progressDialogTemp.dismiss();
        }

    }

    public void showProgress(boolean show) {

        if (show) {
            if (progressDialog != null && !context.isFinishing()) {
                progressDialog.show();
            }

        } else {
            if (progressDialog != null && progressDialog.isShowing() && !context.isFinishing())
                progressDialog.dismiss();
        }

    }

    public void postData(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:\n" + response);
                    showProgress(false);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false);
                            try {
                                printLog(url + "=Error:\n" + error.getMessage());
//                                try {
//                                    jObj = new JSONObject(json.substring(3));
//                                } catch (JSONException e) {
//                                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+json);
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            showProgress(false);
                            if (error == null || null == error.getMessage()) {
                                // error = new VolleyError(context.getString(R.string.something_went_));
                            }
                            callback.onError(error);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                   // headers.put("Content-Type", "application/json");

                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void postDataWithoutLoad(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            try {
                                printLog(url + "=Error:\n" + error.getMessage());
//                                try {
//                                    jObj = new JSONObject(json.substring(3));
//                                } catch (JSONException e) {
//                                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+json);
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            showProgress(false);
                            if (error == null || null == error.getMessage()) {
                                // error = new VolleyError(context.getString(R.string.something_went_));
                            }
                            callback.onError(error);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("Content-Type", "application/json");

                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void postDataWithoutLoading(final String url, final JSONObject requestJson, final VolleyCallback callback) {

        try {
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:\n" + response);
                    showProgress(false);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            if (error == null || null == error.getMessage()) {
                                /* if (context != null && !context.isFinishing())*/
                                //  error = new VolleyError(context.getString(R.string.something_went_));
                            }
                            callback.onError(error);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                   // headers.put("Content-Type", "application/json");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void postDataWithHeader(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false);
                            printLog(url + "=Error:\n" + error.getMessage());
                            knowAboutError(error);
                         /*   if (error == null || null == error.getMessage()) {
                               // error = new VolleyError(context.getString(R.string.something_went_));
                            }*/
                            callback.onError(error);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    // headers.put("request-token", "eyJjaGFubmVsIjoibW9iaWxlIiwiZGV2aWNlLXR5cGUiOiJhbmRyb2lkIiwiaW1laSI6IjM1ODE4NzA3MzY0NzA1MiJ9");
                    // headers.put("request-token", MyApplication.getInstance().getFromSharedPreference(PREF_HEADER_REQ_TOKEN));
                    headers.put("app", "mobile");
                    // headers.put("app", "course");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void postDataWithHeaderLogin(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false);
                            printLog(url + "=Error:\n" + error.getMessage());
                            knowAboutError(error);
                           /* if (error == null || null == error.getMessage()) {
                                error = new VolleyError(context.getString(R.string.something_went_));
                            }*/
                            callback.onError(error);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    // headers.put("request-token", "eyJjaGFubmVsIjoibW9iaWxlIiwiZGV2aWNlLXR5cGUiOiJhbmRyb2lkIiwiaW1laSI6IjM1ODE4NzA3MzY0NzA1MiJ9");
                    // headers.put("request-token", MyApplication.getInstance().getFromSharedPreference(PREF_HEADER_REQ_TOKEN));
                   /* printLog("TokenUsed:" + MyApplication.getInstance().getFromSharedPreference(PREF_HEADER_REQ_TOKEN));
                    headers.put("request-token", MyApplication.getInstance().getFromSharedPreference(PREF_HEADER_REQ_TOKEN));
                    headers.put("app", "mobile");*/
                    // headers.put("app", "course");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    /* JSON ARRAY*/
    public void postDataWithHeaderExtra(final String url, final JSONArray requestJson, final VolleyCallBackArray callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    printLog(url + "=Error:\n" + error.getMessage());
                    knowAboutError(error);
                   /* if (error == null || null == error.getMessage()) {
                        error = new VolleyError(context.getString(R.string.something_went_));
                    }*/
                    callback.onError(error);
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("app", "mobile");
                    headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
                    // headers.put("app", "course");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            arrayRequest.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(arrayRequest, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    /* JSON Obj*/
    public void postDataWithHeaderExtra(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);

                    callback.onSuccess(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    printLog(url + "=Error:\n" + error.getMessage());
                    knowAboutError(error);
                    /*if (error == null || null == error.getMessage()) {
                        error = new VolleyError(context.getString(R.string.something_went_));
                    }*/
                    callback.onError(error);
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("app", "mobile");
                    headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
                    // headers.put("app", "course");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void postDataWithContainUndefine(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false);
                            printLog(url + "=Error:\n" + error.getMessage());
                            knowAboutError(error);
                         /*   if (error == null || null == error.getMessage()) {
                                error = new VolleyError(context.getString(R.string.something_went_));
                            }*/
                            callback.onError(error);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    // headers.put("request-token", "eyJjaGFubmVsIjoibW9iaWxlIiwiZGV2aWNlLXR5cGUiOiJhbmRyb2lkIiwiaW1laSI6IjM1ODE4NzA3MzY0NzA1MiJ9");
                    headers.put("request-token", MyApplication.getInstance().getFromSharedPreference(AppConstantsAndUtils.PREF_HEADER_REQ_TOKEN));
                    headers.put("app", "mobile");
                    // headers.put("app", "course");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void putDataWithHeader(final String url, final JSONObject requestJson, final VolleyCallback callback) {
        showProgress(true);
        try {
            //final JSONObject jsonBody = new JSONObject();
            //jsonBody.put("request", requestJson);

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT, url, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showProgress(false);
                            printLog(url + "=Error:\n" + error.getMessage());
                          /*  if (error == null || null == error.getMessage()) {
                                error = new VolleyError(context.getString(R.string.something_went_));
                            }*/
                            knowAboutError(error);
                            callback.onError(error);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("app", "mobile");
                    // headers.put("app", "course");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void getData(final String url, final VolleyCallback callback) {
        showProgress(true);
        try {

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);
                    showProgress(false);
                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            callback.onError(error);
                          /*  if (error == null || null == error.getMessage()) {
                                error = new VolleyError(context.getString(R.string.something_went_));
                            }*/
                            showProgress(false);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    //  headers.put("X-Access-Token", MyApplication.getInstance().getFromSharedPreference(AppConstantsAndUtils.USER_TOKEN));
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            showProgress(false);
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void getDataAfterLoginHeader(final String url, final VolleyCallback callback) {

        showProgress(true);
        try {

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:::\n" + response);
                    callback.onSuccess(response);
                    showProgress(false);
                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            callback.onError(error);
                            showProgress(false);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            showProgress(false);
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void getDataAfterLoginHeaderNoLoading(final String url, final VolleyCallback callback) {

        try {

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:::\n" + response);
                    callback.onSuccess(response);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            callback.onError(error);

                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void getDataAfterLoginHeader(final int paginaton, final String url, final VolleyCallback callback) {
        if (paginaton == 1) {
            showProgress(true);
        }
        try {

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response:::\n" + response);
                    callback.onSuccess(response);
                    showProgress(false);
                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            callback.onError(error);
                            if (paginaton == 1) {
                                showProgress(false);
                            }
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (paginaton == 1) {
                showProgress(false);
            }
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public void getDataAfterLoginHeader(final String url, final String hederId, final String Token, final VolleyCallback callback) {
        showProgress(true);
        try {

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    printLog(url + "=Response: \n" + response);
                    callback.onSuccess(response);
                    showProgress(false);
                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            printLog(url + "=Error:\n" + error.getMessage());
                            callback.onError(error);
                            showProgress(false);
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("userid", hederId);
                    headers.put("authtoken", Token);
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            showProgress(false);
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }
    }

    public String getErrorMessageObjct(VolleyError error) {
        // As of f605da3 the following should work
        String res = null;
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                // Now you can use any deserializer to make sense of data
//                JSONObject obj = new JSONObject(res);
                printLog("ObjectError" + res);
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
            }
//            } catch (JSONException e2) {
//                // returned data is not JSONObject?
//                e2.printStackTrace();
//            }
        } else {
            if (error != null) {
                printLog("ErrorPrint::" + error);
            }
            printLog("Error is null");
        }
        return res;
    }

    public void knowAboutError(VolleyError error) {
        // As of f605da3 the following should work
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                // Now you can use any deserializer to make sense of data
//                JSONObject obj = new JSONObject(res);
                printLog("ObjectError" + res);
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
            }
//            } catch (JSONException e2) {
//                // returned data is not JSONObject?
//                e2.printStackTrace();
//            }
        } else {
            printLog("Error is null");
        }
    }

    public String getMoreAboutAboutError(VolleyError error) {
        // As of f605da3 the following should work
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                // Now you can use any deserializer to make sense of data
//                JSONObject obj = new JSONObject(res);
                printLog("ObjectError" + res);
                return res;
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
                return null;
            }
//            } catch (JSONException e2) {
//                // returned data is not JSONObject?
//                e2.printStackTrace();
//            }
        } else {
            return null;
        }
    }

    private Map<String, String> checkParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
            printLog("Key:" + pairs.getKey());
            printLog("Values" + pairs.getValue());
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */

    public String getMessage(Object error, Context context) {
        printLog("Object1:" + error);
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.timeout_error);
        } else if (error instanceof ServerError) {
            if (getServerError((VolleyError) error) == null)
                return context.getString(R.string.error_server_error);
            else return getServerError((VolleyError) error);
        } else if (error instanceof AuthFailureError) {
            return context.getString(R.string.error_authentication);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.error_no_network);
        }
        //return context.getResources().getString(R.string.generic_error);
        return context.getResources().getString(R.string.something_went);
    }

    public String getServerError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                // Now you can use any deserializer to make sense of data
//                JSONObject obj = new JSONObject(res);
                printLog("ObjectError" + res);
//                ErrorModel errorModel = new Gson().fromJson(res, ErrorModel.class);
//                return errorModel.getReason();
                return res;
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
            }
        }
        return null;
    }

    private String handleServerError(Object error, Context context) {
        printLog("Object In Handle Server" + error);
        VolleyError er = (VolleyError) error;
        NetworkResponse response = er.networkResponse;
        if (response != null) {
            switch (response.statusCode) {

                case 404:
                case 422:
                case 401:
                    try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        HashMap<String, String> result = new Gson().fromJson(new String(response.data), new TypeToken<HashMap<String, String>>() {
                        }.getType());

                        if (result != null && result.containsKey("error")) {
                            return result.get("error");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return ((VolleyError) error).getMessage();

                default:
                    return context.getResources().getString(R.string.timeout_error);
            }
        }

        return context.getResources().getString(R.string.generic_error);
    }

    private void printLog(String message) {
        Log.e("NetworkUtil", message);
    }


    public void delete(final String url, final VolleyCallback volleyCallback) {
        showProgress(true);
        try {
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showProgress(false);
                    volleyCallback.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error.
                    showProgress(false);
                    volleyCallback.onError(error);

                }
            })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(jsonObjReq, TAG);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            showProgress(false);
            // Cancelling request
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }


    }

    public void postViaStringRequest(final String url, final VolleyCallbackString callback) {
        showProgress(true);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showProgress(false);
                    printLog(url + "=Response:\n" + response);
                    callback.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    knowAboutError(error);
                   /* if (error == null || null == error.getMessage()) {
                        error = new VolleyError(context.getString(R.string.something_went_));
                    }*/
                    callback.onError(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    // headers.put("app", "course");
                    return headers;
                }

            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(stringRequest, TAG);
        } catch (Exception e) {
            showProgress(false);
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            // Cancelling request
            showProgress(false);
            MyApplication.getInstance().getRequestQueue().cancelAll(TAG);
        }

    }

    public void postStringRequest(String url){
        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", "om");
            jsonBody.put("password", "om");
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        //VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(AppConstantsAndUtils.REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            MyApplication.getInstance().addToRequestQueue(stringRequest, TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
