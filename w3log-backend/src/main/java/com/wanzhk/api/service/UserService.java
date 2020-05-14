package com.wanzhk.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanzhk.api.modules.entity.TbUser;

/**
 * 用户接口
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
public interface UserService extends IService<TbUser> {


    TbUser getUserByUserName(String username);
}
