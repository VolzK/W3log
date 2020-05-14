package com.wanzhk.api.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色中间表
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Data
@TableName("tb_user_role")
@Accessors(chain = true)
@NoArgsConstructor
public class TbUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

}
