package com.paascloud.provider.service;

import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.UacWxUser;

public interface UacWxUserService extends IService<UacWxUser> {

	UacWxUser selectUserInfoByUserId(String userId);

	int updateUacWxUser(UacWxUser uacWxUser);
	
	int addUacWxUser(UacWxUser uacWxUser);
	
}
