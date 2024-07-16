package com.example.assignmentapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Cart;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.recycler.CartAdapter;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;
import com.example.assignmentapplication.utils.CartLogicHandlerUtils;
import com.example.assignmentapplication.utils.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private Button checkoutButton;
    private ShopDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycler_view_cart);
        checkoutButton = findViewById(R.id.button_checkout);

        // Fetch cart items from the database
        dao = ShopDatabaseInstance.getDatabase(this).shopDao();
        List<Cart> cartItemList = dao.getUserCart(UserInfoUtils.GetUserId());

        //Verify
        List<Cart> soldOut = new ArrayList<>();
        boolean itemSoldOut = false;
        boolean itemQuantityChanged = false;
        for (int i = 0; i < cartItemList.size(); i++) {
            Cart cart = cartItemList.get(i);
            Product product = dao.getProductById(cart.productId);
            //Sold out?
            if (product == null || product.amount == 0){
                dao.deleteCart(cart);
                soldOut.add(cart);
                itemSoldOut = true;
            }
            //Less items?
            else if (product.amount < cart.quantity){
                cart.quantity = product.amount;
                dao.updateCart(cart);
                itemQuantityChanged = true;
            }
        }
        for (Cart cart : soldOut
             ) {
            cartItemList.remove(cart);
        }
        if (itemSoldOut){
            Toast.makeText(this,"An item in your cart was sold out, and was removed from your cart.",Toast.LENGTH_SHORT)
                    .show();
        }
        if (itemQuantityChanged){
            Toast.makeText(this,"An item in your cart has its quantity changed due to being bought.",Toast.LENGTH_SHORT)
                    .show();
        }

        // Set up RecyclerView
        cartAdapter = new CartAdapter(this, cartItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        // Handle checkout button click
        checkoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Checkout Confirmation");
            builder.setMessage(String.format("Total Price: $%.2f\nDo you want to proceed with the checkout?",
                    CartLogicHandlerUtils.getTotalPrice(dao,UserInfoUtils.GetUserId())));
            builder.setPositiveButton("Yes", (dialog, which) -> performCheckout());
            builder.setNegativeButton("No", null);
            builder.create().show();
        });
    }

    private void performCheckout() {
        boolean result = CartLogicHandlerUtils.checkout(dao, UserInfoUtils.GetUserId());
        if (result){
            // Redirect to ProductListActivity
            Toast.makeText(this,"Checkout completed.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CartActivity.this, ProductListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Close the CartActivity
        } else {
            Toast.makeText(this,"Something went wrong. Please try again later.",Toast.LENGTH_SHORT).show();
        }
    }
}