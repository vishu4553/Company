package com.example.donorapp.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.donorapp.Constant.ConstatsValue;
import com.example.donorapp.Faqs;
import com.example.donorapp.Helper.NetworkHelper;
import com.example.donorapp.Interface.OnClickHome;
import com.example.donorapp.Model.AddDonorModel;
import com.example.donorapp.Model.QueAnsModel;
import com.example.donorapp.R;
import com.example.donorapp.RetrofitClient;
import com.example.donorapp.Signature;
import com.example.donorapp.Util.CommonMethods;
import com.example.donorapp.Util.SharedPreference;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.donorapp.Constant.ConstatsValue.INTENT_KEYS.BEARER;
import static com.example.donorapp.Util.CommonMethods.getLocalToUTCDateSurveyChangeFormat;

public class DonarActivity extends AppCompatActivity implements OnClickHome {
    private ChipGroup chipGroup;
    private String selectedChipText;
    CheckBox checkBoxFAQs;
    EditText etOtherChip,etDName,etDAddress, etDContact, etDEmail, etDAge, etDweight,etDHeight, etDBloodpressure, edCampCode;
    TextView txtSignature,txtDonationDate, txtDob;
    Button btn_donor_save;
    final Calendar myCalendar1 = Calendar.getInstance();
    String selectedDate, selectedDob;
    RadioGroup radioGroupGender;
    RadioButton bt_male, bt_female, bt_other;
    String selectedGender="Male";
    private SharedPreference preference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String signImage;
    ImageView imageSignature;
    private boolean isCheckedValue;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private  static final int   FAQ_ACTIVITY_REQUEST_CODE = 1;
    List<QueAnsModel> QueAnsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar);

        preference = new SharedPreference();
        edCampCode = findViewById(R.id.campCode);
        etDName = findViewById(R.id.d_name);
        etDAddress = findViewById(R.id.d_address);
        etDContact = findViewById(R.id.d_contact_no);
        etDEmail = findViewById(R.id.d_email);
        etDAge = findViewById(R.id.d_age);
        etDweight = findViewById(R.id.d_weight);
        etDHeight = findViewById(R.id.d_height);
        etDBloodpressure = findViewById(R.id.d_blood_pressure);
        txtDonationDate = findViewById(R.id.d_donationDate);
        txtDob = findViewById(R.id.d_dob);
        btn_donor_save = findViewById(R.id.btn_submit);
        radioGroupGender = findViewById(R.id.radioGroupGen);
        bt_male = findViewById(R.id.male);
        bt_female = findViewById(R.id.female);
        bt_other = findViewById(R.id.other);
        checkBoxFAQs = findViewById(R.id.chkFAQs);
        txtSignature = findViewById(R.id.txtSignature);
        imageSignature = findViewById(R.id.imageViewSignature);
        txtSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonarActivity.this, Signature.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
        checkBoxFAQs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxFAQs.isChecked()){
                    isCheckedValue = isChecked; //first set value then assign to boolean extra.

                    Intent intent = new Intent(DonarActivity.this, Faqs.class);
                    intent.putExtra("yourBoolName", isCheckedValue );
                    startActivity(intent);

                }
            }
        });


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat dd = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = dd.format(c);
        SimpleDateFormat db = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDob = db.format(c);
        txtDonationDate.setText(formattedDate);
        txtDob.setText(formattedDob);

        chipGroup = findViewById(R.id.tag_groups);
        etOtherChip = findViewById(R.id.etOtherChips);
        setTag();
        setDobDatePicker();
        setDonationDatePicker();

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                selectedGender=rb.getText().toString();
            }
        });

        btn_donor_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkHelper.isInternetOn(getApplicationContext())) {
                    validationDonor();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.internet_connection_is_not_available, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getByteArrayExtra("byteArray"),0,data.getByteArrayExtra("byteArray").length);
                signImage = encodeTobase64(bitmap);
                imageSignature.setImageBitmap(bitmap);
            }else  if (requestCode == FAQ_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    QueAnsList = (ArrayList<QueAnsModel>) data.getSerializableExtra("ARRAYLIST");
                    Log.d("test", QueAnsList.toString());
                }
            }
        }
    }
    private void setTag() {
        ArrayList<String> tagList = new ArrayList<>();
        tagList.add("A+");
        tagList.add("B+");
        tagList.add("O+");
        tagList.add("AB+");
        tagList.add(" A- ");
        tagList.add(" B-");
        tagList.add(" O-");
        tagList.add("AB- ");

        chipGroup.setSelectionRequired(true);
        chipGroup.setSingleSelection(true);
        for (int index = 0; index < tagList.size(); index++) {
            final String tagName = tagList.get(index);
            final Chip chip = new Chip(this);
            int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            // chip.setPadding(-100,0,0,0);
//chip.setFallbackLineSpacing(false);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0,
                    R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(chipDrawable);
//            chip.setCloseIconResource(R.drawable.ic_action_navigation_close);
            chip.setCloseIconEnabled(false);
//            chip.setTextAppearance(R.style.ChipTextAppearance);
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    tagList.remove(tagName);
//                    chipGroup.removeView(chip);
                    chip.setTextColor(getResources().getColor(R.color.circuler_gray));
                     chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
                }
            });
            chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(ChipGroup chipGroup, int i) {
                    Chip chip = chipGroup.findViewById(i);
                    if (chip != null) {
                        selectedChipText = chip.getText().toString().trim();
                        if (selectedChipText.equalsIgnoreCase("Other")) {
                            etOtherChip.setVisibility(View.VISIBLE);
                            selectedChipText = etOtherChip.getText().toString().trim();
                        } else {
                            etOtherChip.setVisibility(View.GONE);
                        }
                    }
                }
            });
            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.circuler_gray));
            chip.setTextColor(getResources().getColorStateList(R.color.colorPrimaryDark));

            chipGroup.addView(chip);
        }
    }

    private void setDonationDatePicker() {
        DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(ConstatsValue.DATE_FORMATS.DEFAULT, Locale.US);
                txtDonationDate.setText(sdf.format(myCalendar1.getTime()));
                selectedDate = String.valueOf(getLocalToUTCDateSurveyChangeFormat(myCalendar1.getTime()));
            }
        };

        txtDonationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DonarActivity.this, fromDatePicker, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setDobDatePicker() {
        //final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(ConstatsValue.DATE_FORMATS.DEFAULT, Locale.US);
                txtDob.setText(sdf.format(myCalendar1.getTime()));
                selectedDob = String.valueOf(getLocalToUTCDateSurveyChangeFormat(myCalendar1.getTime()));
            }
        };

        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DonarActivity.this, fromDatePicker, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    public void validationDonor(){
        if(etDName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }else if(etDAddress.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
        }else if(etDContact.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
        }else if(etDContact.length() != 10){
            Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
        }else if(etDEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }else if(!etDEmail.getText().toString().trim().matches(emailPattern)){
            Toast.makeText(this, "Enter Valid MailId", Toast.LENGTH_SHORT).show();
        }else if(etDweight.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Weight", Toast.LENGTH_SHORT).show();
        }else if(etDHeight.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Height", Toast.LENGTH_SHORT).show();
        }else if(etDBloodpressure.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Blood Pressure", Toast.LENGTH_SHORT).show();
        }else if(selectedChipText == null){
            Toast.makeText(this, "Select Blood Group", Toast.LENGTH_SHORT).show();
        }else {
            hitAddDonor();
        }
    }

    @Override
    public void onClickHome(View view) {

    }
    private void hitAddDonor() {
        AddDonorModel model = new AddDonorModel();
        model.setDonorName(etDName.getText().toString());
        model.setAddress(etDAddress.getText().toString());
        model.setContact(etDContact.getText().toString());
        model.setDonationDate(selectedDate);
        model.setEmail(etDEmail.getText().toString());
        model.setDob(selectedDob);
        model.setAge(Integer.valueOf(etDAge.getText().toString()));
        model.setGender(selectedGender);
        model.setWeight(Integer.valueOf(etDweight.getText().toString()));
        model.setHeight(etDHeight.getText().toString());
        model.setBloodGroup(selectedChipText);
        model.setBloodPressure(etDBloodpressure.getText().toString());
       // model.setSign(signImage);

        Call<AddDonorModel> call = RetrofitClient.getInstance().getMyApi().addDonor(model, "application/json; charset=utf-8", BEARER + preference.getStringValue(this, ConstatsValue.INTENT_KEYS.Token));
        call.enqueue(new Callback<AddDonorModel>() {
            @Override
            public void onResponse(Call<AddDonorModel> call, Response<AddDonorModel> response) {
                AddDonorModel model1 = response.body();
                if (response.isSuccessful()) {
                    if (model1.getResponseCode() == 0) {
                        CommonMethods.displayResposeToast(getApplicationContext(), model1.getMessage());
                        finish();
                    } else {
                        CommonMethods.displayResposeToast(getApplicationContext(), model1.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddDonorModel> call, Throwable t) {
                CommonMethods.displayResposeToast(getApplicationContext(), t.getMessage());
            }
        });
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
}