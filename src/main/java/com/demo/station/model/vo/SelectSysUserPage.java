package com.demo.station.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lipb
 **/
@Data
@ApiModel("分页查询用户信息")
public class SelectSysUserPage extends PageInfo {
    //    @ApiModelProperty(value = "开始查询个数")
    @JsonIgnore
    private Integer firstIndex;

    @ApiModelProperty(value = "用户账号")
    private String sysUserName;

}
