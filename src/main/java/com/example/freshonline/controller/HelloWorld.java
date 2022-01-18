package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.model.User;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Source;
import java.util.Date;

@RestController
public class HelloWorld {


    @GetMapping("/hello/{hello_id}")
    public String h1(@PathVariable("hello_id") Integer id, @RequestParam("hello_name") String xxx){
        System.out.println("h1:  hello_id="+ id+ "-----hello_name="+xxx);
        return "h1:  hello_id="+ id+ "-----hello_name="+xxx;
    }

    @GetMapping("/hello/{user_id}/orders")
    public String h2(@PathVariable("user_id") String id,
                     @RequestParam("pagesize") Integer pagesize,
                     @RequestParam("pagenum") Integer pagenum,
                     @RequestParam("start") Date start_date,
                     @RequestParam("end") Date end){
        return "h2:   user_id="+ id+
                "-----pagesize="+pagesize+
                "----pagenum="+pagenum+
                "----start="+start_date.toString()+
                "----end="+end.toString();
    }

    @PostMapping("/hello/user")
    public String h3(@RequestBody JSONObject req){
        User user = req.toJavaObject(User.class);
        return "h3:   "+ user.toString();
    }


}
