package com.yoboo.controller.admin;

import com.yoboo.dto.CategoryDTO;
import com.yoboo.dto.CategoryPageQueryDTO;
import com.yoboo.entity.Category;
import com.yoboo.result.PageResult;
import com.yoboo.result.Result;
import com.yoboo.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "Category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Add Category
     * @param categoryDTO
     * @return Result
     */
    @PostMapping
    @ApiOperation("Add Category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * Category pagination
     * @param categoryPageQueryDTO
     * @return Result
     */
    @GetMapping("/page")
    @ApiOperation("Category pagination")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){

        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Delete Category
     * @param id
     * @return Result
     */
    @DeleteMapping
    @ApiOperation("Delete Category")
    public Result<String> deleteById(Long id){

        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * Edit Category
     * @param categoryDTO
     * @return Result
     */
    @PutMapping
    @ApiOperation("Edit Category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * Start or stop Category
     * @param status
     * @param id
     * @return Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop Category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * Get Category by type
     * @param type
     * @return Result
     */
    @GetMapping("/list")
    @ApiOperation("Get Category by type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
