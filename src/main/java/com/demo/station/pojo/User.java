package com.demo.station.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lipb
 **/
@Data
@TableName("user")
public class User implements Serializable {
   private Integer id;
   private String userName;
   private String userPassword;
}
