package com.example.freshonline.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dto.HelloWorldDto;
import com.example.freshonline.enums.respVerifyRule.VerifyRule;
import com.example.freshonline.model.User;
import com.example.freshonline.model.joined_tables.GoodsCategory;
import com.example.freshonline.service.HelloWorldService;
<<<<<<< HEAD
import com.example.freshonline.service.StockedGoodsService;
=======
import com.example.freshonline.service.MongoDbDemo;
>>>>>>> f744e26f620dfa2f6e5f870c872f4da3368c0376
import com.example.freshonline.utils.RespBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
<<<<<<< HEAD
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
=======
import java.util.List;
>>>>>>> f744e26f620dfa2f6e5f870c872f4da3368c0376


@RestController
public class HelloWorldController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockedGoodsService stockedGoodsService;


    @GetMapping("/redis/{id}")
    public String h1(@PathVariable("id") String id) {
        String a = (String)redisTemplate.opsForValue().get(id);
        return ("redis result" + a);
    }

    @PostMapping("/redis/{id}/{value}")
    public String h2(@PathVariable("id") String id,@PathVariable("value") String value) {
        redisTemplate.opsForValue().set(id, value);
        return ("set success");
    }

    @GetMapping("/redis/goods/{id}")
    public JSONObject getGoodsDetails(@PathVariable("id") String id) {
        JSONObject res = new JSONObject();
        Integer goods_id = Integer.parseInt(id);
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(200);
            for(int i =0;i<500;i++){
                executorService.submit(new Runnable() {
                    public void run(){
                        stockedGoodsService.goodsDetailsRedis(goods_id);
                    }
                });
            }
            return res;
        }
        catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            res.put("code", 1);
            res.put("msg", sw.toString());
            return res;
        }
    }







    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    private MongoDbDemo mongoDbDemo;

    @GetMapping("/hello/{hello_id}")
    public String h1(@PathVariable("hello_id") Integer id, @RequestParam("hello_name") String xxx) {
        System.out.println("h1:  hello_id=" + id + "-----hello_name=" + xxx);
        return "h1:  hello_id=" + id + "-----hello_name=" + xxx;
    }

    @GetMapping("/hello/{user_id}/orders")
    public String h2(@PathVariable("user_id") String id,
            @RequestParam("pagesize") String pagesize,
            @RequestParam("pagenum") String pagenum,
            @RequestParam("start") String start_date,
            @RequestParam("end") String end) {
        return "h2:   user_id=" + id +
                "-----pagesize=" + pagesize +
                "----pagenum=" + pagenum +
                "----start=" + start_date.toString() +
                "----end=" + end.toString();
    }

    /**
     *
     * @param req
     * @author
     * @return
     */
    @PostMapping("/hello/user")
    public JSONObject h3(@RequestBody JSONObject req) {

        User user = req.toJavaObject(User.class);
        ArrayList<Integer> ids = new ArrayList<>(); // service
        // return "h3: "+ user.toString();
        if (ids != null && !ids.isEmpty()) {
            JSONObject res = new JSONObject();
            res.put("code", 0);
            res.put("msg", "add user successful");
            ids.add(user.getId());
            res.put("data", ids);
            System.out.println(res);
            return res;
        } else {

        }
        JSONObject res = null;

        return res;

        // JSONObject res = util.createResp(data, rule, succuess_msg, fail_msg)
    }

    @PostMapping("/helloworld/{id}")
    public JSONObject m1(
            @PathVariable("id") Integer id) throws Exception {
        String res = helloWorldService.m1();
        return RespBuilder.create(res, VerifyRule.NOT_NULL);
    }

    @GetMapping("/helloworld/copyuser")
    public void MongoTest1( @RequestParam("id") Integer id){
        mongoDbDemo.copyUserToMongo(id);
    }

    @GetMapping("/helloworld/queryuser")
    public User MongoTest2( @RequestParam("id") Integer id){
        return mongoDbDemo.getUserById(id);
    }

    @GetMapping("/helloworld/queryusername")
    public User MongoTest4( @RequestParam("name") String name){
        return mongoDbDemo.getUserByName(name);
    }

    @GetMapping("/helloworld/queryusertype")
    public List<User> MongoTest5(@RequestParam("type") int type){
        return mongoDbDemo.getUserByType(type);
    }

    @GetMapping("/helloworld/deleteuser")
    public void MongoTest3( @RequestParam("id") Integer id){
        mongoDbDemo.deleteUserMongo(id);
    }

//     @ExceptionHandler(Exception.class)
//     @ResponseStatus(HttpStatus.IM_USED)
//     private void m(Throwable e, HttpServletRequest request, HttpServletResponse
//     response){
//     System.out.println("mm");
//     System.out.println("mm");
//     }

//    @PostMapping("/helloworld/raiseerror")
//    public JSONObject err() throws Exception {
//         throw new CustomExceptions("helloworld error1111", 120);
//    }

}
