package com.example.netpractical.intercepter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.netpractical.api.AuthApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class Authinercepter implements Interceptor {
    private Context context;
    private AuthApi authApi;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return null;
    }
}
