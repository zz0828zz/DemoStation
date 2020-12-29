package com.demo.station.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户返回实体类
 * @Author lipb
 **/
@Data
@ApiModel("返回当前登录用户信息")
public class SysUserDto implements Serializable {
   @ApiModelProperty("用户id")
   private Long userId;

   @ApiModelProperty("用户名称")
   private String userName;

   @ApiModelProperty("角色")
   private List<String> roles;
}
