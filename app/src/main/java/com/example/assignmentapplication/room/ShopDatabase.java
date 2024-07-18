package com.example.assignmentapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.assignmentapplication.entity.*;

@Database(entities = {User.class, Product.class, Category.class, Cart.class, Purchase.class, PurchaseDetail.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract ShopDao shopDao();
}

