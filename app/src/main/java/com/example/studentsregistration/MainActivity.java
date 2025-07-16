package com.example.studentsregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_STUDENT = 1;
    private DatabaseHelper databaseHelper;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        studentList = databaseHelper.getAllStudents();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(this, studentList, new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onEdit(Student student) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra("student", student);
                startActivityForResult(intent, REQUEST_ADD_STUDENT);
            }

            @Override
            public void onDelete(Student student) {
                databaseHelper.deleteStudent(student.getId());
                studentList.remove(student);
                studentAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(studentAdapter);

        findViewById(R.id.buttonAddStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddStudentActivity.class), REQUEST_ADD_STUDENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_STUDENT && resultCode == RESULT_OK) {
            studentList.clear();
            studentList.addAll(databaseHelper.getAllStudents());
            studentAdapter.notifyDataSetChanged();
        }
    }
}
