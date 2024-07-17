package com.example.assignmentapplication.manageProductActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.mangeUserActivity.AddUserActivity;
import com.example.assignmentapplication.mangeUserActivity.ItemUserDecoration;
import com.example.assignmentapplication.mangeUserActivity.ListUserActivity;
import com.example.assignmentapplication.room.ShopDatabaseSingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageProductActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PRODUCT = 1;

    RecyclerView recyclerViewProduct;
    FloatingActionButton fabAddProduct;
    ProductManageAdapter productAdapter;
    ItemUserDecoration itemUserDecoration;
    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProduct = findViewById(R.id.reycle_view_product_manage);
        productAdapter = new ProductManageAdapter(this);
        loadData();
        productAdapter.setOnProductItemClickListener(new ProductManageAdapter.IOnProductItemClickListener() {

            @Override
            public void updateProduct(Product product) {
                Intent intent = new Intent(ManageProductActivity.this, UpdateProductActivity.class);
                intent.putExtra("product", product);
                startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);
            }

            @Override
            public void deleteProduct(Product product) {
                showConfirmDialog(product.productId);
            }
        });

        recyclerViewProduct.setAdapter(productAdapter);
        itemUserDecoration = new ItemUserDecoration(30);
        recyclerViewProduct.addItemDecoration(itemUserDecoration);
        recyclerViewProduct.setLayoutManager(layoutManager);

        fabAddProduct = findViewById(R.id.fab_product_manage);
        fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ManageProductActivity.this, AddProductActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADD_PRODUCT);
        });
    }

    private void loadData() {
        products = ShopDatabaseSingleton.getInstance(this).shopDao().getAllProducts();
        productAdapter.setProductList(products);
    }

    private void showConfirmDialog(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product product = ShopDatabaseSingleton.getInstance(ManageProductActivity.this).shopDao().getProductById(userId);
                        ShopDatabaseSingleton.getInstance(ManageProductActivity.this).shopDao().deleteProduct(product);
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == RESULT_OK) {
            loadData();
        }
    }
}