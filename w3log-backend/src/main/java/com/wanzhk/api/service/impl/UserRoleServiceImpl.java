package com.wanzhk.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanzhk.api.mapper.UserRoleMapper;
import com.wanzhk.api.modules.entity.TbUserRole;
import com.wanzhk.api.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, TbUserRole> implements UserRoleService {


    @Override
    public List<Integer> getRoleIdsByUserId(String userId) {
        return baseMapper.getRoleIdsByUserId(userId);
    }

}
