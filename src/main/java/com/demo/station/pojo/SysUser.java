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
@TableName("sys_user")
@ApiModel("用户对象")
public class SysUser implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账号", required = true)
    private String userName;

    @ApiModelProperty(value = "用户密码", required = true)
    private String userPassword;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户qq")
    private String qq;

    @ApiModelProperty(value = "代理人账号")
    private String agentName;

    @ApiModelProperty(value = "备注")
    private String note;

}
