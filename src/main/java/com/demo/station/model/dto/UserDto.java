package com.demo.station.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增、修改user实体类
 * @Author lipb
 **/
@Data
public class UserDto implements Serializable {
   private Integer id;
   private String userName;
   private String userPassword;
}
