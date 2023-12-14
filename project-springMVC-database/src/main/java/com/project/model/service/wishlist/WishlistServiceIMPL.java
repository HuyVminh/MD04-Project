package com.project.model.service.wishlist;

import com.project.model.dao.wishlist.IWishlistDAO;
import com.project.model.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishlistServiceIMPL implements IWishlishService{
    @Autowired
    private IWishlistDAO wishlistDAO;
    @Override
    public List<Wishlist> findAllByUserId(Integer id) {
        return wishlistDAO.findAllByUserId(id);
    }

    @Override
    public boolean addToWishlist(int userId, int productId) {
        return wishlistDAO.addToWishlist(userId, productId);
    }

    @Override
    public boolean removeFromWishlist(int userId, int productId) {
        return wishlistDAO.removeFromWishlist(userId, productId);
    }
}
