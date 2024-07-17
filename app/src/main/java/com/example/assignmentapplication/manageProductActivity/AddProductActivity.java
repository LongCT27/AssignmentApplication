package com.example.assignmentapplication.manageProductActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignmentapplication.R;
import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDatabaseSingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    TextView textImageReminder;
    TextInputLayout inputLayoutProductName, inputLayoutDescription, inputLayoutPrice, inputLayoutAmount;
    private TextInputEditText editTextProductName, editTextDescription, editTextPrice, editTextAmount;
    private Spinner spinnerCategory;

    SpinnerCategoryAdapter spinnerCategoryAdapter;
    private ImageView imageView;
    private Uri imageUri;

    Button chooseImageButton, addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        initSpinner();
        chooseImageButton = findViewById(R.id.button_choose_image);
        chooseImageButton.setOnClickListener(v -> {
            openImagePicker();
        });
        addButton = findViewById(R.id.button_add_product);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetErrors();
                if(isValidProduct()){
                    Product product = new Product();
                    product.productName = editTextProductName.getText().toString();
                    product.description = editTextDescription.getText().toString();
                    product.price = Double.parseDouble(editTextPrice.getText().toString());
                    product.amount = Integer.parseInt(editTextAmount.getText().toString());
                    product.categoryId = spinnerCategoryAdapter.getCategoryId(spinnerCategory.getSelectedItemPosition());
                    product.imagePath = saveImageToInternalStorage(imageUri);
                    ShopDatabaseSingleton.getInstance(getApplicationContext()).shopDao().insertProduct(product);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void initSpinner() {
        List<Category> categoryList = ShopDatabaseSingleton.getInstance(this).shopDao().getAllCategories();
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(this, categoryList);
        spinnerCategory.setAdapter(spinnerCategoryAdapter);
    }

    private void resetErrors() {
        inputLayoutProductName.setError(null);
        inputLayoutDescription.setError(null);
        inputLayoutPrice.setError(null);
        inputLayoutAmount.setError(null);
    }

    private boolean isValidProduct() {
        boolean result = true;
        if(editTextProductName.getText().toString().isEmpty()){
            inputLayoutProductName.setError("Product name is required");
            result = false;
        }
        if(editTextDescription.getText().toString().isEmpty()){
            inputLayoutDescription.setError("Description is required");
            result = false;
        }
        if(editTextPrice.getText().toString().isEmpty() || Double.parseDouble(editTextPrice.getText().toString()) <= 0){
            inputLayoutPrice.setError("Price is required");
            result = false;
        }

        if(editTextAmount.getText().toString().isEmpty() || Integer.parseInt(editTextAmount.getText().toString()) <= 0){
            inputLayoutAmount.setError("Amount is required");
            result = false;
        }
        if (imageUri == null) {
            textImageReminder.setVisibility(View.VISIBLE);  // Show the reminder text if no image is selected
            result = false;
            return result;
        }

        return result;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void initViews() {
        editTextProductName = findViewById(R.id.edit_text_product_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextAmount = findViewById(R.id.edit_text_amount);
        spinnerCategory = findViewById(R.id.spinner_category);
        imageView = findViewById(R.id.image_view);

        inputLayoutProductName = findViewById(R.id.input_layout_product_name);
        inputLayoutDescription = findViewById(R.id.input_layout_description);
        inputLayoutPrice = findViewById(R.id.input_layout_price);
        inputLayoutAmount = findViewById(R.id.input_layout_amount);

        textImageReminder = findViewById(R.id.text_image_reminder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            File directory = getDir("images", Context.MODE_PRIVATE);
            File myPath = new File(directory, "product_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return myPath.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}