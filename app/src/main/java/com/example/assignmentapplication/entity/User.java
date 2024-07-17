package com.example.assignmentapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    public String username;
    public String password;

    public String email;
    public int role;
    public long createdAt;

    public User(String username, String password, String email, int role, long createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }
}
