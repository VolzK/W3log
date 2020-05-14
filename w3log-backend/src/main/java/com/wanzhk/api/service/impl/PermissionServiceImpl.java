package com.wanzhk.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanzhk.api.mapper.PermissionMapper;
import com.wanzhk.api.modules.entity.TbPermission;
import com.wanzhk.api.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, TbPermission> implements PermissionService {

}
