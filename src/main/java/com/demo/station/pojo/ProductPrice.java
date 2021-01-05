package com.demo.station.pojo;

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
@TableName("product_price")
@ApiModel("产品价格对象")
public class ProductPrice implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "用户账号")        //使用产品id+用户账号锁定唯一价格
    private String userName;

    @ApiModelProperty(value = "产品价格")
    private BigDecimal price;
}
