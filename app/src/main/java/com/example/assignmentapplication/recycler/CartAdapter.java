package com.example.assignmentapplication.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.activity.ProductDetailActivity;
import com.example.assignmentapplication.entity.Cart;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItemList;
    private Context context;

    public CartAdapter(Context context, List<Cart> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cartItem = cartItemList.get(position);
        Product product = ShopDatabaseInstance.getDatabase(context).shopDao().getProductById(cartItem.productId);

        if (product != null) {
            holder.productName.setText(product.productName);
            holder.productPrice.setText(String.format("$%.2f", product.price));
            holder.productQuantity.setText(String.format("Quantity: %d", cartItem.quantity));
            holder.productImage.setImageResource(R.drawable.placeholder); // Placeholder image

            holder.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product_id", product.productId);
                    context.startActivity(intent);
                }
            });

            holder.numberPicker.setMinValue(0);
            holder.numberPicker.setMaxValue(product.amount);
            holder.numberPicker.setValue(cartItem.quantity);

            holder.buttonSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity = holder.numberPicker.getValue();
                    if (newQuantity == 0) {
                        ShopDatabaseInstance.getDatabase(context).shopDao().deleteCart(cartItem);
                        cartItemList.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        cartItem.quantity = newQuantity;
                        ShopDatabaseInstance.getDatabase(context).shopDao().updateCart(cartItem);
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        ImageView productImage;
        NumberPicker numberPicker;
        Button buttonSet;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productImage = itemView.findViewById(R.id.product_image);
            numberPicker = itemView.findViewById(R.id.number_picker);
            buttonSet = itemView.findViewById(R.id.button_set);
        }
    }
}
