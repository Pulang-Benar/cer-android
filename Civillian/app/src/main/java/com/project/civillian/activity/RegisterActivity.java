package com.project.civillian.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.LoginService;
import com.project.civillian.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private EditText tfNik, tfNama, tfTglLahir, tfTptLahir, tfAlamat, tfTelp, tfTelpRef, tfUsername, tfEmail, tfPassword, tfPasswordRetype;
    Spinner cbGender;
    private DatePickerDialog datePickerDialog;
    private TextView tvRegisterHelp, tvLogin;
    private CheckBox showPassword;
    private Button btRegister;
    private CivilService civilService;
    private Civil civil;
    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        initAction();
        loginService = new LoginService(this);
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        if(civil != null) initUserLogin();
    }



    private void initComponent(){
        tfNik = findViewById(R.id.tf_nik);
        tfNama = findViewById(R.id.tf_nama);
        cbGender = findViewById(R.id.cb_gender);
        tfTptLahir = findViewById(R.id.tf_tptLahir);
        tfAlamat = findViewById(R.id.tf_alamat);
        tfTelp = findViewById(R.id.tf_telp);
        tfTelpRef = findViewById(R.id.tf_telp_ref);
        tfUsername = findViewById(R.id.tf_username);
        tfEmail = findViewById(R.id.tf_email);
        tfTglLahir = findViewById(R.id.tf_tglLahir);
        tvRegisterHelp = findViewById(R.id.tv_register_help);
        tvLogin = findViewById(R.id.tv_login);
        showPassword = findViewById(R.id.show_password);
        tfPassword = findViewById(R.id.tf_password);
        tfPasswordRetype = findViewById(R.id.tf_password_retype);
        btRegister = findViewById(R.id.bt_register);

        tvRegisterHelp.setText(Html.fromHtml("Butuh panduan pendaftaran? <b>Cara pendaftaran</b>"));
        tvLogin.setText(Html.fromHtml("Sudah punya akun? <b>Masuk.</b>"));
    }

    private void initAction(){
        tfTglLahir.setOnFocusChangeListener(new CompoundButton.OnFocusChangeListener(){
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
                insertCivil();
                if(loginService.doLogin("male", "user123")){
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
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
                tfTglLahir.setText(new SimpleDateFormat("dd MMMM yyyy").format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void insertCivil(){
        getComponentValue();
        System.out.println("UPDATE USER "+civil.toString());
        civilService.insertCivil(civil);
    }

    private void getComponentValue(){
        civil.setNik(getStr(tfNik.getText()));
        civil.setEmail(getStr(tfEmail.getText()));
        civil.setNama(getStr(tfNama.getText()));
        if(cbGender != null && cbGender.getSelectedItemPosition() != -1){
            if(cbGender.getSelectedItemPosition() == 0) civil.setGender("L");
            else if(cbGender.getSelectedItemPosition() == 1) civil.setGender("P");
        }
        civil.setTanggalLahir(DateUtil.StringToDate(getStr(tfTglLahir.getText()), DateUtil.DATE_PATTERN_VIEW));
        civil.setTempatLahir(getStr(tfTptLahir.getText()));
        civil.setAlamat(getStr(tfAlamat.getText()));
        civil.setTelp(getStr(tfTelp.getText()));
        civil.setTelpRef(getStr(tfTelpRef.getText()));
//        civil.setUsername(getStr(tfUsername.getText()));
        civil.setUsername(getStr("male"));
//        civil.setPassword(getStr(tfPassword.getText()));
        civil.setPassword(getStr("user123"));
    }

    private String getStr(String str){
        return str == null ? "" : str;
    }

    private String getStr(Editable text){
        return text == null ? "" : text.toString();
    }

    private void initUserLogin(){
        tfNik.setText(getStr(civil.getNik()));
        tfNama.setText(getStr(civil.getNama()));
        if (civil.getGender() != null) {
            if("L".equals(civil.getGender())) cbGender.setSelection(0);
            else if("P".equals(civil.getGender())) cbGender.setSelection(1);
        }
        tfTglLahir.setText(DateUtil.dateToString(civil.getTanggalLahir(), DateUtil.DATE_PATTERN_VIEW) );
        tfTptLahir.setText(getStr(civil.getTempatLahir()));
        tfAlamat.setText(getStr(civil.getAlamat()));
        tfTelp.setText(getStr(civil.getTelp()));
        tfTelpRef.setText(getStr(civil.getTelpRef()));
        System.out.println("civil.getUsername() -> "+civil.getUsername());
        tfUsername.setText(getStr(civil.getUsername()));
        tfEmail.setText(getStr(civil.getEmail()));
        tfPassword.setText(getStr(civil.getPassword()));
        tfPasswordRetype.setText(getStr(civil.getPassword()));
    }


}
