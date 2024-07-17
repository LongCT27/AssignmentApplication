package com.example.assignmentapplication.utilities;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.assignmentapplication.entity.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class UserHelper {
    public static void saveUserInfoExternal(User user) {
        String uidStr = user != null ? Integer.toString(user.userId) : "-1";
        if (!isExternalStorageWritable()) {
            //showToast("External Storage is not available");
            return;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "userInfo.txt");
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.append("ID: ").append(uidStr).append("\n");
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
    //return -1 if can't not find
    public static int getUserIDFromFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "userInfo.txt");
        int Id = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    Log.d("UserHelper", "getUserIDFromFile: " + line.toString());
                    Id = Integer.parseInt(line.substring(4)); // Remove "Id: " prefix
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or errors as needed
        }
        return Id;
    }
}
