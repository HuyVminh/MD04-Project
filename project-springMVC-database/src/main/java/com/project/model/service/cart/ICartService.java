package com.project.model.service.cart;

import com.project.model.entity.Cart;
import com.project.model.entity.Product;

import java.util.List;

public interface ICartService {
    List<Cart> findCartByUserId(int userId);
    boolean addToCart(Product product, int user_id, int quantity);
    void removeFromCart(int cart_id);
    void removeCartByUserId(int user_id);
    void updateCartById(int cart_id,int qty);
    boolean updateCart(Product product,int user_id,int quantity);
    boolean checkProductInCart(int user_id,int product_id);
    Float getTotal(List<Cart> cartList);
}
