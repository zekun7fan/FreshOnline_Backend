package com.example.freshonline.utils;

public class ValidationChecker {
    /*
    * @author: Josh Sun
    * @description: check validation of request
    * */
    public Integer string2Integer(String req, Integer def) {
        /*
        * @author: Josh Sun
        * @description: validation checking for input int
        * */
        Integer output;
        try{
            output = Integer.parseInt(req);
        }
        catch (Exception e){
            output = def;
        }
        return output;
    }
}
