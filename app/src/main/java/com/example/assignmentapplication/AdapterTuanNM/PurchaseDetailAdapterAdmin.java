package com.example.assignmentapplication.AdapterTuanNM;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.PurchaseDetail;

import java.util.List;

public class PurchaseDetailAdapterAdmin extends RecyclerView.Adapter<PurchaseDetailAdapterAdmin.PurchaseDetailViewHolder> {
    private List<PurchaseDetail> mlist;
    private List<Product> plist;


    public PurchaseDetailAdapterAdmin(List<PurchaseDetail> mlist, List<Product> plist) {
        this.plist = plist;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public PurchaseDetailAdapterAdmin.PurchaseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchasedetail_admin, parent, false);
        return new PurchaseDetailAdapterAdmin.PurchaseDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseDetailAdapterAdmin.PurchaseDetailViewHolder holder, int position) {
        PurchaseDetail PurchaseDetaito = mlist.get(position);
        if (PurchaseDetaito == null) {
            return;
        }
        String productNameT="";
        for (Product p : plist) {
            if (p.productId== PurchaseDetaito.productId){
                productNameT = p.productName;
                break;
            }
        }

        //  String productName = Integer.toString(PurchaseDetaito.productId);

        String productQuantity = Long.toString(PurchaseDetaito.quantity);

        holder.ProductName.setText(productNameT);
        holder.Quantity.setText(productQuantity);


    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class PurchaseDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView ProductName, Quantity;


        public PurchaseDetailViewHolder(@NonNull View itemView) {

            super(itemView);

            ProductName = itemView.findViewById(R.id.purchase_detail_producName);
            Quantity = itemView.findViewById(R.id.purchase_detail_quantity);


        }

    }


}
