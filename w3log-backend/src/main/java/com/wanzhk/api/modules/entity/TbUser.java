package com.wanzhk.api.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Data
@TableName("tb_user")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名
     */
    @NotNull(message = "username不能为空")
    @Length(min = 2, max = 20, message = "用户名长度2-20之间")
    @TableField("username")
    private String username;

    /**
     * 别名
     */
    @Length(min = 2, max = 20, message = "别名长度2-20之间")
    @TableField("alias_name")
    private String aliasName;

    /**
     * 密码
     */
    @TableField("password")
    @Length(min = 5, max = 20, message = "密码长度2-20之间")
    private String password;

    /**
     * 1：使用中，0：封号
     */
    @TableField("status")
    private Integer status;

    public TbUser() {

    }

}
