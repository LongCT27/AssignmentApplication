package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Purchases",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Purchase {
    @PrimaryKey(autoGenerate = true)
    public int purchaseId;

    public int userId;
    public double totalPrice;
    public long purchaseDate;

    public Purchase(int userId, double totalPrice, long purchaseDate) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }
}

