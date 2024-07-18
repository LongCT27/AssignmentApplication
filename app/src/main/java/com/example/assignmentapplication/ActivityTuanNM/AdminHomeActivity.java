package com.example.assignmentapplication.ActivityTuanNM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.activity.LoginActivity;
import com.example.assignmentapplication.activity.ProductListActivity;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.manageProductActivity.ManageProductActivity;
import com.example.assignmentapplication.mangeUserActivity.ListUserActivity;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;
import com.example.assignmentapplication.utilities.UserHelper;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminhome), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout cate = findViewById(R.id.Category_admin);
        cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageCategoryActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout user = findViewById(R.id.User_admin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout purchase = findViewById(R.id.Purchase_admin);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManagePurchaseActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout product = findViewById(R.id.Product_admin);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageProductActivity.class);
                startActivity(intent);
            }
        });
        //Username
        ShopDao shopDao = ShopDatabaseInstance.getDatabase(this).shopDao();
        User user1 = shopDao.getUserById(UserHelper.getUserIDFromFile());
        TextView username = findViewById(R.id.UserName);
        username.setText(user1.username);
        //Logout
        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserHelper.saveUserInfoExternal(null);
                    Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.create().show();
        });
    }
}
