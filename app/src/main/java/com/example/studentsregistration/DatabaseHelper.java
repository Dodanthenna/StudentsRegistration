package com.example.studentsregistration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_SEX = "sex";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_MOBILE = "mobile";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_SEX + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_MOBILE + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_LAST_NAME, student.getLastName());
        values.put(COLUMN_AGE, student.getAge());
        values.put(COLUMN_SEX, student.getSex());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_MOBILE, student.getMobile());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_LAST_NAME, student.getLastName());
        values.put(COLUMN_AGE, student.getAge());
        values.put(COLUMN_SEX, student.getSex());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_MOBILE, student.getMobile());
        db.update(TABLE_STUDENTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_AGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SEX)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE))
                );
                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }
}
