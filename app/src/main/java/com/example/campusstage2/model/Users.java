package com.example.campusstage2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.campusstage2.DatabaseHelper;
import com.example.campusstage2.HashUtil;

public class Users {
    public DatabaseHelper dbHelper;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String name;
    private Integer id;

    public Users(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertUser(String username, String password, String phone,
                           String email, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", HashUtil.hashPassword(password));
        values.put("phone", phone);
        values.put("email", email);
        values.put("name", name);
        db.insert("users", null, values);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
