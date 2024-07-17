package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Products",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "categoryId",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int productId;

    public String productName;
    public String description;
    public double price;
    public int categoryId;
    public int amount;
    public String imagePath;

    public Product() {
    }

    public Product(String productName, String description, double price, int categoryId, int amount, String imagePath) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
        this.imagePath = imagePath;
    }

    @Ignore
    public Product(String productName, String description, double price, int categoryId, int amount) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
    }




}
