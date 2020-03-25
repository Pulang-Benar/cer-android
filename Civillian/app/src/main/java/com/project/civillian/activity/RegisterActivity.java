package com.project.civillian.activity;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.civillian.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private EditText tfTtl;
    private TextView tvRegisterHelp;
    private TextView tvLogin;
    private CheckBox showPassword;
    private EditText tfPassword;
    private EditText tfPasswordRetype;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
    }

//    @Override
//    public void onBackPressed() {
//
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(setIntent);
//    }

    private void initComponent(){
        tfTtl = findViewById(R.id.tf_ttl);
        tvRegisterHelp = findViewById(R.id.tv_register_help);
        tvLogin = findViewById(R.id.tv_login);
        showPassword = findViewById(R.id.show_password);
        tfPassword = findViewById(R.id.tf_password);
        tfPasswordRetype = findViewById(R.id.tf_password_retype);
        btRegister = findViewById(R.id.bt_register);

        tvRegisterHelp.setText(Html.fromHtml("Butuh panduan pendaftaran? <b>Cara pendaftaran</b>"));
        tvLogin.setText(Html.fromHtml("Sudah punya akun? <b>Masuk.</b>"));

        tfTtl.setOnFocusChangeListener(new CompoundButton.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog();
                } else {
                    datePickerDialog.hide();
                }
            }
        });
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tfPasswordRetype.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tfPasswordRetype.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        tvLogin.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                RegisterActivity.this.finish();
                return true;
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tfTtl.setText(new SimpleDateFormat("dd MMMM yyyy").format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
