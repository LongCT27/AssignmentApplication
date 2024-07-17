package com.example.assignmentapplication.room;

import android.content.Context;

import androidx.room.Room;

public class ShopDatabaseInstance {
    private static ShopDatabase database;

    public static ShopDatabase getDatabase(Context context){
        if (database == null){
            database = Room.databaseBuilder(context,ShopDatabase.class,"database")
                    .allowMainThreadQueries()
                    .addMigrations(Migrations.MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
