package com.wanzhk.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanzhk.api.modules.entity.TbRolePermission;

import java.util.List;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
public interface RolePermissionService extends IService<TbRolePermission> {

    List<Integer> getPermissionIdsByRoles(List<Integer> roleIds);
}
