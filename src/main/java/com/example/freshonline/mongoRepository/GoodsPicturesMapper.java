package com.example.freshonline.mongoRepository;


import com.example.freshonline.dto.GoodsPicInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GoodsPicturesMapper extends MongoRepository<GoodsPicInfo, String> {


    GoodsPicInfo findGoodsPicInfoByUrl(String url);

    GoodsPicInfo deleteGoodsPicInfoByUrl(String url);
}
