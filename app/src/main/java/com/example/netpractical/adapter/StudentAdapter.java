package com.example.netpractical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netpractical.R;
import com.example.netpractical.dto.StudentDTO;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final List<StudentDTO> list;

    public StudentAdapter(List<StudentDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentDTO studentDTO = list.get(position);
        holder.studentName.setText(studentDTO.getName());
        holder.studentAge.setText(String.valueOf(studentDTO.getAge()));
        holder.studentCourse.setText(studentDTO.getCourse());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView studentName,studentAge,studentCourse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.studentName = itemView.findViewById(R.id.studentName);
            this.studentAge = itemView.findViewById(R.id.studentAge);
            this.studentCourse = itemView.findViewById(R.id.studentCourse);
        }

        public TextView getStudentName() {
            return studentName;
        }

        public TextView getStudentAge() {
            return studentAge;
        }

        public TextView getStudentCourse() {
            return studentCourse;
        }
    }
}
