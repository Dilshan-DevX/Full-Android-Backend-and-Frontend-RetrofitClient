package com.example.netpractical.api;

import com.example.netpractical.dto.StudentDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentApi {
    @GET("student/get-all")
    Call<List<StudentDTO>> getAllStudents();
}
