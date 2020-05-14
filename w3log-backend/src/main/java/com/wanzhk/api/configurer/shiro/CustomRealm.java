package com.wanzhk.api.configurer.shiro;

import com.wanzhk.api.core.Contants;
import com.wanzhk.api.modules.entity.TbPermission;
import com.wanzhk.api.modules.entity.TbRole;
import com.wanzhk.api.modules.entity.TbUser;
import com.wanzhk.api.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Realm
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
public class CustomRealm extends AuthorizingRealm {

    @Lazy
    @Resource
    UserService userService;

    @Lazy
    @Resource
    RoleService roleService;

    @Lazy
    @Resource
    UserRoleService userRoleService;

    @Lazy
    @Resource
    PermissionService permissionService;

    @Lazy
    @Resource
    RolePermissionService rolePermissionService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证方法————");
        TbUser tbUser = (TbUser) principalCollection.getPrimaryPrincipal();

        if (null != tbUser) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


            List<Integer> roleIds = userRoleService.getRoleIdsByUserId(tbUser.getId());
            List<Integer> permissionIds = rolePermissionService.getPermissionIdsByRoles(roleIds);

            // 获取role
            List<TbRole> tbRoles = roleService.listByIds(roleIds);
            // 获取permission
            List<TbPermission> tbPermissions = permissionService.listByIds(permissionIds);

            Collection<String> rolesCollection = new HashSet<>();
            Collection<String> permissionCollection = new HashSet<>();

            // 将每一个role的name装进collection集合
            tbRoles.forEach(tbRole -> rolesCollection.add(tbRole.getName()));
            // 将每一个permission的name装进collection集合
            tbPermissions.forEach(permission -> permissionCollection.add(permission.getName()));
            // 为用户授权
            info.addStringPermissions(permissionCollection);
            // 为用户授予角色
            info.addRoles(rolesCollection);
            return info;
        }
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        TbUser dbUser = userService.getUserByUserName(token.getUsername());
        if (null == dbUser) {
            throw new UnknownAccountException("账号不存在");
        } else if (!dbUser.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        } else if (Contants.ACCOUNT_LOCK == dbUser.getStatus()) {
            throw new LockedAccountException("账号已被锁定");
        }

        // 将用户存放到登陆认证info中，无需自己做密码比对
        return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), getName());
    }
}
