package com.example.assignmentapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

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
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageCategory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private CategoryAdapter adapter;
    private ProductCategoryAdapter adapter2;
    static List<Product> listProductBaseOnCategory = new ArrayList<>();
    static List<Category> list = new ArrayList<>();
    ShopDatabase db;
    ShopDao shopDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initRoomDatabase();
       //addProduct();
        list = shopDao.getAllCategories();
        recyclerView = findViewById(R.id.rcv);
        adapter = new CategoryAdapter(list, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category cate) {
                showCart(cate.categoryId);
            }

            @Override
            public void onRemoveClick(Category cate) {

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

    @SuppressLint("NotifyDataSetChanged")
    private void addProduct() {
    //    shopDao.insertProduct(new Product("product23","no",12.2,1));
       // shopDao.insertUser(new User("tuan","123","tuan@gmail.com",1,System.currentTimeMillis()));
   //     shopDao.insertPurchase(new Purchase(1,12321,System.currentTimeMillis()));
        shopDao.insertPurchaseDetail(new PurchaseDetail(2,1,2));
        shopDao.insertPurchaseDetail(new PurchaseDetail(2,3,11));
//shopDao.insertPurchaseDetail(new PurchaseDetail(1,3,13));
    }

    private void showCart(int CategoryID) {
        // Inflate the custom layout/view
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_product_base_category, null);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();

        // Initialize recyclerView2 from popupView
        recyclerView2 = popupView.findViewById(R.id.rcv2);


        listProductBaseOnCategory = shopDao.getAllProductBaseOnCategoryID(CategoryID);
        // Create an ArrayAdapter
        adapter2 = new ProductCategoryAdapter(listProductBaseOnCategory, new ProductCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Handle item click
            }

        });

        GridLayoutManager manager2 = new GridLayoutManager(this, 1);
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.setAdapter(adapter2);
        // Show the dialog
        dialog.show();
    }
}