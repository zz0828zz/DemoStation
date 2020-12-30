package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.config.jwt.UserUtils;
import com.demo.station.model.dto.ProductDto;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.model.vo.ProductPageVO;
import com.demo.station.pojo.Product;
import com.demo.station.pojo.SysRole;
import com.demo.station.pojo.SysUser;
import com.demo.station.pojo.SysUserRole;
import com.demo.station.service.ProductService;
import com.demo.station.service.SysRoleService;
import com.demo.station.service.SysUserRoleService;
import com.demo.station.service.SysUserService;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.PageResult;
import com.demo.station.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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


    //根据用户账号获取对应的角色名
    public String getRoleName(SysUser sysUser){

        QueryWrapper<SysUserRole> sysUserRoleQuery = new QueryWrapper<>();
        sysUserRoleQuery.eq("user_id", sysUser.getId());
        SysUserRole sysUserRole = sysUserRoleService.getOne(sysUserRoleQuery);
        SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
        String roleName = sysRole.getRoleName();
        return roleName;
    }
    @PostMapping("/page")
    public PageResult<ProductDto> page(@RequestBody ProductPageVO productPageVO){
        String name = productPageVO.getName();
        Long productTypeId = productPageVO.getProductTypeId();

        Page<Product> page = new Page<>(productPageVO.getCurrent(), productPageVO.getSize());  //参数一是当前页，参数二是每页个数
        page.setDesc("create_time");  //根据创建时间排序

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),"name",name)
                .eq(productTypeId!=null,"product_type_id",productTypeId);
        IPage<Product> productIPage = productService.page(page, queryWrapper);


        //判断当前登录角色  用来获取对应的价格
        String sysUserName = UserUtils.getUser(); //获取当前用户账号
        QueryWrapper<SysUser> querySysUser = new QueryWrapper<>();
        querySysUser.eq("user_name", sysUserName);
        SysUser sysUser = userService.getOne(querySysUser);

        String roleName = getRoleName(sysUser);
        List<Product> records = productIPage.getRecords();

        //封装page类
        PageResult pageResult = new PageResult();
        pageResult.setCount(productIPage.getTotal());

        List<ProductDto> list = new ArrayList<>();
        for (Product record : records) {
            ProductDto productDto = CopyUtils.copyPojo(record, ProductDto.class);
            //不同的角色返回不同的价格
            if (roleName.equals("admin")){
                productDto.setFloorPrice(record.getAdminPrice());   //底价 == 管理员设置的价格
                productDto.setSellingPrice(record.getAdminPrice());   //售价 == 管理员设置的价格
            }else if (roleName.equals("stair")){
                productDto.setFloorPrice(record.getAdminPrice());   //底价 == 管理员设置的价格
                productDto.setSellingPrice(record.getStairPrice());   //售价 == 一级代理设置的价格
            }else if (roleName.equals("second")){
                productDto.setFloorPrice(record.getAdminPrice());   //底价 == 一级代理设置的价格
                productDto.setSellingPrice(record.getSecondPrice());   //售价 == 二级代理设置的价格
            }else{  //否则为用户角色
                //需要判断改用户是属于哪个代理下的    然后显示对应的售价
                QueryWrapper<SysUser> queryUser = new QueryWrapper<>();
                queryUser.eq("user_name", sysUser.getAgentName());
                SysUser user = userService.getOne(queryUser);
                String roleName1 = getRoleName(user);
                if (roleName.equals("stair")){
                    productDto.setSellingPrice(record.getStairPrice());   //售价 == 一级代理设置的价格
                }else if (roleName.equals("second")){
                    productDto.setSellingPrice(record.getSecondPrice());   //售价 == 二级代理设置的价格
                }
            }
            list.add(productDto);
        }
        pageResult.setData(list);

        return pageResult;

    }

}
