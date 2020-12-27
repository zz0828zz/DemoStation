package com.demo.station.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lipb
 **/
@Data
@ApiModel("用户对象")
public class PageInfo {
    @ApiModelProperty(value = "当前页",required = true)
    private Integer current;

    @ApiModelProperty(value = "每页数量",required = true)
    private Integer size;
}
