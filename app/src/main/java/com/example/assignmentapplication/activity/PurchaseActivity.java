package com.example.assignmentapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Purchase;
import com.example.assignmentapplication.recycler.PurchaseAdapter;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;
import com.example.assignmentapplication.utilities.UserHelper;

import java.util.List;

public class PurchaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private ShopDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.viewRecyclePurchaseList);
        dao = ShopDatabaseInstance.getDatabase(this).shopDao();
        int userId = UserHelper.getUserIDFromFile();
        if(userId != -1){
            List<Purchase> purchases = dao.getAllPurchasesByUserId(userId);
            purchaseAdapter = new PurchaseAdapter(purchases, this);
            purchaseAdapter.setOnItemClickListener(new PurchaseAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(int position) {

                }

                @Override
                public void onDetailClick(Purchase purchase) {
                    Toast.makeText(PurchaseActivity.this,"Detail Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PurchaseActivity.this, PurchaseDetailActivity.class);
                    intent.putExtra("purchaseId", purchase.purchaseId);
                    startActivity(intent);
                    // intent to Purchase Detail
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(purchaseAdapter);
        }

    }
}