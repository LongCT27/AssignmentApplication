package com.example.assignmentapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmentapplication.Adapter.CategoryAdapterAdmin;
import com.example.assignmentapplication.Adapter.ProductCategoryAdapterAdmin;
import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private CategoryAdapterAdmin adapter;
    private ProductCategoryAdapterAdmin adapter2;
    static List<Product> listProductBaseOnCategory = new ArrayList<>();
    static List<Category> list = new ArrayList<>();
    ShopDatabase db;
    ShopDao shopDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_category_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView232 = (ImageView) findViewById(R.id.imgBack);
        imageView232.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView imgAdd = findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                showCreate();
                adapter.notifyDataSetChanged();
            }
        });

        initRoomDatabase();
        //addProduct();
        list = shopDao.getAllCategories();
        recyclerView = findViewById(R.id.rcv);
        adapter = new CategoryAdapterAdmin(list, new CategoryAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(Category cate) {
                showCart(cate.categoryId);
            }

            @Override
            public void onRemoveClick(Category cate) {
                showEdit(cate);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 2);


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initRoomDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), ShopDatabase.class, "new").allowMainThreadQueries() // Avoid in production!
                .build();
        shopDao = db.shopDao();
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
        adapter2 = new ProductCategoryAdapterAdmin(listProductBaseOnCategory, new ProductCategoryAdapterAdmin.OnItemClickListener() {
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

    private void showCreate() {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_create_category, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        EditText cateName = popupView.findViewById(R.id.category_name);

        Button add = popupView.findViewById(R.id.create_cate);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = new Category(cateName.getText().toString());
                addProduct(category);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showEdit(Category category) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_edit_category, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        EditText cateName = popupView.findViewById(R.id.edit_category_name);
        cateName.setText(category.categoryName);
        Button add = popupView.findViewById(R.id.edit_cate);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.categoryName = cateName.getText().toString();
                updateCate(category);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addProduct(Category category) {
        shopDao.insertCategory(category); // Add the category to the database

        // Update the list of categories from the database
        list.clear();
        list.addAll(shopDao.getAllCategories());

        // Notify the adapter that the dataset has changed
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCate(Category category) {
        shopDao.updateCategory(category);
        adapter.notifyDataSetChanged();
    }
}