package com.example.freshonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTreeNode {
    private Integer id;
    private String name;
    private Integer level;
    private List<CategoryTreeNode> children;
}

