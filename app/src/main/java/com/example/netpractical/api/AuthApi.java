package com.example.netpractical.api;

import com.example.netpractical.dto.LoginReqDTO;
import com.example.netpractical.dto.TokenDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/refresh")
    Call<TokenDTO> refreshAccessToken(@Body TokenDTO tokenDTO);

    @POST("auth/login")
    Call<TokenDTO> userLogin (@Body LoginReqDTO requestDTO);
}
