package com.example.assignmentapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {
    private List<Purchase> mlist;
    private PurchaseAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Purchase cate);


    }


    public PurchaseAdapter(List<Purchase> mlist, PurchaseAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_admin, parent, false);
        return new PurchaseAdapter.PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase Purchaseto = mlist.get(position);
        if (Purchaseto == null) {
            return;
        }
        Date currentDate = new Date(Purchaseto.purchaseDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the Date object to the desired format
        String formattedDate = dateFormat.format(currentDate);

        String PurchaseID = Integer.toString(Purchaseto.purchaseId);
        String PurchaseDate = Long.toString(Purchaseto.purchaseDate) ;
        String PurchaseTotalPrice = Double.toString(Purchaseto.totalPrice) ;
        holder.purchaseID.setText(PurchaseID);
        holder.purchaseDate.setText(formattedDate);
        holder.TotalPrice.setText(PurchaseTotalPrice);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(Purchaseto));


    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {

        private TextView purchaseID,purchaseDate,TotalPrice;


        public PurchaseViewHolder(@NonNull View itemView) {

            super(itemView);

            purchaseID = itemView.findViewById(R.id.purchase_id_manage);
            purchaseDate = itemView.findViewById(R.id.purchaseDate_manage);
            TotalPrice = itemView.findViewById(R.id.purchaseTotalPrice_manage);


        }

    }


}
