package com.example.assignmentapplication.mangeUserActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;
import com.example.assignmentapplication.room.ShopDatabaseSingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_USER = 1;

    RecyclerView recyclerView;

    FloatingActionButton fab;

    UserAdapter userAdapter;

    ItemUserDecoration itemUserDecoration;

    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.reycle_view_user);

        userAdapter = new UserAdapter();
        loadData();
        userAdapter.setClickItem(new UserAdapter.IClickItem() {
            @Override
            public void update(User user) {
                Intent intent = new Intent(ListUserActivity.this, UpdateUserActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent,REQUEST_CODE_ADD_USER);
            }

            @Override
            public void delete(User user) {
                showConfirmDialog(user.userId);
            }
        });
        itemUserDecoration = new ItemUserDecoration(30);
        recyclerView.addItemDecoration(itemUserDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUserActivity.this, AddUserActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_USER && resultCode == RESULT_OK) {
            loadData();
        }
    }

    private void loadData() {
        userList = ShopDatabaseSingleton.getInstance(this).shopDao().getAllUsers();
        userAdapter.setUsers(userList);
    }

    private void showConfirmDialog(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = ShopDatabaseSingleton.getInstance(ListUserActivity.this).shopDao().getUserById(userId);
                        ShopDatabaseSingleton.getInstance(ListUserActivity.this).shopDao().deleteUser(user);
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}