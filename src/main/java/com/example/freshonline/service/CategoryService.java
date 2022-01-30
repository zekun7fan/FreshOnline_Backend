package com.example.freshonline.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.model.Category;
import com.example.freshonline.model.CategoryExample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author Josh Sun
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *
     * @return
     * @author zekun fan
     */
    public List<CategoryTreeNode> getCategoryTree(){
        List<Category> categories = categoryMapper.selectByExample(new CategoryExample());
        HashMap<Integer, Map<Integer, List<CategoryTreeNode>>> map = new HashMap<>();
        for (Category category : categories){
            Integer level = category.getLevel();
            map.putIfAbsent(level, new HashMap<>());
            Map<Integer, List<CategoryTreeNode>> submap = map.get(level);
            Integer parentId = category.getParentId();
            submap.putIfAbsent(parentId, new ArrayList<>());
            List<CategoryTreeNode> list = submap.get(parentId);
            list.add(new CategoryTreeNode(category.getId(), category.getName(), new ArrayList<>()));
        }
        CategoryTreeNode dummyRoot = new CategoryTreeNode(0, "", new ArrayList<>());
        LinkedList<CategoryTreeNode> q = new LinkedList<>();
        q.offerLast(dummyRoot);
        int level = 1;
        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                CategoryTreeNode node = q.pollFirst();
                Integer id = node.getValue();
                Map<Integer, List<CategoryTreeNode>> submap = map.get(level);
                if (submap== null){
                    continue;
                }
                List<CategoryTreeNode> list = submap.get(id);
                if (list == null){
                    continue;
                }
                for (CategoryTreeNode nd : list){
                    node.getChildren().add(nd);
                    q.offerLast(nd);
                }
//                if (node.getChildren().isEmpty()){
//                    node.setChildren(null);
//                }
            }
            level++;
        }
        return dummyRoot.getChildren();
    }
}


