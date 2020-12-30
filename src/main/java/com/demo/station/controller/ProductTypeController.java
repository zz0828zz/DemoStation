package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.pojo.ProductType;
import com.demo.station.service.ProductTypeService;
import com.demo.station.utils.Result;
import com.demo.station.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/productType")
@Api(tags = "产品类型管理")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(value = "/getProductTypeAllList")
    @ApiOperation("获取全部产品类型")
    public Result<List<ProductType>> getProductTypeAllList() {
        List<ProductType> list = productTypeService.list();
        return Result.data(list);
    }

    @PostMapping(value = "/addProductType")
    @ApiOperation("添加产品类型")
    @ApiImplicitParam(name = "name", value = "产品名称", required = true)
    public Result addProductType(String name) {
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        int count = productTypeService.count(queryWrapper);
        if (count > 0) {
            return Result.fail("产品名称已存在");
        } else {
            ProductType productType = new ProductType();
            productType.setName(name);
            productTypeService.save(productType);
            return Result.success("保存成功");
        }
    }

    @PostMapping(value = "/updateProductType")
    @ApiOperation("修改产品类型")
    public Result updateProductType(@RequestBody ProductType productType) {
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", productType.getName());
        int count = productTypeService.count(queryWrapper);
        if (count > 0) {
            return Result.fail("产品名称已存在");
        } else {
            productTypeService.updateById(productType);
            return Result.success("修改成功");
        }
    }

    @PostMapping(value = "/deleteProductType")
    @ApiOperation("删除产品类型")
    @ApiImplicitParam(name = "id", value = "产品id", required = true)
    public Result deleteProductType(Long id) {
        productTypeService.removeById(id);
        return Result.success("删除成功");

    }

}
