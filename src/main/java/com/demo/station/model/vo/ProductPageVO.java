package com.demo.station.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lipb
 **/
@Data
@ApiModel("产品列表分页查询")
public class ProductPageVO extends PageInfo{

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品类型    与类型表关联")
    private Long productTypeId;
}
