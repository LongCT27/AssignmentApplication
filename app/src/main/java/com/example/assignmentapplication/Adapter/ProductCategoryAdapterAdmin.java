package com.example.assignmentapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;

import java.util.List;

public class ProductCategoryAdapterAdmin extends RecyclerView.Adapter<ProductCategoryAdapterAdmin.ProductCategoryViewHolder> {
    private List<Product> mlist;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product product);


    }


    public ProductCategoryAdapterAdmin(List<Product> mlist, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ProductCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_base_category, parent, false);
        return new ProductCategoryAdapterAdmin.ProductCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryViewHolder holder, int position) {
        Product product = mlist.get(position);
        if (product == null) {
            return;
        }
        String productID = Integer.toString(product.productId);
        String productName = product.productName;
        String productPrice = Double.toString(product.price) ;

        holder.productName.setText(productName);
        holder.productPrice.setText(productPrice);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(product));


    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class ProductCategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;


        public ProductCategoryViewHolder(@NonNull View itemView) {

            super(itemView);

            productName = itemView.findViewById(R.id.productName_Category);

            productPrice = itemView.findViewById(R.id.text_priceCate);
        }

    }


}
