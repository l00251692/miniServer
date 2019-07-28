/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ProductVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import com.paascloud.provider.model.dto.oss.ElementImgUrlDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Product vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductVo extends BaseVo {
	private static final long serialVersionUID = 5719854343599455196L;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品货号
	 */
	@Column(name = "product_sn")
	private String productSn;

	/**
	 * 商品副标题
	 */
	private String subtitle;

	/**
	 * 产品主图,url相对地址
	 */
	@Column(name = "main_image")
	private String mainImage;

	/**
	 * 价格,单位-元保留两位小数
	 */
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	private Integer stock;

	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	private Integer status;

	/**
	 * 图片地址,json格式,扩展用
	 */
	@Column(name = "sub_images")
	private String subImages;

	/**
	 * 商品介绍
	 */
	private String description;

	/**
	 * 分类ID
	 */
	private List<Long> categoryIdList;
	
	/**
	 * 
	 */
	@Column(name = "category_id")
	private Long categoryId;
	
	/**
	 * 
	 */
	private String categoryCode;

	/**
	 * 图片集合
	 */
	private List<ElementImgUrlDto> imgUrlList;
	
	/**
	 * 
	 */
	@Column(name = "original_price")
	private BigDecimal originalPrice;
	
	private String unit;
	
	private BigDecimal weight;
	
	private Integer sort;
	
	private String pic;
	
	@Column(name = "publish_status")
	private Integer publishStatus;
	
	@Column(name = "new_status")
	private Integer newStatus;
	
	@Column(name = "recommend_status")
	private Integer recommendStatus;
	
	@Column(name = "detail_title")
	private String detailTitle;
	
	@Column(name = "detail_desc")
	private String detailDesc;
	
	@Column(name = "service_guarantee")
	private String serviceGuarantee;
	
	private String note;
	
	@Column(name = "preview_status")
	private Integer previewStatus;
}
