package com.example.assignmentapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.Purchase;
import com.example.assignmentapplication.entity.PurchaseDetail;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManagePurchase extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private PurchaseAdapter adapter;

    static List<PurchaseDetail> listPurchaseDetailBaseOnPurchaseID = new ArrayList<>();
    static List<Purchase> list = new ArrayList<>();
    ShopDatabase db;
    ShopDao shopDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_purchase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initRoomDatabase();
        //  addProduct();
        list = shopDao.getAllPurchases();
        recyclerView = findViewById(R.id.rcv3);
        adapter = new PurchaseAdapter(list, new PurchaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Purchase cate) {

            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 1);


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initRoomDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), ShopDatabase.class, "new").allowMainThreadQueries() // Avoid in production!
                .build();
        shopDao = db.shopDao();
    }


}