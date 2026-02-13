package com.example.netpractical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.netpractical.R;
import com.example.netpractical.api.AuthApi;
import com.example.netpractical.client.RetrofitClient;
import com.example.netpractical.dto.LoginReqDTO;
import com.example.netpractical.dto.TokenDTO;
import com.example.netpractical.manager.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginBtn = findViewById(R.id.button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                login(email, password);
            }
        });





    }

    private void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }else {
            Retrofit instance = RetrofitClient.getInstanse(this);
            AuthApi authApi = instance.create(AuthApi.class);
/// /////////////////////////////
            Log.d("LoginActivity","Email :" + email);
            Log.d("LoginActivity","Password :" + password);

            Log.d("LoginActivity","AuthAPI :" + authApi.toString());

            LoginReqDTO dto = new LoginReqDTO(email,password);
            Call<TokenDTO> tokenDTOCall = authApi.userLogin(dto);
            tokenDTOCall.enqueue(new Callback<TokenDTO>() {
                @Override
                public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {

/// ///////////////////////////////////
                    Log.d("LoginActivity","RESPONSE :" + response.toString());

                    if (response.isSuccessful()){
/// ////////////////////////////////////
                        Log.d("LoginActivity","Response_IS_Success :" + response.toString());

                        TokenDTO tokenDTO = response.body();
                        if (tokenDTO != null){
/// ////////////////////////////////////
                            Log.d("LoginActivity","Token :" + tokenDTO.getAccessToken());

                            TokenManager.saveTokens(LoginActivity.this,tokenDTO);
                            Intent intent = new Intent(LoginActivity.this,StudentListActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<TokenDTO> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }

    }
}