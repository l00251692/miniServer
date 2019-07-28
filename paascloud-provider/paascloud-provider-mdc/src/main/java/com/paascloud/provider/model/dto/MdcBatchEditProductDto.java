/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcEditProductDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import com.paascloud.core.mybatis.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Mdc edit category dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class MdcBatchEditProductDto extends BaseEntity {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 商品ID
	 */
	private List<String> idList;


	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	@NotBlank(message = "品类状态不能为空")
	private Integer status;
	
	
	/**
	  * 新品状态.1-是 0-否
     */
    @NotBlank(message = "新品状态不能为空")
	private Integer newStatus;
	
    /**
          * 发布状态.1-是 0-否
     */
    @NotBlank(message = "发布状态不能为空")
	private Integer publishStatus;
	
    /**
           * 推荐状态.1-是 0-否
     */
    @NotBlank(message = "推荐状态不能为空")
	private Integer recommendStatus;
	
    /**
           * 预售状态.1-是 0-否
     */
    @NotBlank(message = "预售状态不能为空")
	private Integer previewStatus;
    
    

}
