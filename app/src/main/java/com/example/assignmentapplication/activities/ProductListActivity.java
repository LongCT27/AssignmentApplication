package com.example.assignmentapplication.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.recycler.ProductAdapter;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ShopDatabase shopDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        shopDatabase = Room.databaseBuilder(getApplicationContext(), ShopDatabase.class,"database")
                .allowMainThreadQueries().build();
        ShopDao dao = shopDatabase.shopDao();

        // Mock data for testing

        List<Product> productList = dao.getAllProducts();

        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
    }

    private void populateDatabaseWithMockData() {
        new Thread(() -> {
            if (shopDatabase.shopDao().getAllProducts().isEmpty()) {
                // Insert categories
                List<Category> categories = new ArrayList<>();
                categories.add(new Category("Electronics"));
                categories.add(new Category("Books"));
                categories.add(new Category("Clothing"));
                for (Category category : categories) {
                    shopDatabase.shopDao().insertCategory(category);
                }

                // Retrieve categories with their IDs
                List<Category> insertedCategories = shopDatabase.shopDao().getAllCategories();

                // Insert products
                List<Product> products = new ArrayList<>();
                products.add(new Product("Smartphone", "Latest model smartphone", 699.99, insertedCategories.get(0).categoryId));
                products.add(new Product("Laptop", "Powerful gaming laptop", 1199.99, insertedCategories.get(0).categoryId));
                products.add(new Product("Novel", "Bestselling novel", 14.99, insertedCategories.get(1).categoryId));
                products.add(new Product("Textbook", "Advanced programming textbook", 59.99, insertedCategories.get(1).categoryId));
                products.add(new Product("T-shirt", "Comfortable cotton T-shirt", 9.99, insertedCategories.get(2).categoryId));
                products.add(new Product("Jeans", "Stylish denim jeans", 49.99, insertedCategories.get(2).categoryId));
                for (Product product : products) {
                    shopDatabase.shopDao().insertProduct(product);
                }
            }
        }).start();
    }
}