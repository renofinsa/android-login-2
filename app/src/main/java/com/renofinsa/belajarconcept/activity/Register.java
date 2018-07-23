package com.renofinsa.belajarconcept.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.renofinsa.belajarconcept.R;
import com.renofinsa.belajarconcept.model.Value;
import com.renofinsa.belajarconcept.network.MyService;
import com.renofinsa.belajarconcept.network.ServiceGenerator;
import com.renofinsa.belajarconcept.utilis.Config;

import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    ImageView Back;
    EditText Email, Fullname, Pass, Phone;
    Button Register;
    ProgressDialog progressDialog;
    TextView form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        Back = findViewById( R.id.back );
        Email = findViewById( R.id.email );
        Fullname = findViewById( R.id.fullname );
        Pass = findViewById( R.id.pass );
        Phone = findViewById( R.id.phone );
        Register = findViewById( R.id.register );

        //Ganti Font dari folder assets
//        form.setTypeface( Typeface.createFromAsset(getAssets(), "Ubuntu Bold.ttf"));

        Register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty( Fullname.getText() )){
                    Fullname.setText( R.string.check );
                }else if (TextUtils.isEmpty( Email.getText() )){
                    Email.setText( R.string.check );
                }else if (TextUtils.isEmpty( Pass.getText() )){
                    Pass.setText( R.string.check );
                }else if (TextUtils.isEmpty( Phone.getText() )){
                    Phone.setText( R.string.check );
                }else{
                    progressDialog = new ProgressDialog( Register.this );
                    progressDialog.setTitle( "Loading Bos ...." );
                    progressDialog.show();
                    SimpanData();
                }
            }
        } );

        Back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                supportFinishAfterTransition();
            }
        } );

    }

    private void SimpanData() {
        progressDialog.dismiss();

        SharedPreferences sharedPreferences = getSharedPreferences( Config.SHARED_PREF_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean( Config.LOGIN_SUCCESS, true );
        editor.putString( Config.EMAIL_PREF, Email.getText().toString() );
        editor.putString( Config.FULLNAME_PREF, Fullname.getText().toString() );
        editor.putString( Config.PASSWORD, Pass.getText().toString() );
        editor.putString( Config.PHONE_PREF, Phone.getText().toString() );
        editor.commit();

        retrofit2.Call<Value> call = ServiceGenerator.createService(MyService.class).daftar(Email.getText().toString(),
                Fullname.getText().toString(), Pass.getText().toString(), Phone.getText().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(retrofit2.Call<Value> call, Response<Value> response) {
                String value = response.body().value;
                String pesan = response.body().message;
                if (value.equals("1")) {
                    Hapus();
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Value> call, Throwable t) {
                Toast.makeText( Register.this, "Koneksi gagal", Toast.LENGTH_SHORT ).show();

            }
        });









    }

    private void Hapus() {
        Fullname.setText( "" );
        Email.setText( "" );
        Phone.setText( "" );
        Pass.setText( "" );
    }
}
