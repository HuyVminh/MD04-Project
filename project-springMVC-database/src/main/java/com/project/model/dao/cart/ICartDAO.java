package com.project.model.dao.cart;

import com.project.model.entity.Cart;
import com.project.model.entity.Product;

import java.util.List;

public interface ICartDAO {
    List<Cart> findCartByUserId(int userId);
    boolean addToCart(Product product,int user_id,int quantity);
    void removeFromCart(int cart_id);
    void removeCartByUserId(int user_id);
    void updateCartById(int cart_id,int qty);
    boolean updateCart(Product product,int user_id,int quantity);
}
