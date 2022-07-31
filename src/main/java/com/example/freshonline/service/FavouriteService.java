package com.example.freshonline.service;


import com.example.freshonline.dao.FavoriteMapper;
import com.example.freshonline.model.Favorite;
import com.example.freshonline.model.FavoriteExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavouriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    public boolean isFavourite(Integer userID, Integer goodsID){
        FavoriteExample example = new FavoriteExample();
        example.createCriteria().andUserIdEqualTo(userID).andGoodsIdEqualTo(goodsID);
        long count = favoriteMapper.countByExample(example);
        return count != 0;
    }

    public boolean addFavourite(Favorite favorite) {
        return favoriteMapper.insert(favorite) != 0;
    }

    public boolean deleteFavourite(Favorite favorite) {
        return favoriteMapper.deleteByPrimaryKey( favorite.getUserId(), favorite.getGoodsId()) == 1;
    }
    
}
