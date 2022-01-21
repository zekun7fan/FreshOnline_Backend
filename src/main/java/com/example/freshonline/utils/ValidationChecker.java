package com.example.freshonline.utils;

/**
 * @author Josh Sun
 */
public class ValidationChecker {

    /**
     * @author Josh Sun
     * @param input String
     * @param defaultVal Integer
     * @return Integer
     */
    public Integer str2int(String input, Integer defaultVal) {
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
