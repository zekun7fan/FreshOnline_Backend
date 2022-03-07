package com.example.freshonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTreeNode {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Min(1)
    @Max(3)
    private Integer level;

    @Nullable
    private List<CategoryTreeNode> children;
}

