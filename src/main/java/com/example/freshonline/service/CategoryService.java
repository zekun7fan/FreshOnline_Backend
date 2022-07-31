package com.example.freshonline.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.freshonline.dao.CategoryMapper;
import com.example.freshonline.dao.SaledGoodsMapper;
import com.example.freshonline.dao.StockedGoodsMapper;
import com.example.freshonline.dto.CategoryTreeNode;
import com.example.freshonline.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SaledGoodsMapper saledGoodsMapper;

    @Autowired
    private StockedGoodsMapper stockedGoodsMapper;

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
            list.add(new CategoryTreeNode(category.getId(), category.getName(), category.getLevel(), new ArrayList<>()));
        }
        CategoryTreeNode dummyRoot = new CategoryTreeNode(0, "", 0, new ArrayList<>());
        LinkedList<CategoryTreeNode> q = new LinkedList<>();
        q.offerLast(dummyRoot);
        int level = 1;
        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                CategoryTreeNode node = q.pollFirst();
                Integer id = node.getId();
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


    public boolean addCategory(Category category){
        return categoryMapper.insert(category)==1;
    }


    public List<CategoryTreeNode> delSubCategoryTree(CategoryTreeNode node){
        ArrayList<CategoryTreeNode> failedDeleteNodeList = new ArrayList<>();
        delSingleCategoryNode(node, failedDeleteNodeList);
        return failedDeleteNodeList;
    }

    private boolean delSingleCategoryNode(CategoryTreeNode node,  List<CategoryTreeNode> failedDeleteNodeList){
        boolean res = true;
        if (node.getChildren() != null && !node.getChildren().isEmpty()){
            for (CategoryTreeNode childNode : node.getChildren()){
                if (!delSingleCategoryNode(childNode, failedDeleteNodeList)){
                    res = false;
                }
            }
        }
        if (!res){
            return false;
        }
        if (node.getChildren() != null && node.getChildren().size() > 0){
            int i = categoryMapper.deleteByPrimaryKey(node.getId());
            assert i==1;
            return true;
        }
        Integer id = node.getId();
        // if current node has related saled goods, fail to del, add this node into list
        StockedGoodsExample example1 = new StockedGoodsExample();
        StockedGoodsExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andCategoryIdEqualTo(id);
        List<StockedGoods> stockedGoods = stockedGoodsMapper.selectByExample(example1);
        List<Integer> goodsIdList = stockedGoods.stream().map(StockedGoods::getId).collect(Collectors.toList());

        if (goodsIdList.size() > 0){
            SaledGoodsExample saledGoodsExample = new SaledGoodsExample();
            SaledGoodsExample.Criteria criteria = saledGoodsExample.createCriteria();
            criteria.andGoodsIdIn(goodsIdList);
            long num1 = saledGoodsMapper.countByExample(saledGoodsExample);
            if (num1 > 0){
                failedDeleteNodeList.add(node);
                return false;
            }
        }
        // if current node has related stocked goods, delete these goods
        if (goodsIdList.size() > 0){
            StockedGoodsExample example2 = new StockedGoodsExample();
            StockedGoodsExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andIdIn(goodsIdList);
            int num2 = stockedGoodsMapper.deleteByExample(example2);
            assert num2 == goodsIdList.size();
        }
        int num3 = categoryMapper.deleteByPrimaryKey(id);
        assert num3 == 1;
        return true;
    }
}


