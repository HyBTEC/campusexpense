package com.example.campusstage2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.campusstage2.DatabaseHelper;

import java.util.LinkedHashMap;
import java.util.Map;

public class Expense {
    public DatabaseHelper dbHelper;
    private Integer id;
    private int amount;
    private Integer categoryId;
    private Integer userId;
    private String date;
    private String note;
    private String categoryName;

    // Getter for categoryName
    public String getCategoryName() {
        return categoryName;
    }

    // Setter for categoryName
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Constructor
    public Expense(Integer id, int amount, Integer categoryId, Integer userId, String date) {
        this.setId(id);
        this.setAmount(amount);
        this.setCategoryId(categoryId);
        this.setUserId(userId);
        this.setDate(date);
    }

    public void insertExpense(int amount, Integer categoryId, Integer userId, String date, String note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("category_id", categoryId);
        values.put("user_id", userId);
        values.put("note", note);
        values.put("date", date);
        db.insert("expense", null, values);
    }

    public Expense(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map<String, Integer> getSumAmountByDay() {
        Map<String, Integer> result = new LinkedHashMap<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String query = "SELECT date, SUM(amount) as total FROM expense GROUP BY date ORDER BY date";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                result.put(date, total);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
}