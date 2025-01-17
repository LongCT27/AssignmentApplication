package com.example.assignmentapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Cart;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;
import com.example.assignmentapplication.room.ShopDatabaseInstance;
import com.example.assignmentapplication.utilities.UserHelper;
import com.example.assignmentapplication.utils.CartLogicHandlerUtils;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private NumberPicker quantityPicker;
    private Button addToCartButton;
    private ShopDatabase shopDatabase;
    private ShopDao dao;
    private int productId;

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

        //Image


        // Get product details from intent
        productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            // Fetch product from database
            Product product = dao.getProductById(productId);
            if (product != null) {
                productName.setText(product.productName);
                productDescription.setText(product.description);
                productPrice.setText(String.format("$%.2f", product.price));
                productImage.setImageResource(R.drawable.placeholder); // Placeholder image
                if (product.imagePath != null){
                    Bitmap bitmap = BitmapFactory.decodeFile(product.imagePath);
                    productImage.setImageBitmap(bitmap);
                }
                //Check for amount able to add
                quantityPicker.setMinValue(1);
                Cart cart = dao.getUserCartOfProduct(1, productId);
                if (cart == null){
                    quantityPicker.setMinValue(1);
                    quantityPicker.setMaxValue(product.amount);
                } else {
                    int max = product.amount - cart.quantity;
                    if (max > 0){
                        quantityPicker.setMinValue(1);
                        quantityPicker.setMaxValue(max);
                    } else {
                        quantityPicker.setMinValue(0);
                        quantityPicker.setMaxValue(0);
                        quantityPicker.setValue(0);
                        addToCartButton.setEnabled(false);
                    }
                }

            }
        }



        addToCartButton.setOnClickListener(v -> {
            int quantity = quantityPicker.getValue();
            // Add product to cart with specified quantity
            boolean result = CartLogicHandlerUtils.addItemToCart(dao, UserHelper.getUserIDFromFile(), productId, quantity);
            if (result){
                Toast.makeText(ProductDetailActivity.this, "Added to cart: " + quantity + " items", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductDetailActivity.this, "Failed to add. Quantity exceeded.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}