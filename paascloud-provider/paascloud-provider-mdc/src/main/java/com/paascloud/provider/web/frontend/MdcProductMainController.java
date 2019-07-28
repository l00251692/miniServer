/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.MdcBatchEditProductDto;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.provider.service.OpcOssFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductMainController extends BaseController {

	@Resource
	private MdcProductService mdcProductService;
	
	@Resource
	private OpcOssFeignApi opcOssFeignApi;
	
	@Resource 
	MdcProductCategoryService mdcProductCategoryService;

	/**
	 * 分页查询商品列表.
	 *
	 * @param mdcProduct the mdc product
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryProductListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public Wrapper<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody MdcProduct mdcProduct) {

		logger.info("分页查询商品列表, mdcProduct={}", mdcProduct);
		PageHelper.startPage(mdcProduct.getPageNum(), mdcProduct.getPageSize());
		mdcProduct.setOrderBy("update_time desc");
		List<ProductVo> productVoList = mdcProductService.queryProductListWithPage(mdcProduct);
		
		fillUpProductVo(productVoList);
		
		return WrapMapper.ok(new PageInfo<>(productVoList));
	}

	/**
     * @param roleVoList
     */
    private void fillUpProductVo(List<ProductVo> productVoList) {
        if (CollectionUtils.isEmpty(productVoList)) {
            return;
        }
        
        for (ProductVo productVo : productVoList) {
            productVo.setPic(getPicByAttatchmentId(productVo.getMainImage()));
            productVo.setCategoryCode(getCategoryCodeById(productVo.getCategoryId()));
        }
        
    }

    /**
     * @param categoryId
     * @return
     */
    private String getCategoryCodeById(Long categoryId) {
        MdcProductCategory productCategory = mdcProductCategoryService.getByCategoryId(categoryId);
        return productCategory == null ? null : productCategory.getCategoryCode();
    }

    /**
     * @param mainImage
     * @return
     */
    private String getPicByAttatchmentId(String attachmentId) {
        OptGetUrlRequest optGetUrlRequest = new OptGetUrlRequest();
        optGetUrlRequest.setAttachmentId(Long.valueOf(attachmentId));
        optGetUrlRequest.setEncrypt(false);
        Wrapper<String> result = opcOssFeignApi.getFileUrl(optGetUrlRequest);
        if (result.success()) {
            return "http://" + result.getResult();
        } else {
            return null;
        }
        
    }

    /**
	 * 商品详情.
	 */
	@PostMapping(value = "/getById/{id}")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public Wrapper<ProductVo> getById(@PathVariable Long id) {
		logger.info("查询商品详情, id={}", id);
		ProductVo productVo = mdcProductService.getProductVo(id);
		return WrapMapper.ok(productVo);
	}

	@PostMapping(value = "/update/newStatus")
	@ApiOperation(httpMethod = "POST", value = "修改是否新品")
	@LogAnnotation
	public Wrapper updateNewStatus(@RequestParam(name="ids") String ids, @RequestParam(name="newStatus") Integer newStatus) {
		logger.info("修改是否新品. ids={}, newStatus={}", ids, newStatus);
		MdcBatchEditProductDto mdcBatchEditProductDto = new MdcBatchEditProductDto();
		mdcBatchEditProductDto.setNewStatus(newStatus);
		if (StringUtils.isEmpty(ids)) {
            
        } else {
            mdcBatchEditProductDto.setIdList(Arrays.asList(ids.split(GlobalConstant.Symbol.COMMA)));
        }
		mdcProductService.batchUpdate(mdcBatchEditProductDto, getLoginAuthDto());
		return WrapMapper.ok();
	}
	
	@PostMapping(value = "/update/publishStatus")
    @ApiOperation(httpMethod = "POST", value = "修改是否新品")
    @LogAnnotation
    public Wrapper updatePublishStatus(@RequestParam(name="ids") String ids, @RequestParam(name="publishStatus") Integer publishStatus) {
        logger.info("修改是否新品. ids={}, publishStatus={}", ids, publishStatus);
        MdcBatchEditProductDto mdcBatchEditProductDto = new MdcBatchEditProductDto();
        mdcBatchEditProductDto.setPublishStatus(publishStatus);
        if (StringUtils.isEmpty(ids)) {
            
        } else {
            mdcBatchEditProductDto.setIdList(Arrays.asList(ids.split(GlobalConstant.Symbol.COMMA)));
        }
        mdcProductService.batchUpdate(mdcBatchEditProductDto, getLoginAuthDto());
        return WrapMapper.ok();
    }
	
	@PostMapping(value = "/update/recommendStatus")
    @ApiOperation(httpMethod = "POST", value = "修改是否新品")
    @LogAnnotation
    public Wrapper updateRecommendStatus(@RequestParam(name="ids") String ids, @RequestParam(name="recommendStatus") Integer recommendStatus) {
        logger.info("修改是否新品. ids={}, recommendStatus={}", ids, recommendStatus);
        MdcBatchEditProductDto mdcBatchEditProductDto = new MdcBatchEditProductDto();
        mdcBatchEditProductDto.setRecommendStatus(recommendStatus);
        if (StringUtils.isEmpty(ids)) {
            
        } else {
            mdcBatchEditProductDto.setIdList(Arrays.asList(ids.split(GlobalConstant.Symbol.COMMA)));
        }
        mdcProductService.batchUpdate(mdcBatchEditProductDto, getLoginAuthDto());
        return WrapMapper.ok();
    }
	
	@PostMapping(value = "/update/previewStatus")
    @ApiOperation(httpMethod = "POST", value = "修改是否新品")
    @LogAnnotation
    public Wrapper updatePreviewStatus(@RequestParam(name="ids") String ids, @RequestParam(name="previewStatus") Integer previewStatus) {
        logger.info("修改是否新品. ids={}, previewStatus={}", ids, previewStatus);
        MdcBatchEditProductDto mdcBatchEditProductDto = new MdcBatchEditProductDto();
        mdcBatchEditProductDto.setPreviewStatus(previewStatus);
        if (StringUtils.isEmpty(ids)) {
            
        } else {
            mdcBatchEditProductDto.setIdList(Arrays.asList(ids.split(GlobalConstant.Symbol.COMMA)));
        }
        mdcProductService.batchUpdate(mdcBatchEditProductDto, getLoginAuthDto());
        return WrapMapper.ok();
    }
	
	@PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "编辑商品")
    @LogAnnotation
    public Wrapper saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto) {
        logger.info("编辑商品. mdcCategoryAddDto={}", mdcCategoryAddDto);
        mdcProductService.saveProduct(mdcCategoryAddDto, getLoginAuthDto());
        return WrapMapper.ok();
    }

	@PostMapping(value = "/deleteProductById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除商品信息")
	@LogAnnotation
	public Wrapper<ProductVo> deleteProductById(@PathVariable Long id) {
		logger.info("删除商品信息, id={}", id);
		mdcProductService.deleteProductById(id);
		return WrapMapper.ok();
	}
}
