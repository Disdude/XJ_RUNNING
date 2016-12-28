package com.atguigu.jf.console.shiro.realm;
import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.jf.console.demo.service.UserService;

/**
 * 注意：需要继承AuthorizingRealm
 * 有两个回调方法：
 * doGetAuthorizationInfo ： 授权的回调方法
 * doGetAuthenticationInfo ： 认证的回调方法
 */

public class MyRealm extends AuthorizingRealm{
	@Autowired
	UserService userService;
	/* 
	 * 授权
	 * @param principals
	 * @return
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimplePrincipalCollection ssm = (SimplePrincipalCollection) principals;
		Object principal = ssm.getPrimaryPrincipal();
	
		return null;
	}

	/* 
	 * 验证
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		// TODO Auto-generated method stub
				System.out.println("---执行了doGetAuthenticationInfo认证的回调方法----" + new Date());
				UsernamePasswordToken uToken = (UsernamePasswordToken) token;
				// principal : 可以包含用户的任何信息
				String username = uToken.getUsername();
				//char[] password = uToken.getPassword();
				String	password =  String.valueOf(uToken.getPassword());
				Object principal = uToken.getPrincipal();
				System.out.println("---principal---" + principal);
				System.out.println("---username---" + username);
				System.out.println("---password---" + password);
				
				// 模拟，假设去查数据库
				String credentials = "";
				if("admin".equals(principal)){
					credentials = "12345";
				}
				/**
				 * principal : 用户名，也可以存放用户的其他任意信息（也可以包含整个用户对象）
				 * credentials ： 密码
				 * realmName ： 固定写法getName()
				 */
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, getName());
				return info;
		
		
		
		
		
		
		
	}

}
