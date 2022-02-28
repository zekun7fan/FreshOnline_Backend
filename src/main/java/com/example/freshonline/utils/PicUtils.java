package com.example.freshonline.utils;

import com.example.freshonline.constants.Constants;
import com.example.freshonline.dto.GoodsPicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PicUtils {


    private static final String PICS_FOLDER = System.getProperty("user.dir")+System.getProperty("file.separator")+"pics";

    private static final String GOODS_PICS_FOLDER = System.getProperty("user.dir")+System.getProperty("file.separator")+"pics"
            +System.getProperty("file.separator")+"goods_pics";


    public static GoodsPicInfo save(Integer goods_id, MultipartFile multipartFile) {

        File root_pic_folder = new File(PICS_FOLDER);
        if (!root_pic_folder.exists()) {
            if (!root_pic_folder.mkdir()) {
                return null;
            }
        }

        File goods_pic_folder = new File(GOODS_PICS_FOLDER);
        if (!goods_pic_folder.exists()) {
            if (!goods_pic_folder.mkdir()) {
                return null;
            }
        }

        String current_folder = GOODS_PICS_FOLDER + System.getProperty("file.separator") + goods_id;
        File cur_folder = new File(current_folder);
        if (!cur_folder.exists()) {
            if (!cur_folder.mkdir()) {
                return null;
            }
        }

        String contentType = multipartFile.getContentType();
        int index = contentType.lastIndexOf("/");
        String typeName = contentType.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + typeName;
        String picPath = current_folder + System.getProperty("file.separator")
                + fileName;
        String name = String.valueOf(goods_id);
        String url = "http://localhost:8080/pics/goods_pics/" + goods_id + System.getProperty("file.separator") + fileName;
        try {
            multipartFile.transferTo(Paths.get(picPath));
            return new GoodsPicInfo(uuid, name, url);
        } catch (IOException e) {
            return null;
        }
    }

    public static GoodsPicInfo delete(Integer id, String url) {
        int index = url.lastIndexOf(System.getProperty("file.separator"));
        String picPath = GOODS_PICS_FOLDER + System.getProperty("file.separator")
                + id + System.getProperty("file.separator") + url.substring(index + 1);
        try {
            Files.deleteIfExists(Paths.get(picPath));
            return new GoodsPicInfo(null, String.valueOf(id), url);
        } catch (IOException e) {
            return null;
        }

    }


    public static boolean deleteAllPicByGoodsId(Integer id) {
        String picPath = GOODS_PICS_FOLDER + System.getProperty("file.separator") + id;
        if (!Files.exists(Paths.get(picPath))){
            return true;
        }
        try {
            List<Path> paths = Files.walk(Paths.get(picPath))
                    .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            for (Path p : paths){
                Files.deleteIfExists(p);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static String change(Integer dummyId, Integer id, String pic) {
        String folder_path = GOODS_PICS_FOLDER + System.getProperty("file.separator") + dummyId;
        File old_folder = new File(folder_path);
        String new_folder_path = GOODS_PICS_FOLDER + System.getProperty("file.separator") + id;
        boolean success = old_folder.renameTo(new File(new_folder_path));
        if (!success) {
            return null;
        }
        String[] pic_list = pic.split(",");
        StringBuilder res = new StringBuilder();
        String[] url_list = pic.split(",");
        for (String url : url_list) {
            int idx = url.lastIndexOf(System.getProperty("file.separator"));
            String fileName = url.substring(idx + 1);
            String new_url = "http://localhost:8080/pics/goods_pics/" + id + System.getProperty("file.separator") + fileName;
            res.append(new_url);
            res.append(",");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();


    }

}
