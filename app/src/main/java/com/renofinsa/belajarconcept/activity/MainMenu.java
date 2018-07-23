package com.renofinsa.belajarconcept.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renofinsa.belajarconcept.R;
import com.renofinsa.belajarconcept.utilis.Config;

public class MainMenu extends AppCompatActivity {
    TextView Fullname, Email, Password, Phone;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    String FullnameUrl, EmailUrl, PassUrl, PhoneUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );
        Fullname = findViewById( R.id.fullname );
        Email = findViewById( R.id.email );
        Password = findViewById( R.id.pass);
        Phone = findViewById( R.id.phone );
        btnLogout = findViewById( R.id.logout);
        sharedPreferences = getSharedPreferences( Config.SHARED_PREF_NAME, Context.MODE_PRIVATE );
        FullnameUrl = sharedPreferences.getString( Config.FULLNAME_PREF, "Not Available" );
        EmailUrl = sharedPreferences.getString( Config.EMAIL_PREF, "Not Available" );
        PassUrl = sharedPreferences.getString( Config.PASSWORD, "Not Available" );
        PhoneUrl = sharedPreferences.getString( Config.PHONE_PREF, "Not Available" );

        Fullname.setText( FullnameUrl );
        Email.setText( EmailUrl );
        Password.setText( PassUrl );
        Phone.setText( PhoneUrl );

        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Config.LOGIN_SUCCESS, false);
                editor.commit();
                startActivity(new Intent(MainMenu.this, Login.class));
            }
        } );
    }
}
