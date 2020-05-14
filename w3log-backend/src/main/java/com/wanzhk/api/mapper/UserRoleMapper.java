package com.wanzhk.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanzhk.api.modules.entity.TbUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<TbUserRole> {

    List<Integer> getRoleIdsByUserId(@Param("userId") String userId);
}
