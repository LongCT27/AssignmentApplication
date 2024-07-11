package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "PurchaseDetails",
        primaryKeys = {"purchaseId", "productId"},
        foreignKeys = {
                @ForeignKey(entity = Purchase.class,
                        parentColumns = "purchaseId",
                        childColumns = "purchaseId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Product.class,
                        parentColumns = "productId",
                        childColumns = "productId",
                        onDelete = ForeignKey.CASCADE)
        })
public class PurchaseDetail {
    public int purchaseId;
    public int productId;
    public int quantity;

    public PurchaseDetail(int purchaseId, int productId, int quantity) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
    }
}

