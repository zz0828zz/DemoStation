package com.demo.station.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lipb
 **/
@Data
@TableName("user_role")
@ApiModel("用户角色对象")
public class SysUserRole implements Serializable {


    @ApiModelProperty(value = "用户id",required = true)
   private Long userId;

    @ApiModelProperty(value = "角色id",required = true)
   private Long roleId;
}
