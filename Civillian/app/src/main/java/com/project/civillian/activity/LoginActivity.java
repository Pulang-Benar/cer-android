package com.project.civillian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.civillian.R;
import com.project.civillian.model.Civil;
import com.project.civillian.service.CivilService;
import com.project.civillian.service.LoginService;
import com.project.civillian.service.PanicService;

public class LoginActivity extends AppCompatActivity {
    TextView tvLoginHelp;
    TextView tvRegister;
    CheckBox showPassword;
    EditText tfUsername;
    EditText tfPassword;
    Button btLogin;
    LoginService loginService;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginService = new LoginService(this);
        initComponent();
        initAction();
    }

    private void initComponent(){
        tvLoginHelp = findViewById(R.id.tv_login_help);
        tvRegister = findViewById(R.id.tv_register);
        tfUsername = findViewById(R.id.tf_username);
        tfPassword = findViewById(R.id.tf_password);
        showPassword = findViewById(R.id.show_password);
        btLogin = findViewById(R.id.bt_login);
        tvLoginHelp.setText(Html.fromHtml("Lupa detail informasi masuk Anda? <b>Dapatkan bantuan untuk masuk.</b>"));
        tvRegister.setText(Html.fromHtml("Tidak punya akun? <b>Buat Akun.</b>"));
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void initAction(){
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
                System.out.println("btLogin.setOnClickListener ---");
                progressBar.setVisibility(View.VISIBLE);
                btLogin.setEnabled(false);
                new AsyncTask<Void,Void,Void>(){
                    //The variables you need to set go here
                    @Override
                    protected Void doInBackground(final Void... params){
                        if(loginService.doLogin(tfUsername.getText().toString(), tfPassword.getText().toString())){
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Username atau password salah", Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(final Void result){
                        progressBar.setVisibility(View.INVISIBLE);
                        btLogin.setEnabled(true);
                    }
                }.execute();

            }
        });
    }

}
