package com.renofinsa.belajarconcept.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.renofinsa.belajarconcept.R;
import com.renofinsa.belajarconcept.model.Users;
import com.renofinsa.belajarconcept.network.MyService;
import com.renofinsa.belajarconcept.network.ServiceGenerator;
import com.renofinsa.belajarconcept.utilis.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText Email, Pass;
    Button btnLogin;
    TextView Regiter;
    private boolean login = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        btnLogin = findViewById( R.id.submit );
        Regiter = findViewById( R.id.register );
        Email = findViewById( R.id.email );
        Pass = findViewById( R.id.pass );


        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Users> call = ServiceGenerator.createService(MyService.class).login(Email.getText().toString(),
                        Pass.getText().toString());
                call.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        String value = response.body().value;
                        String fullname = response.body().fullname;
                        String password = response.body().pass;
                        String email = response.body().email;
                        String id = response.body().id;
                        String phone = response.body().phone;
                        if (value.equals("1")){
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Config.LOGIN_SUCCESS, true);
                            editor.putString(Config.PHONE_PREF,phone);
                            editor.putString(Config.EMAIL_PREF,email);
                            editor.putString(Config.ID_PREF,id);
                            editor.putString(Config.PASSWORD,password);
                            editor.putString(Config.FULLNAME_PREF,fullname);
                            editor.commit();
                            startActivity(new Intent(Login.this, MainMenu.class));
                        }else{
                            Toast.makeText(Login.this, "Username dan password salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Toast.makeText(Login.this, "Cek Internet Koneksi anda", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        } );

        Regiter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Login.this, Register.class ) );
            }
        } );


    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        login = sharedPreferences.getBoolean(Config.LOGIN_SUCCESS, false);
        if (login){
            startActivity(new Intent(Login.this, MainMenu.class));

        }else{

        }

    }
}
