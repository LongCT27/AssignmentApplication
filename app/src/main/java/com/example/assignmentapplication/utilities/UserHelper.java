package com.example.assignmentapplication.utilities;

import android.content.Intent;
import android.os.Environment;

import com.example.assignmentapplication.activity.LoginActivity;
import com.example.assignmentapplication.entity.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class UserHelper {
    public static void saveUserInfoExternal(User user) {
        if (!isExternalStorageWritable()) {
            //showToast("External Storage is not available");
            return;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "userInfo.txt");
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.append("Username: ").append(user.username).append("\n");
            writer.append("Email: ").append(user.email).append("\n");
            writer.flush();
            // showToast("User info saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            // showToast("Failed to save user info");
            e.printStackTrace();
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    public static String getUserEmailFromFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "userInfo.txt");
        String email = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Email: ")) {
                    email = line.substring(7); // Remove "Email: " prefix
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or errors as needed
        }
        return email;
    }
}
