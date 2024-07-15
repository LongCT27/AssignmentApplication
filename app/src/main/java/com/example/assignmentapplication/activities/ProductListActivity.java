package com.example.assignmentapplication.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.example.assignmentapplication.room.ShopDatabaseInstance;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ShopDatabase shopDatabase;
    private ShopDao dao;

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
        shopDatabase = ShopDatabaseInstance.getDatabase(getApplicationContext());
        dao = shopDatabase.shopDao();

        List<Product> productList = dao.getAllProducts();

        productAdapter = new ProductAdapter(this,productList);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);

        MenuItem searchItem = menu.findItem(R.id.product_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search products...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // Handle search query
                List<Product> products = dao.queryProducts("%" + query + "%");
                productAdapter.setProductList(products);
                productAdapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void populateDatabaseWithMockData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    products.add(new Product("Smartphone", "Latest model smartphone", 699.99, insertedCategories.get(0).categoryId,10));
                    products.add(new Product("Laptop", "Powerful gaming laptop", 1199.99, insertedCategories.get(0).categoryId,10));
                    products.add(new Product("Novel", "Bestselling novel", 14.99, insertedCategories.get(1).categoryId,10));
                    products.add(new Product("Textbook", "Advanced programming textbook", 59.99, insertedCategories.get(1).categoryId,10));
                    products.add(new Product("T-shirt", "Comfortable cotton T-shirt", 9.99, insertedCategories.get(2).categoryId,10));
                    products.add(new Product("Jeans", "Stylish denim jeans", 49.99, insertedCategories.get(2).categoryId,10));
                    for (Product product : products) {
                        shopDatabase.shopDao().insertProduct(product);
                    }
                }
            }
        }).start();
    }

}