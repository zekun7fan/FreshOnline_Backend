package com.example.freshonline.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPicInfo {

    @Indexed(unique = true)
    private String url;

    private String uid;
    private String name;
    private byte[] data;

}
