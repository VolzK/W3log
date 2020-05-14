package com.wanzhk.api.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色权限中间表
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Data
@TableName("tb_role_permission")
@Accessors(chain = true)
@NoArgsConstructor
public class TbRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * role
     */
    private String roleId;

    /**
     * permission
     */
    private String permissionId;

}
