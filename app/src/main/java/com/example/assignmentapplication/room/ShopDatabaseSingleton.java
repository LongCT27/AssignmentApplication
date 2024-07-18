package com.example.assignmentapplication.room;
import android.content.Context;
import androidx.room.Room;

public class ShopDatabaseSingleton {

    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ShopDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
