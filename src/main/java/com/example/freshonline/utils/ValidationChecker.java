package com.example.freshonline.utils;

public class ValidationChecker {
    /*
    * @author: Josh Sun
    * @description: check validation of request
    * */
    public Integer str2int(String input, Integer defaultVal) {
        /*
        * @author: Josh Sun
        * @description: validation checking for input int
        * */
        Integer output;
        try{
            output = Integer.parseInt(input);
        }
        catch (Exception e){
            output = defaultVal;
        }
        return output;
    }
}
