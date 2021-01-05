package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.config.jwt.UserUtils;
import com.demo.station.model.dto.ProductDto;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.model.vo.ProductPageVO;
import com.demo.station.model.vo.ProductPriceVO;
import com.demo.station.model.vo.SaveOrUpdateProductVO;
import com.demo.station.pojo.*;
import com.demo.station.service.*;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.PageResult;
import com.demo.station.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/product")
@Api(tags = "产品管理")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductPriceService productPriceService;


//    //根据用户账号获取对应的角色名
//    public String getRoleName(SysUser sysUser) {
//
//        QueryWrapper<SysUserRole> sysUserRoleQuery = new QueryWrapper<>();
//        sysUserRoleQuery.eq("user_id", sysUser.getId());
//        SysUserRole sysUserRole = sysUserRoleService.getOne(sysUserRoleQuery);
//        SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
//        String roleName = sysRole.getRoleName();
//        return roleName;
//    }

    @GetMapping("/page")
    @ApiOperation("产品分页")
    public PageResult<ProductDto> page(ProductPageVO productPageVO) {
        String name = productPageVO.getName();
        Long productTypeId = productPageVO.getProductTypeId();

        Page<Product> page = new Page<>(productPageVO.getCurrent(), productPageVO.getSize());  //参数一是当前页，参数二是每页个数
        page.setDesc("create_time");  //根据创建时间排序

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name), "name", name)
                .eq(productTypeId != null, "product_type_id", productTypeId);
        IPage<Product> productIPage = productService.page(page, queryWrapper);


        //判断当前登录角色  用来获取对应的价格
        String sysUserName = UserUtils.getUser(); //获取当前用户账号
        QueryWrapper<SysUser> querySysUser = new QueryWrapper<>();
        querySysUser.eq("user_name", sysUserName);
        SysUser sysUser = userService.getOne(querySysUser);


        List<Product> records = productIPage.getRecords();

        //封装page类
        PageResult pageResult = new PageResult();
        pageResult.setCount(productIPage.getTotal());

        List<ProductDto> list = new ArrayList<>();
        for (Product record : records) {
            ProductDto productDto = CopyUtils.copyPojo(record, ProductDto.class);
            //根据产品 id 和  当前登录用户和上级用户获取对应的低价和售价
            Long id = productDto.getId(); //产品id
            String agentName = sysUser.getAgentName();  //上级用户的账号

            QueryWrapper<ProductPrice> priceQueryWrapper = new QueryWrapper<>();
            priceQueryWrapper.eq("product_id", id)
                    .eq("user_name", sysUserName);
            ProductPrice productPrice = productPriceService.getOne(priceQueryWrapper);
            BigDecimal sellingPrice = null;
            if (productPrice != null) {
                sellingPrice = productPrice.getPrice(); //售价
                productDto.setSellingPrice(sellingPrice);

            }
            BigDecimal floorPrice = null;
            if (null == agentName || agentName.equals("")) { //如果上级用户账号为空  则说明此人是管理员  管理员的低价和售价一致
                floorPrice = sellingPrice;
            } else {
                //上级账号不为空  则底价为上级的售价
                QueryWrapper<ProductPrice> priceQueryWrapper1 = new QueryWrapper<>();
                priceQueryWrapper1.eq("product_id", id)
                        .eq("user_name", agentName);
                ProductPrice productPrice1 = productPriceService.getOne(priceQueryWrapper1);
                if (productPrice1 != null) {
                    floorPrice = productPrice1.getPrice();
                }
            }
            //如果售价为空   底价有值   则售价为底价
            if (sellingPrice == null && floorPrice!=null){
                productDto.setSellingPrice(floorPrice);
            }
            productDto.setFloorPrice(floorPrice);

            //将产品类型返回给前台
            ProductType productType = productTypeService.getById(productDto.getProductTypeId());
            if (productType != null) {
                productDto.setProductTypeName(productType.getName());
            }

            list.add(productDto);

        }
        pageResult.setData(list);

        return pageResult;

    }

    @PostMapping("/saveOrUpdateProduct")
    @ApiOperation("管理员新增或修改产品")
    public Result saveOrUpdateProduct(@RequestBody SaveOrUpdateProductVO productVO) {
        if (productService.saveOrUpdateProduct(productVO)) {
            return Result.success("操作成功");
        }
        return Result.fail("操作失败");
    }

    @PostMapping("/saveOrUpdateProductPrice")
    @ApiOperation("代理新增或修改产品价格")
    public Result saveOrUpdateProductPrice(@RequestBody ProductPriceVO productPriceVO) {
        try {
            String sysUserName = UserUtils.getUser(); //获取当前用户账号
            Long productId = productPriceVO.getProductId(); //产品id
            //根据用户账号和产品id   判断产品价格是否存在      如果存在则更新不存在则新增
            QueryWrapper<ProductPrice> priceQueryWrapper = new QueryWrapper<>();
            priceQueryWrapper.eq("product_id", productId)
                    .eq("user_name", sysUserName);
            ProductPrice productPrice = productPriceService.getOne(priceQueryWrapper);
            if (productPrice == null) {
                productPrice = new ProductPrice();
            }
            productPrice.setUserName(sysUserName);
            productPrice.setProductId(productId);
            productPrice.setPrice(productPriceVO.getPrice());
            productPriceService.saveOrUpdate(productPrice);
            return Result.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("操作失败");
    }


    @PostMapping("/deleteProduct")
    @ApiOperation("删除产品")
    @ApiImplicitParam(name = "id", value = "产品id", required = true)
    @Transactional
    public Result deleteProduct(Long id) {
        try {
            if (productService.removeById(id)) {
                QueryWrapper<ProductPrice> productPriceQuery = new QueryWrapper<>();
                productPriceQuery.eq("product_id", id);
                productPriceService.remove(productPriceQuery);
                return Result.success("删除成功");
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据用户账号查询该账号所改动的产品价格
     */
    @GetMapping("/getProductInfoByUserName")
    @ApiOperation("根据用户账号查询该账号的产品")
    @ApiImplicitParam(name = "name", value = "用户账号", required = true)
    public Result getProductInfoByUserName(String userName) {

        //一、查询出该账号所有的产品价格信息
        QueryWrapper<ProductPrice> productPriceQuery = new QueryWrapper<>();
        productPriceQuery.eq("user_name", userName);
        List<ProductPrice> productPriceList = productPriceService.list(productPriceQuery);

        //二、根据产品价格信息中所有的产品id  再根据产品id查询产品信息
        List<ProductDto> list = new ArrayList<>();
        for (ProductPrice productPrice : productPriceList) {
            Product product = productService.getById(productPrice.getProductId());
            if (product != null) {
                ProductDto productDto = CopyUtils.copyPojo(product, ProductDto.class);
                //将产品类型返回给前台
                ProductType productType = productTypeService.getById(productDto.getProductTypeId());
                if (productType != null) {
                    productDto.setProductTypeName(productType.getName());
                }
                productDto.setSellingPrice(productPrice.getPrice());
                list.add(productDto);
            }
        }


        return Result.data(list);
    }


}
