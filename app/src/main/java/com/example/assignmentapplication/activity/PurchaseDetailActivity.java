package com.example.assignmentapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.PurchaseDetail;
import com.example.assignmentapplication.recycler.PurchaseAdapter;
import com.example.assignmentapplication.recycler.PurchaseDetailAdapter;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

import java.util.List;

public class PurchaseDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PurchaseDetailAdapter purchaseDetailAdapter;
    private ShopDao dao;
    private int purchaseId;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase_detail);
        purchaseId = getIntent().getIntExtra("purchaseId", -1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.viewRecyclePurchaseDetailList);
        dao = ShopDatabaseInstance.getDatabase(this).shopDao();
        if(purchaseId != -1){
            List<PurchaseDetail> purchaseDetails= dao.getPurchaseDetailsByPurchaseId(purchaseId);
            purchaseDetailAdapter = new PurchaseDetailAdapter(purchaseDetails, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(purchaseDetailAdapter);
        } else {
            Toast.makeText(this, "Invalid Purchase ID", Toast.LENGTH_SHORT).show();
        }
        btnBack = findViewById(R.id.btnBackToPurchase);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PurchaseDetailActivity.this, PurchaseActivity.class);
            startActivity(intent);
            finish();
        });

    }
}