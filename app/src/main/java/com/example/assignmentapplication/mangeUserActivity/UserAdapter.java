package com.example.assignmentapplication.mangeUserActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.User;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    private IClickItem clickItem;

    public interface IClickItem {
        void update(User user);

        void delete(User user);
    }
    public UserAdapter(){
    }

    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    public void setClickItem(IClickItem clickItem){
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_user, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.idTextView.setText("ID: "+user.userId);
        holder.usernameTextView.setText("Username: "+user.username);
        holder.roleTextView.setText("Role: " + (user.role == 0? "Manager" : "Customer"));
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.update(user);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.delete(user);
            }

        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView usernameTextView;
        TextView roleTextView;

        ImageButton editButton;
        ImageButton deleteButton;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.text_view_id);
            usernameTextView = itemView.findViewById(R.id.text_view_username);
            roleTextView = itemView.findViewById(R.id.text_view_role);
            editButton = itemView.findViewById(R.id.edit_btn);
            deleteButton = itemView.findViewById(R.id.delete_btn);

        }

    }

}
