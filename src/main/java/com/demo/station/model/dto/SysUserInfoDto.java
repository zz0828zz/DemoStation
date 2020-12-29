package com.demo.station.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel("返回用户信息")
public class SysUserInfoDto implements Serializable {
   @ApiModelProperty(value = "用户id")
   private Long id;

   @ApiModelProperty(value = "用户账号")
   private String userName;

   @ApiModelProperty(value = "用户手机号")
   private String phone;

   @ApiModelProperty(value = "用户qq")
   private Long qq;

   @ApiModelProperty(value = "用户手机号")
   private String agentName;

   @ApiModelProperty(value = "用户权限")
   private String roleName;
}
