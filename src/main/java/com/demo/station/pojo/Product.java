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
@TableName("product")
@ApiModel("产品对象")
public class Product implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "跳转地址")
    private String skipUrl;

    @ApiModelProperty(value = "产品说明")
    private String explain;

    @ApiModelProperty(value = "产品类型    与类型表关联")
    private Long productTypeId;

    @ApiModelProperty(value = "管理员价格")
    private BigDecimal adminPrice;

    @ApiModelProperty(value = "一级代理价格")
    private BigDecimal stairPrice;

    @ApiModelProperty(value = "二级代理价格")
    private BigDecimal secondPrice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
