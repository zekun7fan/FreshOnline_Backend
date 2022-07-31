package com.example.freshonline.service;


import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.GoodsPicInfo;
import com.example.freshonline.model.StockedGoods;
import com.example.freshonline.mongoRepository.GoodsPicturesMapper;
import com.example.freshonline.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
public class GoodsPictureService {

    @Autowired
    private GoodsPicturesMapper mapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

    public GoodsPicInfo save(Integer goodsId, MultipartFile multipartFile) {
        byte[] data;
        try {
            data = multipartFile.getBytes();
        } catch (IOException e) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        sb.append(goodsId).append("-").append(uuid);
        String url = sb.toString();
        GoodsPicInfo goodsPicInfo = new GoodsPicInfo(url, uuid, uuid, data);
        mapper.save(goodsPicInfo);
        StockedGoods stockedGoods = stockedGoodsMapper.selectByPrimaryKey(goodsId);
        String pic = StringUtils.addPicUrl(stockedGoods.getPic(), url);
        stockedGoods.setPic(pic);
        int n = stockedGoodsMapper.updateByPrimaryKeySelective(stockedGoods);
        goodsPicInfo.setData(null);
        return n == 1 ? goodsPicInfo : null;
    }

    public GoodsPicInfo delete(Integer goodsId, String url){
        GoodsPicInfo info = mapper.findGoodsPicInfoByUrl(url);
        if (info != null) {
            mapper.deleteGoodsPicInfoByUrl(url);
        }
        StockedGoods stockedGoods = stockedGoodsMapper.selectByPrimaryKey(goodsId);
        String pic = StringUtils.delPicUrl(stockedGoods.getPic(), url);
        stockedGoods.setPic(pic);
        int n = stockedGoodsMapper.updateByPrimaryKey(stockedGoods);
        GoodsPicInfo goodsPicInfo = new GoodsPicInfo(url, null, null, null);
        return n ==1 ? goodsPicInfo : null;
    }


    public void delete(Integer goodsId){
        StockedGoods stockedGoods = stockedGoodsMapper.selectByPrimaryKey(goodsId);
        if (stockedGoods == null || stockedGoods.getPic() == null){
            return;
        }
        String[] urlList = stockedGoods.getPic().split(",");
        for (String url : urlList){
            mapper.deleteGoodsPicInfoByUrl(url);
        }
    }

    public byte[] find(String url){
        return mapper.findGoodsPicInfoByUrl(url).getData();
    }
}
