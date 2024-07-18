package com.example.assignmentapplication.ActivityTuanNM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.manageProductActivity.ManageProductActivity;
import com.example.assignmentapplication.mangeUserActivity.ListUserActivity;

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

    }
}
