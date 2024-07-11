package com.example.assignmentapplication.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.assignmentapplication.entity.*;

import java.util.List;

@Dao
public interface ShopDao {

    // User operations
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM Users WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM Users WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM Users")
    List<User> getAllUsers();

    // Product operations
    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Products WHERE productId = :productId")
    Product getProductById(int productId);

    @Query("SELECT * FROM Products")
    List<Product> getAllProducts();

    // Category operations
    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM Categories WHERE categoryId = :categoryId")
    Category getCategoryById(int categoryId);

    @Query("SELECT * FROM Categories")
    List<Category> getAllCategories();
}
