/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacWxUser.java
 * 创建人：Todd
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paascloud.core.mybatis.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * The class Uac user.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "wx_uac_user")
@Alias(value = "uacWxUser")
public class UacWxUser extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7527762880322223895L;

	/**
	 * 登录名
	 */
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	
	
	/**
	 * 用户昵称
	 */
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 头像地址
	 */
	@Column(name = "avatar_url")
	private String avatarUrl;

	/**
	 * 性别
	 */
	@Column(name = "gender")
	private short gender;

	/**
	 * 国家
	 */
	@Column(name = "country")
	private String country;
	
	/**
	 * 省
	 */
	@Column(name = "province")
	private String province;
	
	/**
	 * 城市
	 */
	@Column(name = "city")
	private String city;

	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 最后登录时间
	 */
	@Column(name = "last_login_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;
	
	
	/**
	 * 最后登录位置
	 */
	@Column(name = "last_login_location")
	private String lastLoginLocation;

	/**
	 * 描述
	 */
	@Column(name = "remark")
	private String remark;

	
}