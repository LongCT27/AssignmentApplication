package com.example.assignmentapplication.utils;

import com.example.assignmentapplication.entity.Cart;
import com.example.assignmentapplication.entity.Product;
import com.example.assignmentapplication.room.ShopDao;

import java.util.List;

public class CartLogicHandlerUtils {
    /**
     * Handle adding items into cart.
     * @param dao DAO
     * @param userId userId of the cart
     * @param productId productId
     * @param amount The amount adding. Must be larger than 0.
     * @return true if the number of items in cart has changed. false otherwise.
     */
    public static boolean addItemToCart(ShopDao dao, int userId, int productId, int amount){
        if (amount <= 0){
            return false;
        }
        //Get existing in cart
        Cart cart = dao.getUserCartOfProduct(userId, productId);
        int inCart = 0;
        if (cart != null){
            inCart = cart.quantity;
        }
        //Get product amount.
        Product product = dao.getProductById(productId);
        //Check
        if (inCart + amount > product.amount){
            return false;
        }
        //Add
        if (cart == null){
            cart = new Cart(userId,productId,amount);
            dao.insertCart(cart);
        } else {
            cart.quantity = inCart + amount;
            dao.updateCart(cart);
        }
        return true;
    }

    public static boolean checkout(ShopDao dao, int userId){
        List<Cart> cartItems = dao.getUserCart(userId);
        //Check against
        for (Cart cart : cartItems){
            Product product = dao.getProductById(cart.productId);
            if (product.amount < cart.quantity){
                return false;
            }
        }
        //Do checkout
        for (Cart cart : cartItems){
            Product product = dao.getProductById(cart.productId);
            product.amount -= cart.quantity;
            dao.updateProduct(product);
        }
        //Clear cart
        dao.clearCart(userId);
        return true;
    }

    public static double getTotalPrice(ShopDao dao, int userId) {
        List<Cart> cartItems = dao.getUserCart(userId);
        double total = 0;
        for (Cart cart : cartItems){
            Product product = dao.getProductById(cart.productId);
            total += product.price * cart.quantity;
        }
        return total;
    }
}
