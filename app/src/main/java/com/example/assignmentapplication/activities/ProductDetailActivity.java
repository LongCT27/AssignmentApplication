package com.example.assignmentapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private NumberPicker quantityPicker;
    private Button addToCartButton;
    private ShopDatabase shopDatabase;
    private ShopDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        productImage = findViewById(R.id.product_image);
        quantityPicker = findViewById(R.id.quantity_picker);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        shopDatabase = ShopDatabaseInstance.getDatabase(getApplicationContext());
        dao = shopDatabase.shopDao();

        // Get product details from intent
        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            // Fetch product from database
            Product product = dao.getProductById(productId);
            if (product != null) {
                productName.setText(product.productName);
                productDescription.setText(product.description);
                productPrice.setText(String.format("$%.2f", product.price));
                productImage.setImageResource(R.drawable.placeholder); // Placeholder image
            }
        }

        quantityPicker.setMinValue(1);
        quantityPicker.setMaxValue(100);

        addToCartButton.setOnClickListener(v -> {
            int quantity = quantityPicker.getValue();
            // Add product to cart with specified quantity
            // Here you can implement the logic to add the product to the cart
            Toast.makeText(ProductDetailActivity.this, "Added to cart: " + quantity + " items", Toast.LENGTH_SHORT).show();
        });
    }
}