package com.example.assignmentapplication.manageProductActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDatabaseSingleton;

import java.util.List;

public class ProductManageAdapter extends RecyclerView.Adapter<ProductManageAdapter.ProductManageViewHolder> {
    private Context context;
    private List<Product> productList;
    private IOnProductItemClickListener onProductItemClickListener;

    public interface IOnProductItemClickListener {
        void updateProduct(Product product);

        void deleteProduct(Product product);
    }

    public ProductManageAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void setOnProductItemClickListener(IOnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    @NonNull
    @Override
    public ProductManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_product_manage, null);
        return new ProductManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManageViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productId.setText("Id: " + product.productId);
        holder.productName.setText("Name: " + product.productName);
        Category category = ShopDatabaseSingleton.getInstance(context).shopDao().getCategoryById(product.categoryId);
        holder.productCategory.setText("Category: " + category.categoryName);
        Bitmap bitmap = BitmapFactory.decodeFile(product.imagePath);
        holder.productImage.setImageBitmap(bitmap);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProductItemClickListener.updateProduct(product);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProductItemClickListener.deleteProduct(product);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductManageViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productId;
        TextView productName;
        TextView productCategory;

        ImageButton editButton;
        ImageButton deleteButton;

        public ProductManageViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_img_manage);
            productId = itemView.findViewById(R.id.text_view_product_id);
            productName = itemView.findViewById(R.id.text_view_product_name);
            productCategory = itemView.findViewById(R.id.text_view_product_category);
            editButton = itemView.findViewById(R.id.edit_product_btn);
            deleteButton = itemView.findViewById(R.id.delete_product_btn);
        }
    }
}
