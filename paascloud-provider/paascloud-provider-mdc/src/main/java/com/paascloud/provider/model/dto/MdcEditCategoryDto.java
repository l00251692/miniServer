/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcEditCategoryDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;


import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * The class Mdc edit category dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class MdcEditCategoryDto implements Serializable {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 首图的流水号
	 */
	private Long imgId;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 父类别id当id=0时说明是根节点,一级类别
	 */
	private Long pid;

	/**
	 * 类别名称
	 */
	private String name;

	/**
	 * 类别状态1-正常,2-已废弃
	 */
	private Integer status;

	/**
	 * 排序编号,同类展示顺序,数值相等则自然排序
	 */
	private Integer sortOrder;

	/**
	 * 分类编码
	 */
	private String categoryCode;
	
	/**
     * 级别，当pid=0时，为一级级别
    */
   private Integer level;

   
   /**
     * 是否显示在导航栏 0-不显示在导航栏，1-显示
    */
   private Integer navStatus;
   
   /**
          * 是否显示 0-不显示，1-显示
    */
   private Integer showStatus;
   
   /**
          * 关键词
    */
   private String keywords;
   
   /**
          * 分类描述
    */
   private String description;
   
   /**
        * 数量单位
   */
   private String productUnit;
}
