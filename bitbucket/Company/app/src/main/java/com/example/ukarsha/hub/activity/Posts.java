package com.example.ukarsha.hub.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.CommonModel1;
import com.example.ukarsha.hub.model.JbTypeModel;
import com.example.ukarsha.hub.model.JobTypeResult;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_NAME;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.JOB_TYPE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_CAMPUS_DRIVE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_INDUSTRIAL_DRIVE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_POSTING;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_TYPE;

public class Posts extends BaseActivity implements View.OnClickListener {
    TextView textViewCampus, textViewIndustrial, textViewJob;
    private EditText editTextCmyName1, editTextPostDt1, editTextPost1, editTextDriveInfo1, editTextEndDt1,
            editTextCmyName2, editTextPostDt2, editTextVisitDt2, editTextTraInfo2,
            editTextCmyName3, editTextPostDt3, editTextOther3, editTextEndDt3, editTextVacancies3, editTextTechnology3, editTextJobTit3;
    Spinner spinnerJbType;
    private Button buttonSave1, buttonCancel1, buttonClear1,
            buttonSave2, buttonCancel2, buttonClear2,
            buttonSave3, buttonCancel3, buttonClear3;
    ProgressDialog progressDialog;
    private ImageView imageDatePicker1, imageDatePicker2, imageDatePicker3, imageDatePicker4, imageDatePicker5, imageDatePicker6;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<JobTypeResult> TypeArray = new ArrayList<>();


    @SuppressLint({"WrongViewCast", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        initialiseView();
        setSpinner();
        TypeArray = new ArrayList<>();

        getJobTypeList();
    }

    private void initialiseView() {
        textViewCampus = findViewById(R.id.campusDrive);
        textViewIndustrial = findViewById(R.id.indVisit);
        textViewJob = findViewById(R.id.jobPosting);

        final LinearLayout linearCampusDrive = findViewById(R.id.linearCampus);
        final LinearLayout linearIndustrialVisit = findViewById(R.id.linearIndustrial);
        final LinearLayout linearJobPosting = findViewById(R.id.linearJob);
        linearJobPosting.setVisibility(View.GONE);
        linearIndustrialVisit.setVisibility(View.GONE);
        linearCampusDrive.setVisibility(View.VISIBLE);

        textViewCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewCampus.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                textViewIndustrial.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                textViewJob.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                linearJobPosting.setVisibility(View.GONE);
                linearIndustrialVisit.setVisibility(View.GONE);
                linearCampusDrive.setVisibility(View.VISIBLE);

            }
        });

        textViewIndustrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewIndustrial.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                textViewCampus.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                textViewJob.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                linearIndustrialVisit.setVisibility(View.VISIBLE);
                linearCampusDrive.setVisibility(View.GONE);
                linearJobPosting.setVisibility(View.GONE);

            }
        });

        textViewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewJob.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                textViewCampus.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                textViewIndustrial.setTextColor(getApplicationContext().getResources().getColor(R.color.four));
                linearJobPosting.setVisibility(View.VISIBLE);
                linearCampusDrive.setVisibility(View.GONE);
                linearIndustrialVisit.setVisibility(View.GONE);

            }
        });

        /*For Campus Drive*/

        editTextCmyName1 = findViewById(R.id.edCmpyName);
        editTextCmyName1.setText(MyApplication.getInstance().getFromSharedPreference(COMPANY_NAME));
        editTextPostDt1 = findViewById(R.id.edPostDt);
        editTextPost1 = findViewById(R.id.edPost);
        //editTextEndDt = findViewById(R.id.edEndDate1);
        editTextEndDt1 = findViewById(R.id.edEndDate1);
        editTextDriveInfo1 = findViewById(R.id.edDriveInfo);
        imageDatePicker1 = findViewById(R.id.imageDob);
        imageDatePicker1.setOnClickListener(this);
        imageDatePicker5 = findViewById(R.id.imageDob11);
        imageDatePicker5.setOnClickListener(this);
        imageDatePicker6 = findViewById(R.id.imageDob12);
        imageDatePicker6.setOnClickListener(this);

        buttonClear1 = findViewById(R.id.btnClear);
        buttonSave1 = findViewById(R.id.btnSave);
        buttonSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(Posts.this)) {
                    validationCampus();
                } else {
                    noNetwork();
                }
            }
        });
       /* buttonCancel1 = findViewById(R.id.btnCancel);
        buttonCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Posts.this, Login.class));
                //finish();
            }
        });
*/
        buttonClear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear1();
            }
        });

         /*For Industrial Visit*/

        editTextCmyName2 = findViewById(R.id.edCmpyName1);
        editTextCmyName2.setText(MyApplication.getInstance().getFromSharedPreference(COMPANY_NAME));
        editTextPostDt2 = findViewById(R.id.edPostDt2);
        editTextVisitDt2 = findViewById(R.id.edVisitDt2);
        editTextTraInfo2 = findViewById(R.id.edTraInfo);
        imageDatePicker2 = findViewById(R.id.imageDob2);
        imageDatePicker2.setOnClickListener(this);
        imageDatePicker3 = findViewById(R.id.imageDob3);
        imageDatePicker3.setOnClickListener(this);

        buttonClear2 = findViewById(R.id.btnClear2);
        buttonSave2 = findViewById(R.id.btnSave2);
        buttonSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(Posts.this)) {
                    validationIndustrail();
                } else {
                    noNetwork();
                }
            }
        });
       /* buttonCancel2 = findViewById(R.id.btnCancel2);
        buttonCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Posts.this, Login.class));
                finish();
            }
        });*/

        buttonClear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear2();
            }
        });

         /*For Job Posting*/

        editTextCmyName3 = findViewById(R.id.edCmpyName3);
        editTextCmyName3.setText(MyApplication.getInstance().getFromSharedPreference(COMPANY_NAME));
        imageDatePicker4 = findViewById(R.id.imageDob4);
        imageDatePicker4.setOnClickListener(this);
        editTextPostDt3 = findViewById(R.id.edPostDt3);
        editTextEndDt3 = findViewById(R.id.edEndDate3);
        editTextJobTit3 = findViewById(R.id.edJobTl);
        editTextOther3 = findViewById(R.id.edOther);
        editTextVacancies3 = findViewById(R.id.edVacancy);
        editTextTechnology3 = findViewById(R.id.edTech);
        spinnerJbType = findViewById(R.id.spinnerJobType);

        buttonClear3 = findViewById(R.id.btnClear3);
        buttonSave3 = findViewById(R.id.btnSave3);
        buttonSave3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(Posts.this)) {
                   validationJob();
                    //finish();
                    //getData3();
                } else {
                    noNetwork();
                }
            }
        });
        buttonCancel3 = findViewById(R.id.btnCancel3);
        buttonCancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Posts.this, Login.class));
                finish();
            }
        });

        buttonClear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear3();
            }
        });

    }

    public void gotoHome(){
        startActivity(new Intent(Posts.this, List3.class));
        finish();
    }

    /*For Campus Drive*/
    private void getData() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            /*requestJson.put("name", "cyberathon");
            requestJson.put("posted_date", "2019-09-27");
            requestJson.put("end_date", "2019-09-27");
            requestJson.put("contact_no", "9856231456");
            requestJson.put("registration_link", "www.cyberathon.com");
            requestJson.put("position", "web developer");
            requestJson.put("drive_info", "testing");*/

            requestJson.put("name", MyApplication.getInstance().getFromSharedPreference(COMPANY_NAME));
            requestJson.put("posted_date", editTextPostDt1.getText().toString());
            requestJson.put("end_date", editTextEndDt1.getText().toString());
            requestJson.put("contact_no", "78946222200");
            requestJson.put("registration_link", "www.cyberathon.com");
            requestJson.put("position", editTextPost1.getText().toString());
            requestJson.put("drive_info", editTextDriveInfo1.getText().toString());

            new NetworkRequestUtil(this).postData(URL_CAMPUS_DRIVE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel != null) {
                        if (commonModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            Toast.makeText(Posts.this, commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                            gotoHome();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(commonModel.getMessage());
                        }

                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*For Industrial Visit*/
    private void getData2() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();

            requestJson.put("name", editTextCmyName2.getText().toString());
            requestJson.put("posted_date", editTextPostDt2.getText().toString());
            requestJson.put("training_info", editTextTraInfo2.getText().toString());
            requestJson.put("visit_date", editTextVisitDt2.getText().toString());

            new NetworkRequestUtil(this).postData(URL_INDUSTRIAL_DRIVE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel != null) {
                        if (commonModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            Toast.makeText(Posts.this, commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                            gotoHome();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(commonModel.getMessage());
                        }

                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData3() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();

            requestJson.put("name", editTextCmyName3.getText().toString());
            requestJson.put("posted_date", editTextPostDt3.getText().toString());
            requestJson.put("end_date", editTextEndDt3.getText().toString());
            requestJson.put("job_title", editTextJobTit3.getText().toString());
            requestJson.put("technology", editTextTechnology3.getText().toString());
            requestJson.put("type_id", MyApplication.getInstance().getIntFromSharedPreference(JOB_TYPE) + "");
            //requestJson.put("type_id", spinnerJbType.getSelectedItem().toString());
            requestJson.put("other", editTextOther3.getText().toString());
            requestJson.put("vacancies", editTextVacancies3.getText().toString());

            new NetworkRequestUtil(this).postData(URL_JOB_POSTING, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel != null) {
                        if (commonModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(Posts.this, commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                            gotoHome();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(commonModel.getMessage());
                        }

                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*second method(api) to get the job Type List*/

    private void getJobTypeList() {
        //showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            new NetworkRequestUtil(this).postData(URL_JOB_TYPE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    JbTypeModel getList = new Gson().fromJson(response.toString(), JbTypeModel.class);
                    if (getList.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                        if (getList != null) {
                            for (int i = 0; i < getList.getJobtype_result().size(); i++) {
                                TypeArray.add(new JobTypeResult(getList.getJobtype_result().get(i).getType_id(), getList.getJobtype_result().get(i).getType()));
                            }
                            setSpinner();
                        } else {
                            showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                        }

                    } else showDialogWithOkButton(getList.getMessage());

                }

                // @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinner() {
        //spinner for
        final ArrayAdapter<JobTypeResult> arrayAdapter = new ArrayAdapter<JobTypeResult>(
                this, android.R.layout.simple_spinner_item, TypeArray) {
            @Override
            public boolean isEnabled(int position) {
                if (position == -1) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == -1) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundColor(Color.WHITE);
                } /*else {
                    tv.setTextColor(Color.BLACK);
                }*/
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerJbType.setAdapter(arrayAdapter);

        spinnerJbType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                JobTypeResult getType = (JobTypeResult) parent.getSelectedItem();
                MyApplication.getInstance().addToSharedPreference(JOB_TYPE, getType.getType_id());
                MyApplication.getInstance().addIntToSharedPreference(JOB_TYPE, Integer.parseInt(getType.getType_id()));
                //MyApplication.getInstance().addToSharedPreference(CITY_ID, getType.getCityid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected String getTag() {
        return "";
    }

    /* Hide key board*/
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(Posts.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(Posts.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        }
    }

    public void showProgress(boolean show) {

        if (show) {
            if (progressDialog != null && !this.isFinishing()) {
                progressDialog.show();
            }

        } else {
            if (progressDialog != null && progressDialog.isShowing() && !this.isFinishing())
                progressDialog.dismiss();
        }

    }

    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_success, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        alertDialog.show();
        TextView text = (TextView) dialogView.findViewById(R.id.text);
        text.setText("Data  Saved");
        Button button = (Button) dialogView.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alertDialog.dismiss();
                startActivity(new Intent(Posts.this, Profile.class));
                finish();
            }
        });
        PopupWindow mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == imageDatePicker1) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextPostDt1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == imageDatePicker2) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextPostDt2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == imageDatePicker3) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextVisitDt2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == imageDatePicker4) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            editTextPostDt3.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == imageDatePicker5) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextEndDt1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == imageDatePicker6) {
            // Get Current Date
            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextEndDt3.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
    }

    public void validationCampus() {
        final String cmpyname = editTextCmyName1.getText().toString();
        final String postDt = editTextPostDt1.getText().toString();
        final String endDt = editTextEndDt1.getText().toString();
        final String position = editTextPost1.getText().toString();
        final String driveInfo = editTextDriveInfo1.getText().toString();


        if (TextUtils.isEmpty(cmpyname)) {
            editTextCmyName1.setError("Please Enter Company Name");
            editTextCmyName1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(postDt)) {
            editTextPostDt1.setError("Please Select Post Date");
            editTextPostDt1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(endDt)) {
            editTextEndDt1.setError("Please Select End Date");
            editTextEndDt1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(position)) {
            editTextPost1.setError("Please Enter Position");
            editTextPost1.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(driveInfo)) {
            editTextDriveInfo1.setError("Please Enter Drive Information");
            editTextDriveInfo1.requestFocus();
            return;
        }

        getData();

    }


    public void validationIndustrail() {
        final String cmpyname = editTextCmyName2.getText().toString();
        final String postDt = editTextPostDt2.getText().toString();
        final String visitDt = editTextVisitDt2.getText().toString();
        final String traInfo = editTextTraInfo2.getText().toString();


        if (TextUtils.isEmpty(cmpyname)) {
            editTextCmyName2.setError("Please Enter Company Name");
            editTextCmyName2.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(postDt)) {
            editTextPostDt2.setError("Please Select Post Date");
            editTextPostDt2.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(visitDt)) {
            editTextVisitDt2.setError("Please Select Visit Date");
            editTextVisitDt2.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(traInfo)) {
            editTextTraInfo2.setError("Please Enter Drive Information");
            editTextTraInfo2.requestFocus();
            return;
        }

        getData2();
    }

    public void validationJob() {
        final String cmpyname = editTextCmyName3.getText().toString();
        final String postDt = editTextPostDt3.getText().toString();
        final String EndDt = editTextEndDt3.getText().toString();
        final String tech = editTextTechnology3.getText().toString();
        final String jobTit = editTextJobTit3.getText().toString();
        final String vacancy = editTextVacancies3.getText().toString();
        final String info = editTextOther3.getText().toString();


        if (TextUtils.isEmpty(cmpyname)) {
            editTextCmyName3.setError("Please Enter Company Name");
            editTextCmyName3.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(postDt)) {
            editTextPostDt3.setError("Please Select Post Date");
            editTextPostDt3.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(EndDt)) {
            editTextEndDt3.setError("Please Select End Date");
            editTextEndDt3.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tech)) {
            editTextTechnology3.setError("Please Enter Technology");
            editTextTechnology3.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(jobTit)) {
            editTextJobTit3.setError("Please Enter Job Title");
            editTextJobTit3.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(vacancy)) {
            editTextVacancies3.setError("Please Enter Vacancy");
            editTextVacancies3.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(info)) {
            editTextOther3.setError("Please Enter Other Information");
            editTextOther3.requestFocus();
            return;
        }

        getData3();

    }

    /*To Clear Text*/

    public void clear1() {
        editTextCmyName1.getText().clear();
        editTextPostDt1.getText().clear();
        editTextEndDt1.getText().clear();
        editTextPost1.getText().clear();
        editTextDriveInfo1.getText().clear();
    }

    public void clear2() {
        editTextCmyName2.getText().clear();
        editTextPostDt2.getText().clear();
        editTextVisitDt2.getText().clear();
        editTextTraInfo2.getText().clear();
    }

    public void clear3() {
        editTextCmyName3.getText().clear();
        editTextPostDt3.getText().clear();
        editTextEndDt3.getText().clear();
        editTextTechnology3.getText().clear();
        editTextJobTit3.getText().clear();
        editTextVacancies3.getText().clear();
        editTextTraInfo2.getText().clear();
    }

    public void noNetwork() {
        startActivity(new Intent(Posts.this, NoNetwork.class));
    }
}
