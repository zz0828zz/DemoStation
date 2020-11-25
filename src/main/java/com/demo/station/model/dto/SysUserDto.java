package com.demo.station.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户返回实体类
 * @Author lipb
 **/
@Data
public class SysUserDto implements Serializable {
   private Integer id;
   private String userName;
   private String userPassword;
}
