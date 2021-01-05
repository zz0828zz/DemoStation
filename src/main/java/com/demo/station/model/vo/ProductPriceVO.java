package com.demo.station.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 代理修改产品价格
 * @Author lipb
 **/
@Data
@ApiModel
public class ProductPriceVO{

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品价格")
    private BigDecimal price;
}
