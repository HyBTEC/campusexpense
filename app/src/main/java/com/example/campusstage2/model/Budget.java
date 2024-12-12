package com.example.campusstage2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.campusstage2.DatabaseHelper;

public class Budget {
    private DatabaseHelper dbHelper;
    private Integer id;
    private int amount;
    private Integer categoryId;
    private Integer userId;
    private String startDate;
    private String endDate;
    private String categoryName;
    private int remaining;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void insertBudget(int amount, Integer categoryId, Integer userId, String startDate, String endDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("remaining", amount);
        values.put("category_id", categoryId);
        values.put("user_id", userId);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        db.insert("budgets", null, values);
    }

    public Budget(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public Budget(Integer id, int amount, Integer categoryId, Integer userId, String startDate, String endDate) {
        this.setId(id);
        this.setAmount(amount);
        this.setCategoryId(categoryId);
        this.setUserId(userId);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }
    public void deleteBudget(int budgetId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("budgets", "id = ?", new String[]{String.valueOf(budgetId)});
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
