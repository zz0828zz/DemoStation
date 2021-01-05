package com.demo.station.model.vo;

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
@ApiModel
public class SaveOrUpdateProductVO implements Serializable {
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
    private String productExplain;

    @ApiModelProperty(value = "产品类型    与类型表关联")
    private Long productTypeId;

    @ApiModelProperty(value = "产品价格")
    private BigDecimal price;


}
