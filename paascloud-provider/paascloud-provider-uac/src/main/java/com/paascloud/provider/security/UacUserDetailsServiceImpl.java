package com.paascloud.provider.security;

import com.paascloud.DataSourceMapHolder;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.core.config.DataSourceHolder;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.security.core.SecurityUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Collection;

/**
 * The class Uac user details service.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UacUserService uacUserService;

	/**
	 * Load user by username user details.
	 *
	 * @param username the username
	 *
	 * @return the user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Collection<GrantedAuthority> grantedAuthorities;
		
		UacUser user = uacUserService.findByLoginName(username, String.valueOf(DataSourceMapHolder.get(GlobalConstant.Sys.APP_ID)));
		if (user == null) {
			throw new BadCredentialsException("用户名不存在或者密码错误");
		}
		
		user = uacUserService.findUserInfoByUserId(user.getId());
		grantedAuthorities = uacUserService.loadUserAuthorities(user.getId());
		return new SecurityUser(user.getId(), user.getLoginName(), user.getLoginPwd(),
				user.getUserName(), user.getGroupId(), user.getGroupName(), user.getStatus(), grantedAuthorities);
	}
}
