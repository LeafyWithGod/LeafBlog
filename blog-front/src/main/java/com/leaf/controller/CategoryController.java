package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类",description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分类接口")
    @SystemLog(businessName = "获取分类接口")
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
