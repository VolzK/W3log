package com.wanzhk.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanzhk.api.mapper.RolePermissionMapper;
import com.wanzhk.api.modules.entity.TbRolePermission;
import com.wanzhk.api.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, TbRolePermission> implements RolePermissionService {

    @Override
    public List<Integer> getPermissionIdsByRoles(List<Integer> roleIds) {
        return baseMapper.getPermissionIdsByRoleIds(roleIds);
    }
}
