package com.example.studentsregistration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public interface OnStudentClickListener {
        void onEdit(Student student);
        void onDelete(Student student);
    }

    private Context context;
    private List<Student> studentList;
    private OnStudentClickListener listener;

    public StudentAdapter(Context context, List<Student> studentList, OnStudentClickListener listener) {
        this.context = context;
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.txtFirstName.setText(student.getFirstName());
        holder.txtLastName.setText(student.getLastName());
        holder.txtAge.setText(String.valueOf(student.getAge()));
        holder.txtSex.setText(student.getSex());
        holder.txtAddress.setText(student.getAddress());
        holder.txtMobile.setText(student.getMobile());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(student));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(student));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView txtFirstName, txtLastName, txtAge, txtSex, txtAddress, txtMobile;
        ImageButton btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFirstName = itemView.findViewById(R.id.txtFirstName);
            txtLastName = itemView.findViewById(R.id.txtLastName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSex = itemView.findViewById(R.id.txtSex);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
