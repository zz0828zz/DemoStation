package com.demo.station.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lipb
 **/
@Data
@ApiModel("产品对象 -- 后台")
public class ProductDto implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "产品演示地址")
    private String productDemoStationUrl;
    @ApiModelProperty(value = "产品演示登录名称")
    private String productDemoStationName;
    @ApiModelProperty(value = "产品演示登陆密码")
    private String productDemoStationPwd;
    @ApiModelProperty(value = "产品后台地址")
    private String productAdminUrl;
    @ApiModelProperty(value = "产品后台登录名称")
    private String productAdminName;
    @ApiModelProperty(value = "产品后台登陆密码")
    private String productAdminPwd;

    @ApiModelProperty(value = "产品说明")
    private String productExplain;

    @ApiModelProperty(value = "产品类型id    与类型表关联")
    private Long productTypeId;

    @ApiModelProperty(value = "产品类型名称    与类型表关联")
    private String productTypeName;

    @ApiModelProperty(value = "底价")
    private BigDecimal  floorPrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal  sellingPrice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
