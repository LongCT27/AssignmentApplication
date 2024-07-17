package com.example.assignmentapplication.manageProductActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignmentapplication.entity.Category;

import java.util.List;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categories;

    public SpinnerCategoryAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, android.R.layout.simple_spinner_item, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(categories.get(position).categoryName);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(categories.get(position).categoryName);
        return convertView;
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    public int getCategoryId(int position) {
        return categories.get(position).categoryId;
    }

    public int getPositionById(int categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).categoryId == categoryId) {
                return i;
            }
        }
        return -1;
    }

}
