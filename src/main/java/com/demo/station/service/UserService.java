package com.demo.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.station.model.dto.UserDto;
import com.demo.station.pojo.User;

public interface UserService extends IService<User> {
    boolean saveUser(UserDto userDto);
}
