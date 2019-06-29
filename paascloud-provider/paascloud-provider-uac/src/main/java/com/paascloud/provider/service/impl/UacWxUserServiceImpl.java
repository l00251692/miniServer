package com.paascloud.provider.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacWxUserMapper;
import com.paascloud.provider.model.domain.UacWxUser;
import com.paascloud.provider.service.UacWxUserService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UacWxUserServiceImpl  extends BaseService<UacWxUser> implements UacWxUserService {

	@Resource
	private UacWxUserMapper uacWxUserMapper;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacWxUser selectUserInfoByUserId(String userId){

		return uacWxUserMapper.selectUserInfoByUserId(userId);
	}

	public int updateUacWxUser(UacWxUser uacWxUser){
		
		logger.info("更新用户信息 uacUser={}", uacWxUser);
		int updateResult = uacWxUserMapper.updateUacWxUser(uacWxUser);
		if (updateResult < 1) {
			logger.info("用户【 {} 】修改用户信息失败", uacWxUser.getUserId());
		} else {
			logger.info("用户【 {} 】修改用户信息成功", uacWxUser.getUserId());
		}
		return updateResult;
		
	}
	
	public int addUacWxUser(UacWxUser uacWxUser){
		
		logger.info("添加信息 uacUser={}", uacWxUser);
		int updateResult = uacWxUserMapper.addUacWxUser(uacWxUser);
		if (updateResult < 1) {
			logger.info("用户【 {} 】添加用户信息失败", uacWxUser.getUserId());
		} else {
			logger.info("用户【 {} 】添加用户信息成功", uacWxUser.getUserId());
		}
		return updateResult;
		
	}
	
}
