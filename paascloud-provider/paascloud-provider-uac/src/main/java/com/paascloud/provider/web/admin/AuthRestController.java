/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthRestController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.paascloud.AesCbcUtil;
import com.paascloud.HttpRequest;
import com.google.common.base.Preconditions;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.core.annotation.OperationLogDto;
import com.paascloud.core.config.DataSourceHolder;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.constant.UacApiConstant;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.domain.UacWxUser;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.service.EmailService;
import com.paascloud.provider.service.SmsService;
import com.paascloud.provider.service.UacLogService;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.provider.service.UacWxUserService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不认证的URL请求.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/auth")
@Api(value = "Web-AuthRestController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthRestController extends BaseController {
	@Resource
	private UacUserService uacUserService;
	@Resource
	private UacWxUserService uacWxUserService;
	@Resource
	private SmsService smsService;
	@Resource
	private EmailService emailService;
	@Resource
	private UacLogService uacLogService;

	/**
	 * 校验手机号码.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkPhoneActive/{mobileNo}")
	@ApiOperation(httpMethod = "POST", value = "校验手机号码")
	public Wrapper<Boolean> checkPhoneActive(@PathVariable String mobileNo) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setMobileNo(mobileNo);
		uacUser.setAppId(getAppId());
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.ok(count > 0);
	}
	
	/**
	 * 校验手机号码.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/wx/login")
	@ApiOperation(httpMethod = "POST", value = "微信小程序登录")
	public Wrapper<HashMap<Object, Object>> wxLogin(@RequestParam String wxCode, @RequestParam String encryptedData, @RequestParam String iv) {
		HashMap<Object, Object> result = new  HashMap<>();
		
		JSONObject obj = new JSONObject();
		
		//登录凭证不能为空 
		if (wxCode == null || wxCode.length() == 0) 
		{ 
			result.put(GlobalConstant.STATUS, GlobalConstant.FAILEURE);
			result.put(GlobalConstant.MESSAGE, "授权凭证错误");
			return WrapMapper.ok(result);
		} 

		//小程序唯一标识  (在微信小程序管理后台获取) 
		String wxAppid = getAppId();
		//小程序的 app secret (在微信小程序管理后台获取) 
		String wxSecret = "7ff5471d0e423cd277c35c7a7f958b7c";
	    //授权（必填） 
		String grant_type = "authorization_code"; 
	
		//////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid //////////////// 
		//请求参数 
		String params = "appid=" + wxAppid + "&secret=" + wxSecret + "&js_code=" + wxCode + "&grant_type=" + grant_type; 
		//发送请求 
		String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params); 
		//解析相应内容（转换成json对象） 
		//JSONObject json = JSONObject.fromObject(sr); 
		JSONObject json = JSONObject.parseObject(sr);
		
		//获取会话密钥（session_key）
		String session_key = "";
		if(json.get("session_key") != null)
		{
			session_key = json.get("session_key").toString(); 
		}
		else
		{
			obj.put("message", "获取用户秘钥信息失败");
			result.put(GlobalConstant.STATUS, GlobalConstant.FAILEURE);
			result.put(GlobalConstant.MESSAGE, obj);
			return WrapMapper.ok(result);
		}
		
		//////////////// 2、对encryptedData加密数据进行AES解密 //////////////// 
		try 
		{ 
			String decryptResult = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			if (null != decryptResult && decryptResult.length() > 0) 
			{ 
				JSONObject userInfoJSON = JSONObject.parseObject(decryptResult); 
				
				JSONObject userInfo = new JSONObject(); 
				userInfo.put("openId", userInfoJSON.get("openId")); 
				userInfo.put("nickName", userInfoJSON.get("nickName")); 
				userInfo.put("gender", userInfoJSON.get("gender")); 
				userInfo.put("city", userInfoJSON.get("city")); 
				userInfo.put("province", userInfoJSON.get("province")); 
				userInfo.put("country", userInfoJSON.get("country")); 
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl")); 
				userInfo.put("userId", userInfoJSON.get("openId")); 
				userInfo.put("userToken", userInfoJSON.get("unionId"));
				
				try {
					DataSourceHolder.setDataSource(wxAppid);
					UacWxUser user = uacWxUserService.selectUserInfoByUserId(userInfoJSON.get("openId").toString());
				
					if (user != null)
					{
						user.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
						user.setNickName(userInfoJSON.get("nickName").toString());
						user.setLastLoginTime(new Date());
						user.setGender(new Short(userInfoJSON.get("gender").toString()));
						uacWxUserService.updateUacWxUser(user);
					}
					else
					{
						Date now = new Date();
						UacWxUser newUser = new UacWxUser();
						newUser.setUserId(userInfoJSON.get("openId").toString());
						newUser.setCreateTime(now);
						newUser.setNickName(userInfoJSON.get("nickName").toString());
						newUser.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
						newUser.setGender(new Short(userInfoJSON.get("gender").toString()));
						newUser.setCountry(userInfoJSON.get("country").toString());
						newUser.setProvince(userInfoJSON.get("province").toString());
						newUser.setCity(userInfoJSON.get("city").toString());
						newUser.setLastLoginTime(now);
						newUser.setLastLoginLocation("");
						newUser.setRemark("");
							
						uacWxUserService.addUacWxUser(newUser);
					}				
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					logger.error("[login fail]userId=" + userInfoJSON.get("openId"));
				}
				
				obj.put("userInfo", userInfo);
				
				result.put(GlobalConstant.STATUS, GlobalConstant.SUCCESS);
				result.put(GlobalConstant.DATA, obj);
				
				return WrapMapper.ok(result);
			}
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
			logger.error("[login fail]" + e.getMessage()); 
		}
				
		result.put(GlobalConstant.STATUS, GlobalConstant.FAILEURE);
		result.put(GlobalConstant.MESSAGE, "内部异常处理错误");
		return WrapMapper.ok(result);			
	}

	/**
	 * 校验邮箱.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmailActive/{email:.+}")
	@ApiOperation(httpMethod = "POST", value = "校验邮箱")
	public Wrapper<Boolean> checkEmailActive(@PathVariable("email") String email) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setEmail(email);
		uacUser.setAppId(getAppId());
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.ok(count > 0);
	}

	/**
	 * 校验数据.
	 *
	 * @param checkValidDto the check valid dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkValid")
	@ApiOperation(httpMethod = "POST", value = "校验数据")
	public Wrapper checkValid(@RequestBody CheckValidDto checkValidDto) {
		String type = checkValidDto.getType();
		String validValue = checkValidDto.getValidValue();

		Preconditions.checkArgument(StringUtils.isNotEmpty(validValue), "参数错误");
		String message = null;
		boolean result = false;
		//开始校验
		if (UacApiConstant.Valid.LOGIN_NAME.equals(type)) {
			result = uacUserService.checkLoginName(validValue);
			if (!result) {
				message = "用户名已存在";
			}
		}
		if (UacApiConstant.Valid.EMAIL.equals(type)) {
			result = uacUserService.checkEmail(validValue);
			if (!result) {
				message = "邮箱已存在";
			}
		}

		if (UacApiConstant.Valid.MOBILE_NO.equals(type)) {
			result = uacUserService.checkMobileNo(validValue);
			if (!result) {
				message = "手机号码已存在";
			}
		}

		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, message, result);
	}


	/**
	 * 重置密码-邮箱-提交.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdEmail")
	@ApiOperation(httpMethod = "POST", value = "重置密码-邮箱-提交")
	public Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email) {
		logger.info("重置密码-邮箱-提交, email={}", email);
		emailService.submitResetPwdEmail(email);
		return WrapMapper.ok();
	}


	/**
	 * 重置密码-手机-提交.
	 *
	 * @param mobile   the mobile
	 * @param response the response
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdPhone")
	@ApiOperation(httpMethod = "POST", value = "重置密码-手机-提交")
	public Wrapper<String> submitResetPwdPhone(@RequestParam("mobile") String mobile, HttpServletResponse response) {
		logger.info("重置密码-手机-提交, mobile={}", mobile);
		String token = smsService.submitResetPwdPhone(mobile, response);
		return WrapMapper.ok(token);
	}

	/**
	 * 重置密码-最终提交.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resetLoginPwd")
	@ApiOperation(httpMethod = "POST", value = "重置密码-最终提交")
	public Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto) {
		uacUserService.resetLoginPwd(resetLoginPwdDto);
		return WrapMapper.ok();
	}

	/**
	 * 注册用户.
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/register")
	@ApiOperation(httpMethod = "POST", value = "注册用户")
	public Wrapper registerUser(UserRegisterDto user) {
	    user.setAppId(getAppId());
		uacUserService.register(user);
		return WrapMapper.ok();
	}

	/**
	 * 激活用户.
	 *
	 * @param activeUserToken the active user token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/activeUser/{activeUserToken}")
	@ApiOperation(httpMethod = "POST", value = "激活用户")
	public Wrapper activeUser(@PathVariable String activeUserToken) {
		uacUserService.activeUser(activeUserToken);
		return WrapMapper.ok("激活成功");
	}

	/**
	 * 查询日志.
	 *
	 * @param operationLogDto the operation log dto
	 *
	 * @return the integer
	 */
	@PostMapping(value = "/saveLog")
	@ApiOperation(httpMethod = "POST", value = "查询日志")
	public Integer saveLog(@RequestBody OperationLogDto operationLogDto) {
		logger.info("saveLog - 保存操作日志. operationLogDto={}", operationLogDto);
		return uacLogService.saveOperationLog(operationLogDto);
	}

	@PostMapping(value = "/callback/qq")
	public void callbackQQ(HttpServletRequest request) {
		logger.info("callback - callback qq. request={}", request);
	}
}
