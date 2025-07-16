package com.example.studentsregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editFirstName, editLastName, editAge, editSex, editAddress, editMobile;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private Student student;
    private TextView topic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        databaseHelper = new DatabaseHelper(this);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editAge = findViewById(R.id.editAge);
        editSex = findViewById(R.id.editSex);
        editAddress = findViewById(R.id.editAddress);
        editMobile = findViewById(R.id.editMobile);
        buttonSave = findViewById(R.id.buttonSave);
        topic2 = findViewById(R.id.texttopic2);

        Intent intent = getIntent();
        if (intent.hasExtra("student")) {
            student = (Student) intent.getSerializableExtra("student");
            editFirstName.setText(student.getFirstName());
            editLastName.setText(student.getLastName());
            editAge.setText(String.valueOf(student.getAge()));
            editSex.setText(student.getSex());
            editAddress.setText(student.getAddress());
            editMobile.setText(student.getMobile());
            buttonSave.setText("Update");
            topic2.setText("Update");
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                int age = Integer.parseInt(editAge.getText().toString());
                String sex = editSex.getText().toString();
                String address = editAddress.getText().toString();
                String mobile = editMobile.getText().toString();

                if (student == null) {
                    databaseHelper.addStudent(new Student(firstName, lastName, age, sex, address, mobile));
                } else {
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setAge(age);
                    student.setSex(sex);
                    student.setAddress(address);
                    student.setMobile(mobile);
                    databaseHelper.updateStudent(student);
                }

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
