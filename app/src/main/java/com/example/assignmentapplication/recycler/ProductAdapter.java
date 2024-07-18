package com.example.assignmentapplication.recycler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.activity.ProductDetailActivity;
import com.example.assignmentapplication.activity.ProductListActivity;
import com.example.assignmentapplication.entity.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {



    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.productName);
        holder.productDescription.setText(product.description);
        holder.productPrice.setText(String.format("$%.2f", product.price));
        holder.productStock.setText(String.format("Stock: %d",product.amount));

        //Set Image
        if (product.imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(product.imagePath);
            holder.productImage.setImageBitmap(bitmap);
        }

        //Out of stock?
        if (product.amount == 0){
            holder.btnAddToCart.setEnabled(false);
        }

        // Set click listeners for buttons (they do nothing for now)
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_id", product.productId);
                context.startActivity(intent);
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListActivity activity = (ProductListActivity) context;
                activity.addItemToCart(productList.get(position).productId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productDescription, productPrice, productStock;
        Button btnViewDetails, btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            productStock = itemView.findViewById(R.id.product_stock);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
