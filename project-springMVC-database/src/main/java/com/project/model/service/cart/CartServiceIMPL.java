package com.project.model.service.cart;

import com.project.model.dao.cart.ICartDAO;
import com.project.model.entity.Cart;
import com.project.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartServiceIMPL implements ICartService {
    @Autowired
    private ICartDAO cartDAO;
    @Autowired
    private HttpSession session;

    @Override
    public List<Cart> findCartByUserId(int userId) {
        List<Cart> cartList = cartDAO.findCartByUserId(userId);
        session.setAttribute("cartList", cartList);
        float total = getTotal(cartList);
        session.setAttribute("total", total);
        return cartList;
    }

    @Override
    public boolean addToCart(Product product, int user_id, int quantity) {
        return cartDAO.addToCart(product, user_id, quantity);
    }

    @Override
    public void removeFromCart(int cart_id) {
        cartDAO.removeFromCart(cart_id);
    }

    @Override
    public void removeCartByUserId(int user_id) {
        cartDAO.removeCartByUserId(user_id);
    }

    @Override
    public void updateCartById(int cart_id, int qty) {
        cartDAO.updateCartById(cart_id, qty);
    }

    @Override
    public boolean updateCart(Product product, int user_id, int quantity) {
        return cartDAO.updateCart(product, user_id, quantity);
    }

    @Override
    public boolean checkProductInCart(int user_id, int product_id) {
        List<Cart> cartList = cartDAO.findCartByUserId(user_id);
        for (Cart cart : cartList) {
            if (cart.getProduct().getProductId() == product_id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Float getTotal(List<Cart> cartList) {
        float total = 0;
        for (Cart cart : cartList) {
            total += cart.getProduct().getPrice() * cart.getQuantity();
        }

        return total;
    }
}
