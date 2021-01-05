package com.demo.station.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lipb
 **/
@Data
@ApiModel("保存用户信息")
public class SaveSysUserVO {


    @ApiModelProperty(value = "用户账号",required = true)
    private String userName;

    @ApiModelProperty(value = "用户密码",required = true)
    private String userPassword;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户qq")
    private Long qq;

    @ApiModelProperty(value = "代理人账号")
    private String agentName;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "用户角色id 1：admin(管理员)  2：stair(一级代理)  3：second（二级代理）  4：user（普通用户） 传参1234",required = true)
    private Long role;


}
