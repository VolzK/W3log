package com.wanzhk.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanzhk.api.modules.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Mapper
public interface UserMapper extends BaseMapper<TbUser> {

}
