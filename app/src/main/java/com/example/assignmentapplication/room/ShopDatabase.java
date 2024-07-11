package com.example.assignmentapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.assignmentapplication.entity.*;

@Database(entities = {User.class, Product.class, Category.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract ShopDao shopDao();
}
