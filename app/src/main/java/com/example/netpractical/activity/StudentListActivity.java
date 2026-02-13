package com.example.netpractical.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netpractical.R;
import com.example.netpractical.adapter.StudentAdapter;
import com.example.netpractical.api.StudentApi;
import com.example.netpractical.client.RetrofitClient;
import com.example.netpractical.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<StudentDTO> studentDTOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        this.recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadAllStudent();

    }
    private void loadAllStudent(){
        Retrofit retrofit = RetrofitClient.getInstanse(this);
        StudentApi studentApi = retrofit.create(StudentApi.class);
        Call<List<StudentDTO>>listCall = studentApi.getAllStudents();
        listCall.enqueue(new Callback<List<StudentDTO>>() {
            @Override
            public void onResponse(Call<List<StudentDTO>> call, Response<List<StudentDTO>> response) {
                if (response.isSuccessful()){
                   studentDTOS = response.body();
                    if (studentDTOS != null && !studentDTOS.isEmpty()){
                        StudentAdapter studentAdapter = new StudentAdapter(studentDTOS);
                        recyclerView.setAdapter(studentAdapter);
                        setDataForSerchBar();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StudentDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void setDataForSerchBar(){
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.searchInput);
        if (studentDTOS != null && !studentDTOS.isEmpty()){
            List<String> names = new ArrayList<>();
            for (StudentDTO dto : studentDTOS){
             names.add(dto.getName());
            }
//            String[] namesArray = names.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,names);
            autoCompleteTextView.setAdapter(adapter);
        }

    }
}