package com.example.assignmentapplication.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmentapplication.entity.Cart;
import com.example.assignmentapplication.entity.Category;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.entity.Purchase;
import com.example.assignmentapplication.entity.PurchaseDetail;
import com.example.assignmentapplication.entity.User;

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

    // Cart operations
    @Insert
    void insertCart(Cart cart);

    @Update
    void updateCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Query("SELECT * FROM Carts WHERE cartId = :cartId")
    Cart getCartById(int cartId);

    @Query("SELECT * FROM Carts")
    List<Cart> getAllCarts();

    // Purchase operations
    @Insert
    void insertPurchase(Purchase purchase);

    @Update
    void updatePurchase(Purchase purchase);

    @Delete
    void deletePurchase(Purchase purchase);

    @Query("SELECT * FROM Purchases WHERE purchaseId = :purchaseId")
    Purchase getPurchaseById(int purchaseId);

    @Query("SELECT SUM(totalPrice) FROM Purchases")
    double getTotalBalance();

    @Query("SELECT * FROM Purchases")
    List<Purchase> getAllPurchases();

    // PurchaseDetail operations
    @Insert
    void insertPurchaseDetail(PurchaseDetail purchaseDetail);

    @Update
    void updatePurchaseDetail(PurchaseDetail purchaseDetail);

    @Delete
    void deletePurchaseDetail(PurchaseDetail purchaseDetail);

    @Query("SELECT * FROM PurchaseDetails WHERE purchaseId = :purchaseId")
    List<PurchaseDetail> getPurchaseDetailsByPurchaseId(int purchaseId);

    @Query("SELECT * FROM PurchaseDetails")
    List<PurchaseDetail> getAllPurchaseDetails();
    @Query("SELECT * FROM Products WHERE categoryId = :id")
    List<Product> getAllProductBaseOnCategoryID(int id);
}
