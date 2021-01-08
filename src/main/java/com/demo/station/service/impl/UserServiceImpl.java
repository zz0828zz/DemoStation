package com.demo.station.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.config.GoalException.BusinessException;
import com.demo.station.mapper.SysUserMapper;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.model.dto.SysUserInfoDto;
import com.demo.station.model.vo.SaveSysUserVO;
import com.demo.station.model.vo.SelectSysUserPage;
import com.demo.station.model.vo.UpdateSysUserVO;
import com.demo.station.pojo.SysUser;
import com.demo.station.pojo.SysUserRole;
import com.demo.station.service.SysUserRoleService;
import com.demo.station.service.SysUserService;
import com.demo.station.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lipb
 **/
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    @Transactional
    public boolean saveUser(SaveSysUserVO sysUserVO) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",sysUserVO.getUserName());

        Integer integer = userMapper.selectCount(queryWrapper);
        if (integer>0){
            throw new BusinessException("该用户名已存在，请换一个用户名尝试！");
        }


        //添加用户
        SysUser sysUser = CopyUtils.copyPojo(sysUserVO, SysUser.class);
        sysUser.setUserPassword(new BCryptPasswordEncoder().encode(sysUser.getUserPassword()));

        userMapper.insert(sysUser);

        //添加角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(sysUserVO.getRole());
        sysUserRoleService.save(sysUserRole);
        return true;
    }

    @Override
    public boolean updateUser(UpdateSysUserVO sysUserVO) {
        SysUser sysUser = userMapper.selectById(sysUserVO.getId());
        if (StringUtils.isNotEmpty(sysUserVO.getUserName())){
            if (!sysUser.getUserName().equals(sysUserVO.getUserName())){
                QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_name",sysUserVO.getUserName());

                Integer integer = userMapper.selectCount(queryWrapper);
                if (integer>0){
                    throw new BusinessException("该用户名已存在，请换一个用户名尝试！");
                }
                sysUser.setUserName(sysUserVO.getUserName());
            }
        }
        sysUser = CopyUtils.copyPojo(sysUserVO, SysUser.class);

        if (sysUserVO.getUserPassword()!=null){
            sysUser.setUserPassword(new BCryptPasswordEncoder().encode(sysUser.getUserPassword()));
        }
        userMapper.updateById(sysUser);


        if (sysUserVO.getRole()!=null){
            UpdateWrapper<SysUserRole> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id",sysUser.getId());
            updateWrapper.set("role_id",sysUserVO.getRole());
            sysUserRoleService.update(updateWrapper);
        }

        return true;
    }

    @Override
    public List<SysUserInfoDto> getSysUserInfoDto(SelectSysUserPage page) {

        List<SysUserInfoDto> sysUserInfoDto = userMapper.getSysUserInfoDto(page);

        return  sysUserInfoDto;
    }

    @Override
    public int getSysUserCount(SelectSysUserPage page) {
        int count = userMapper.getSysUserCount(page);
        return count;
    }
}
