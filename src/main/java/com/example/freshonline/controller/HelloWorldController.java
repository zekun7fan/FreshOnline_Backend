package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dto.HelloWorldDto;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.User;
import com.example.freshonline.service.HelloWorldService;
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class HelloWorldController {


    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("/hello/{hello_id}")
    public String h1(@PathVariable("hello_id") Integer id, @RequestParam("hello_name") String xxx){
        System.out.println("h1:  hello_id="+ id+ "-----hello_name="+xxx);
        return "h1:  hello_id="+ id+ "-----hello_name="+xxx;
    }

    @GetMapping("/hello/{user_id}/orders")
    public String h2(@PathVariable("user_id") String id,
                     @RequestParam("pagesize") String pagesize,
                     @RequestParam("pagenum") String pagenum,
                     @RequestParam("start") String start_date,
                     @RequestParam("end") String end){
        return "h2:   user_id="+ id+
                "-----pagesize="+pagesize+
                "----pagenum="+pagenum+
                "----start="+start_date.toString()+
                "----end="+end.toString();
    }

    /**
     *
     * @param req
     * @author
     * @return
     */
    @PostMapping("/hello/user")
    public JSONObject h3(@RequestBody JSONObject req){



        User user = req.toJavaObject(User.class);
        ArrayList<Integer> ids = new ArrayList<>(); // service
//        return "h3:   "+ user.toString();
        if (ids != null && !ids.isEmpty()){
            JSONObject res = new JSONObject();
            res.put("code",0);
            res.put("msg","add user successful");
            ids.add(user.getId());
            res.put("data",ids);
            System.out.println(res);
            return res;
        }else{

        }
        JSONObject res = null;

        return res;

//        JSONObject res = util.createResp(data, rule, succuess_msg, fail_msg)
    }

    @PostMapping("/helloworld/{id}")
    public JSONObject m1(
            @PathVariable("id") Integer id
    ) throws Exception {
        String res = helloWorldService.m1();
        return RespBuilder.create(res, VerifyRule.NOT_NULL);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.IM_USED)
    private void m(Throwable e, HttpServletRequest request, HttpServletResponse response){
        System.out.println("mm");
        System.out.println("mm");
    }



}
