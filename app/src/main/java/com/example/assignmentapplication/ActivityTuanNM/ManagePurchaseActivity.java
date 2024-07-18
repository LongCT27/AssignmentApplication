package com.example.assignmentapplication.ActivityTuanNM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmentapplication.AdapterTuanNM.PurchaseAdapterAdmin;
import com.example.assignmentapplication.AdapterTuanNM.PurchaseDetailAdapterAdmin;
import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.Purchase;
import com.example.assignmentapplication.entity.PurchaseDetail;
import com.example.assignmentapplication.room.ShopDao;
import com.example.assignmentapplication.room.ShopDatabase;
import com.example.assignmentapplication.room.ShopDatabaseInstance;

import java.util.ArrayList;
import java.util.List;

public class ManagePurchaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private PurchaseAdapterAdmin adapter;

    private PurchaseDetailAdapterAdmin adapter2;

    static List<PurchaseDetail> listPurchaseDetailBaseOnPurchaseID = new ArrayList<>();
    static List<Purchase> list = new ArrayList<>();
    ShopDatabase db;
    ShopDao shopDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_purchase_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView totalbalance = findViewById(R.id.TotalBalance);

        ImageView imageView232 = (ImageView) findViewById(R.id.imgBack);
        imageView232.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        initRoomDatabase();
        totalbalance.setText(Double.toString(shopDao.getTotalBalance()));
        //  addProduct();
        list = shopDao.getAllPurchases();
        recyclerView = findViewById(R.id.rcv3);
        adapter = new PurchaseAdapterAdmin(list, new PurchaseAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(Purchase cate) {

                showCart(cate.purchaseId,shopDao.getUserById(cate.userId).username,Double.toString(cate.totalPrice));
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 1);


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initRoomDatabase() {
        db = ShopDatabaseInstance.getDatabase(this);
        shopDao = db.shopDao();
    }

    private void showCart(int id, String username, String totalPrice) {
        // Inflate the custom layout/view
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_purchasedetail, null);

        // Create the AlertDialog with custom style
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();

        // Initialize recyclerView2 from popupView
        TextView t1 = popupView.findViewById(R.id.UserName);
        TextView t2 = popupView.findViewById(R.id.TotalPrice);
        t1.setText(username);
        t2.setText(totalPrice);

        recyclerView2 = popupView.findViewById(R.id.rcv6);
        List<PurchaseDetail> listt = shopDao.getPurchaseDetailsByPurchaseId(id);
        List<Product> listp = shopDao.getAllProducts();

        // Create an ArrayAdapter
        adapter2 = new PurchaseDetailAdapterAdmin(listt, listp);

        GridLayoutManager manager2 = new GridLayoutManager(this, 1);
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.setAdapter(adapter2);
        // Show the dialog
        dialog.show();
    }


}