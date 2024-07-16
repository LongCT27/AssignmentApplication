package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Products",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "categoryId",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE))
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productId;

    public String productName;
    public String description;
    public double price;
    public int categoryId;
    public int amount;

    public Product(String productName, String description, double price, int categoryId, int amount) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
    }


}
