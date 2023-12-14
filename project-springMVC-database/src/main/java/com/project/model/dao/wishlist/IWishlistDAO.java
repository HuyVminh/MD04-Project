package com.project.model.dao.wishlist;

import com.project.model.entity.Wishlist;

import java.util.List;

public interface IWishlistDAO {
    List<Wishlist> findAllByUserId(Integer id);
    boolean addToWishlist(int userId,int productId);
    boolean removeFromWishlist(int userId,int productId);
}
