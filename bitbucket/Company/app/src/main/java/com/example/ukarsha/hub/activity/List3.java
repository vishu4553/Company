package com.example.ukarsha.hub.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.adapter.CampusListAdapter;
import com.example.ukarsha.hub.adapter.IndustryListAdapter;
import com.example.ukarsha.hub.adapter.JobPostListAdapter;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.CampusDriveModel;
import com.example.ukarsha.hub.model.CampusListResult;
import com.example.ukarsha.hub.model.CommonModel1;
import com.example.ukarsha.hub.model.IndustrialListModel;
import com.example.ukarsha.hub.model.IndustrialVisit1;
import com.example.ukarsha.hub.model.JobPosting1;
import com.example.ukarsha.hub.model.JobPostingListModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.CAMPUS_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.INDUSTRIAL_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.JOB_POSTING_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STUDENT_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_CAMPUS_DRIVE_LIST;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_IDUSTRIAL_LIST;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_EXPIRED;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_POSTING_LIST;

public class List3 extends BaseActivity {
    TextView textViewCampus, textViewIndustrial, textViewJob;
    ProgressDialog progressDialog;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    String login;

    @SuppressLint({"WrongViewCast", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

       // login = getIntent().getStringExtra("ID");

        initialiseView();

        if (MyApplication.getInstance().isConnectedToNetwork(List3.this)) {
            //getList3();
        } else {
            noNetwork();
        }
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
        recyclerView1 = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        Animation anim = AnimationUtils.loadAnimation(List3.this, R.anim.bounce_interpolator);
        recyclerView1.startAnimation(anim);

        if (MyApplication.getInstance().isConnectedToNetwork(List3.this)) {
            getList1();
        } else {
           noNetwork();
        }


         /*For Industrial Visit*/

        recyclerView2 = (RecyclerView) findViewById(R.id.recycleView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        Animation anim2 = AnimationUtils.loadAnimation(List3.this, R.anim.bounce_interpolator);
        recyclerView2.startAnimation(anim2);

        if (MyApplication.getInstance().isConnectedToNetwork(List3.this)) {
            getList2();
        } else {
           noNetwork();
        }


         /*For Job Posting*/

        recyclerView3 = (RecyclerView) findViewById(R.id.recycleView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        Animation anim3 = AnimationUtils.loadAnimation(List3.this, R.anim.bounce_interpolator);
        recyclerView3.startAnimation(anim3);

        if (MyApplication.getInstance().isConnectedToNetwork(List3.this)) {
            getList3();
        } else {
            noNetwork();
        }

        getJobExpired();

    }


    /*For Campus Drive*/
    private void getList1() {
       // showProgress(true);
        final JSONObject requestJson;
        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID) + "");
            //Toast.makeText(this, MyApplication.getInstance().getFromSharedPreference(COMPANY_ID) + "", Toast.LENGTH_SHORT).show();
            //requestJson.put("comp_id", "1");
            new NetworkRequestUtil(this).postData(URL_CAMPUS_DRIVE_LIST, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CampusDriveModel getList = new Gson().fromJson(response.toString(), CampusDriveModel.class);
                    if (getList != null) {
                        if (getList.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            for (int i = 0; i > getList.getCampus_list_result().size(); i++) {
                            }

                            showList(getList);
                        } else showDialogWithOkButton(getList.getMessage());
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                // @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showList(final CampusDriveModel getList) {
        final CampusListAdapter adapter = new CampusListAdapter(getApplicationContext(), getList.getCampus_list_result(), new CampusListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CampusListResult services, ImageView type, String pos) {
                MyApplication.getInstance().addToSharedPreference(CAMPUS_ID, services.getCampus_id());
            }
            @Override
            public void onButtonClick(CampusListResult service, LinearLayout linearLayout, String s) {
                MyApplication.getInstance().addToSharedPreference(STUDENT_ID, service.getComp_id());
                MyApplication.getInstance().addToSharedPreference(CAMPUS_ID, service.getCampus_id());
                // startActivity(new Intent(CompanyList.this, StudentList.class));
                Intent i = new Intent(List3.this, RepostCampus.class);
                i.putExtra("COMPANY_ID", service.getComp_id());
                i.putExtra("POSITION", service.getPosition());
                i.putExtra("NAME", service.getName());
                i.putExtra("POSTED_ON", service.getPosted_date());
                i.putExtra("WEBSITE", service.getRegistration_link());
                i.putExtra("DRIVE_INFO", service.getDrive_info());
                startActivity(i);
            }

            @Override
            public void onButtonClick(int layoutPosition) {
                startActivity(new Intent(List3.this, RepostCampus.class));
               /* Intent i = new Intent(List3.this, RepostIndustrial.class);
                i.putExtra("COMPANY_ID", services.getComp_id());
                startActivity(i);*/
            }
        });
        recyclerView1.setAdapter(adapter);
    }

    /*For Industrial Visit*/
    private void getList2() {
        //showProgress(true);
        final JSONObject requestJson;
        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID) + "");
            //requestJson.put("comp_id", "5");
            new NetworkRequestUtil(this).postData(URL_IDUSTRIAL_LIST, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    IndustrialListModel getList = new Gson().fromJson(response.toString(), IndustrialListModel.class);
                    if (getList != null) {
                        if (getList.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            for (int i = 0; i > getList.getIndustrial_visit().size(); i++) {
                            }
                            showList(getList);
                        } else showDialogWithOkButton(getList.getMessage());
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                // @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showList(final IndustrialListModel getList) {
        final IndustryListAdapter adapter = new IndustryListAdapter(getApplicationContext(), getList.getIndustrial_visit(), new IndustryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IndustrialVisit1 services, ImageView type, String pos) {
                //MyApplication.getInstance().addToSharedPreference(SERVICE_ID, services.getId());
                MyApplication.getInstance().addToSharedPreference(INDUSTRIAL_ID, services.getIndustrial_id());

            }

            @Override
            public void onButtonClick(IndustrialVisit1 service, LinearLayout linearLayout, String s) {
                MyApplication.getInstance().addToSharedPreference(INDUSTRIAL_ID, service.getIndustrial_id());
                //MyApplication.getInstance().addToSharedPreference(STUDENT_ID, service.getComp_id());
                // startActivity(new Intent(CompanyList.this, StudentList.class));
                Intent i = new Intent(List3.this, RepostIndustrial.class);
                i.putExtra("COMPANY_ID", service.getComp_id());
                //i.putExtra("POSITION", service.getComp_id());
                i.putExtra("NAME", service.getName());
                i.putExtra("POSTED_ON", service.getPosted_date());
                i.putExtra("VISIT_DATE", service.getVisit_date());
                i.putExtra("TRAIN_INFO", service.getTraining_info());
                startActivity(i);
            }
        });
        recyclerView2.setAdapter(adapter);
    }

    //third list

    private void getList3() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID) + "");
            //requestJson.put("comp_id", "5");
            new NetworkRequestUtil(this).postData(URL_JOB_POSTING_LIST, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    JobPostingListModel getList = new Gson().fromJson(response.toString(), JobPostingListModel.class);
                    if (getList != null) {
                        if (getList.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            for (int i = 0; i > getList.getJob_posting().size(); i++) {
                            }
                            showList(getList);
                        } else showDialogWithOkButton(getList.getMessage());
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }
                // @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showList(final JobPostingListModel getList) {

        final JobPostListAdapter adapter = new JobPostListAdapter(getApplicationContext(), getList.getJob_posting(), new JobPostListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(JobPosting1 services, ImageView type, String pos) {
                MyApplication.getInstance().addToSharedPreference(JOB_POSTING_ID, services.getPosting_id());
                //MyApplication.getInstance().addToSharedPreference(SERVICE_ID, services.getId());

            }

            @Override
            public void onButtonClick(JobPosting1 service, LinearLayout linearLayout, String s) {
                MyApplication.getInstance().addToSharedPreference(JOB_POSTING_ID, service.getPosting_id());
                //MyApplication.getInstance().addToSharedPreference(STUDENT_ID, service.getComp_id());
                // startActivity(new Intent(CompanyList.this, StudentList.class));
                Intent i = new Intent(List3.this, RepostJobPosting.class);
                i.putExtra("COMPANY_ID", service.getComp_id());
                i.putExtra("NAME", service.getName());
                i.putExtra("TECHNOLOGY", service.getTechnology());
                i.putExtra("JOB_TITLE", service.getJob_title());
                i.putExtra("TYPE", service.getType());
                i.putExtra("VACANCY", service.getVacancies());
                i.putExtra("POSTED_ON", service.getPosted_date());
                i.putExtra("OTHER", service.getOther());
                startActivity(i);
            }
        });
        recyclerView3.setAdapter(adapter);
    }

    //second method(api)is for job expire

    private void getJobExpired() {
       // showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            new NetworkRequestUtil(this).postData(URL_JOB_EXPIRED, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 getList = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (getList != null) {
                        if (getList.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(List3.this, getList.getMessage(), Toast.LENGTH_SHORT).show();
                        } else showDialogWithOkButton(getList.getMessage());
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                // @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            progressDialog = new ProgressDialog(List3.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(List3.this);
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
                startActivity(new Intent(List3.this, Profile.class));
                finish();
            }
        });
        PopupWindow mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void noNetwork(){
        startActivity(new Intent(List3.this, NoNetwork.class));
    }


}
