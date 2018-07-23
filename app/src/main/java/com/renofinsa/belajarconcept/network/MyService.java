package com.renofinsa.belajarconcept.network;

import com.renofinsa.belajarconcept.model.Users;
import com.renofinsa.belajarconcept.model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyService {
    @FormUrlEncoded
    @POST("register.php")
    Call<Value> daftar(@Field( "email" )String email,
                       @Field( "pass" )String pass,
                       @Field( "fullname" )String fullname,
                       @Field( "phone" )String phone
                       );

    @FormUrlEncoded
    @POST("login.php")
    Call<Users> login(
            @Field( "email" )String email,
            @Field( "pass" )String pass
    );
}
