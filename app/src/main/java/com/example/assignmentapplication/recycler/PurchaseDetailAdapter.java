package com.example.assignmentapplication.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.Purchase;
import com.example.assignmentapplication.entity.PurchaseDetail;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

import java.util.Date;
import java.util.List;

public class PurchaseDetailAdapter extends RecyclerView.Adapter<PurchaseDetailAdapter.PurchaseDetailViewHolder> {

    ShopDao dao;

    private List<PurchaseDetail> mPurchaseDetails;
    private Context mContext;
    private PurchaseAdapter.OnItemClickListener onItemClickListener;

    public void updateData(List<PurchaseDetail> temp) {
        this.mPurchaseDetails = temp;
    }

    public PurchaseDetailAdapter(List<PurchaseDetail> mPurchaseDetails, Context mContext) {
        this.dao = ShopDatabaseInstance.getDatabase(mContext).shopDao();
        this.mPurchaseDetails = mPurchaseDetails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PurchaseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View purchaseView = inflater.inflate(R.layout.item_purchase_detail, parent, false);
        PurchaseDetailAdapter.PurchaseDetailViewHolder viewHolder = new PurchaseDetailViewHolder(purchaseView, onItemClickListener, mPurchaseDetails);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseDetailViewHolder holder, int position) {
        PurchaseDetail purchaseDetail = mPurchaseDetails.get(position);
//        holder.imageView.setImageResource(R.drawable.ic_action_cart);
        // get product:
        Product product = dao.getProductById(purchaseDetail.productId);
        holder.productName.setText(product.productName);
        holder.purchaseQuantity.setText(String.valueOf(purchaseDetail.quantity));
        holder.purchasePrice.setText(String.valueOf(purchaseDetail.quantity * product.price));
    }

    @Override
    public int getItemCount() {
        return mPurchaseDetails.size();
    }

    class PurchaseDetailViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView productName, purchaseQuantity, purchasePrice;
        private List<PurchaseDetail> mPurchaseDetails;

        public PurchaseDetailViewHolder(@NonNull View itemView,final PurchaseAdapter.OnItemClickListener listener, List<PurchaseDetail> purchaseDetails) {
            super(itemView);
            imageView = itemView.findViewById(R.id.purchase_detail_image);
            productName = itemView.findViewById(R.id.product_name);
            purchaseQuantity = itemView.findViewById(R.id.purchase_quantity);
            purchasePrice = itemView.findViewById(R.id.purchase_price);
            this.mPurchaseDetails = purchaseDetails;
        }
    }
}
