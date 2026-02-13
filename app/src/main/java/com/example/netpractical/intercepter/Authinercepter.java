package com.example.netpractical.intercepter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.netpractical.api.AuthApi;
import com.example.netpractical.client.RetrofitClient;
import com.example.netpractical.dto.TokenDTO;
import com.example.netpractical.manager.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authinercepter implements Interceptor {
    private Context context;
    private static final String AUTHORIZATION = "Authorization";
    private static final int UNAUTHORIZED = 401;


    private AuthApi authApi;

    public Authinercepter(Context context) {
        this.context = context.getApplicationContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi = retrofit.create(AuthApi.class);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalReq = chain.request();
        String url = originalReq.url().encodedPath();
        if(url.contains("/auth/login") || url.contains("/auth/refresh")){
            return chain.proceed(originalReq);
        }

        String accessToken = TokenManager.retrieveAccessToken(context);
        Request request = originalReq.newBuilder().header(Authinercepter.AUTHORIZATION,"Bearer " + accessToken).build();
        Response response = chain.proceed(request);

        if (response.code()==Authinercepter.UNAUTHORIZED){
            //refresh logic
            response.close();
            synchronized (this){
                String newAccess = fetchRefreshToken();
                if (newAccess != null){
                    Request newRequest = originalReq.newBuilder().header(Authinercepter.AUTHORIZATION,"Bearer " + newAccess).build();
                    return chain.proceed(newRequest);
                }
            }
        }
        return response;


    }
    private String fetchRefreshToken(){
        try{
            String refreshToken = TokenManager.retrieveRefreshToken(context);           ///retritive refresh token
//            if(refreshToken == null){
//                return null;
//            }
            TokenDTO dto = new TokenDTO();                ///TokenDTO object
            dto.setRefreshToken(refreshToken);                    ///attach refresh token to dto
            Call<TokenDTO> tokenDTOCall = authApi.refreshAccessToken(dto);       ///make tokenDTOCall event
            retrofit2.Response<TokenDTO> response = tokenDTOCall.execute();   ///synchoronous call ==> stop other execution

            if(response.isSuccessful()){
              TokenDTO tokenDTO = response.body();
              if(tokenDTO != null) {
                  TokenManager.saveTokens(context, tokenDTO); /// save token again
                  return tokenDTO.getAccessToken(); ///return new access token
              }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /// when faild -> logout
            TokenManager.clearTokens(context); /// clear all tokens for shared preference
            return null;
    }
}
