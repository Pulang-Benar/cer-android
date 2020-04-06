package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.project.civillian.R;

public class LoginActivity extends AppCompatActivity {

    TextView tvLoginHelp;
    TextView tvRegister;
    CheckBox showPassword;
    EditText tfPassword;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
    }

    private void initComponent(){
        tvLoginHelp = findViewById(R.id.tv_login_help);
        tvRegister = findViewById(R.id.tv_register);
        tfPassword = findViewById(R.id.tf_password);
        showPassword = findViewById(R.id.show_password);
        btLogin = findViewById(R.id.bt_login);
        tvLoginHelp.setText(Html.fromHtml("Lupa detail informasi masuk Anda? <b>Dapatkan bantuan untuk masuk.</b>"));
        tvRegister.setText(Html.fromHtml("Tidak punya akun? <b>Buat Akun.</b>"));
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        tvRegister.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();
                return true;
            }
        });
        tvLoginHelp.setOnTouchListener(new CompoundButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

}
