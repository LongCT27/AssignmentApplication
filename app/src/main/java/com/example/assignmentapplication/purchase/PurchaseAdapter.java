package com.example.assignmentapplication.purchase;

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
import com.example.assignmentapplication.entity.Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> mPurchases;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void updateData(List<Purchase> temp) {
        this.mPurchases = temp;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View purchaseView = inflater.inflate(R.layout.item_purchase, parent, false);
        PurchaseViewHolder viewHolder = new PurchaseViewHolder(purchaseView, onItemClickListener, mPurchases);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase purchase = mPurchases.get(position);
        // Assuming purchase.purchaseDate is a long value representing epoch milliseconds
        holder.imageView.setImageResource(R.drawable.ic_action_cart);
        Date date = new Date(purchase.purchaseDate);
        holder.purchaseDate.setText(date.toString());
        holder.purchaseTotal.setText(String.valueOf(purchase.totalPrice));
    }

    @Override
    public int getItemCount() {
        return mPurchases.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDetailClick(Purchase purchase);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public PurchaseAdapter(List<Purchase> mProducts, Context context) {
        this.mPurchases = mProducts;
//        this.itemClickListener = itemClickListener;
        this.mContext = context;
    }


    class PurchaseViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView purchaseDate;
        public  TextView purchaseTotal;
        public Button detail_button;
        private List<Purchase> mPurchases;

        public PurchaseViewHolder(@NonNull View itemView, final PurchaseAdapter.OnItemClickListener listener, List<Purchase> purchases) {
            super(itemView);
            imageView =itemView.findViewById(R.id.purchase_image);
            purchaseDate = itemView.findViewById(R.id.purchase_date);
            purchaseTotal = itemView.findViewById(R.id.purchase_price);
            detail_button = itemView.findViewById(R.id.button_detail);
            this.mPurchases = purchases;
            detail_button.setOnClickListener(v -> {
                int position = getAdapterPosition();
//            purchases.get(position);
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDetailClick(mPurchases.get(position));
                }
            });
        }
    }

}

