package com.wanzhk.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanzhk.api.modules.entity.TbUserRole;

import java.util.List;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
public interface UserRoleService extends IService<TbUserRole> {

    List<Integer> getRoleIdsByUserId(String userId);
}
