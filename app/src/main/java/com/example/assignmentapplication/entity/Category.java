package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int categoryId;

    public String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}

