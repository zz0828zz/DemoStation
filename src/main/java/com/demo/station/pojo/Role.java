package com.demo.station.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : lipb
 * @date : 2020-11-23 14:57
 */
@Data
@TableName("role")
@ApiModel("角色对象")
public class Role implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名称",required = true)
    private String roleName;

    @ApiModelProperty(value = "角色说明",required = true)
    private String description;
}
