package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;
import com.project.civillian.util.DateUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private Button btSimpan, btKembali;
    private EditText tfNik, tfNama, tfTglLahir, tfTptLahir, tfAlamat, tfTelp, tfTelpRef, tfUsername, tfEmail, tfPassword, tfRePassword;
    Spinner cbGender;
    private DatePickerDialog datePickerDialog;
    private CivilService civilService;
    private Civil civil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        civilService = new CivilService(this);
        civil = civilService.getCivilLogin();
        if(civil != null) initUserLogin();
        initAction();
    }

    private void initComponent(){
        tfNik = findViewById(R.id.tf_nik);
        tfNama = findViewById(R.id.tf_nama);
        cbGender = findViewById(R.id.cb_gender);
        tfTglLahir = findViewById(R.id.tf_tglLahir);
        tfTptLahir = findViewById(R.id.tf_tptLahir);
        tfAlamat = findViewById(R.id.tf_alamat);
        tfTelp = findViewById(R.id.tf_telp);
        tfTelpRef = findViewById(R.id.tf_telp_ref);
        tfUsername = findViewById(R.id.tf_username);
        tfEmail = findViewById(R.id.tf_email);
        tfPassword = findViewById(R.id.tf_password);
        tfRePassword = findViewById(R.id.tf_password_retype);
        btSimpan = findViewById(R.id.bt_simpan);
        btKembali = findViewById(R.id.bt_kembali);
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
        tfRePassword.setText(getStr(civil.getPassword()));
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
        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCivil();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });
        btKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });
    }

    private void updateCivil(){
        getComponentValue();
        System.out.println("UPDATE USER "+civil.toString());
        civilService.updateCivil(civil);
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
    }

    private String getStr(String str){
        return str == null ? "" : str;
    }

    private String getStr(Editable text){
        return text == null ? "" : text.toString();
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

}
