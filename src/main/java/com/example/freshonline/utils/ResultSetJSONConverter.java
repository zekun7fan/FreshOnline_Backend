package com.example.freshonline.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.ResultSet;

public class ResultSetJSONConverter {
    public static JSONArray convert(ResultSet resultSet) throws Exception {
 
        JSONArray jsonArray = new JSONArray();
     
        while (resultSet.next()) {
     
            int columns = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
     
            for (int i = 0; i < columns; i++)
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
     
            jsonArray.add(obj);
        }
        return jsonArray;
    }
}
