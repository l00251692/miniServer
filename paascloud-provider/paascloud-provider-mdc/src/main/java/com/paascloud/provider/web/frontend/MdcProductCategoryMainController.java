/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.BaseQuery;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.MdcEditCategoryDto;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc category main controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductCategoryMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductCategoryMainController extends BaseController {

	@Resource
	private MdcProductCategoryService mdcProductCategoryService;

	/**
	 * 获取商品分类列表数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取商品分类树")
	public Wrapper<List<MdcCategoryVo>> queryCategoryTreeList() {
		List<MdcCategoryVo> categoryVoList = mdcProductCategoryService.getCategoryTreeList();
		return WrapMapper.ok(categoryVoList);
	}

	/**
	 * 根据ID获取商品分类信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
	public Wrapper<MdcCategoryVo> queryCategoryVoById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id) {
		logger.info("根据Id查询商品分类信息, categoryId={}", id);
		MdcCategoryVo mdcCategoryVo = mdcProductCategoryService.getMdcCategoryVoById(id);
		return WrapMapper.ok(mdcCategoryVo);
	}
	
	/**
           * 根据ID获取商品分类信息.
     *
     * @param id the id
     *
     * @return the wrapper
     */
    @PostMapping(value = "/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
    public Wrapper<MdcProductCategory> queryCategoryById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id) {
        logger.info("根据Id查询商品分类信息, categoryId={}", id);
        MdcProductCategory mdcProductCategory = mdcProductCategoryService.getMdcProductCategoryById(id);
        return WrapMapper.ok(mdcProductCategory);
    }
    
    @PostMapping(value = "/update/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
    public Wrapper updateCategoryById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id,
            @RequestBody MdcEditCategoryDto mdcCategoryAddDto) {
        logger.info("根据Id查询商品分类信息, categoryId={}", id);
        MdcProductCategory mdcCategory = new MdcProductCategory();
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        BeanUtils.copyProperties(mdcCategoryAddDto, mdcCategory);
        mdcCategory.setId(id);
        
        mdcProductCategoryService.saveMdcCategory(mdcCategory, loginAuthDto);
        return WrapMapper.ok();
    }

    @PostMapping(value = "/create")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
    public Wrapper createCategory(@RequestBody MdcEditCategoryDto mdcCategoryAddDto) {
        MdcProductCategory mdcCategory = new MdcProductCategory();
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        BeanUtils.copyProperties(mdcCategoryAddDto, mdcCategory);
        mdcCategory.setId(null);
        
        mdcProductCategoryService.saveMdcCategory(mdcCategory, loginAuthDto);
        return WrapMapper.ok();
    }

	/**
	 * 根据id修改商品分类的禁用状态
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "根据id修改商品分类的禁用状态")
	@LogAnnotation
	public Wrapper updateMdcCategoryStatusById(@ApiParam(name = "mdcCategoryStatusDto", value = "修改商品分类状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("根据id修改商品分类的禁用状态 updateStatusDto={}", updateStatusDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		mdcProductCategoryService.updateMdcCategoryStatusById(updateStatusDto, loginAuthDto);
		return WrapMapper.ok();
	}

	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "编辑商品分类")
	@LogAnnotation
	public Wrapper saveCategory(@ApiParam(name = "saveCategory", value = "编辑商品分类") @RequestBody MdcEditCategoryDto mdcCategoryAddDto) {
		MdcProductCategory mdcCategory = new MdcProductCategory();
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		BeanUtils.copyProperties(mdcCategoryAddDto, mdcCategory);
		mdcProductCategoryService.saveMdcCategory(mdcCategory, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 根据id删除商品分类
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据id删除商品分类")
	@LogAnnotation
	public Wrapper<Integer> deleteMdcCategoryById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id) {
		logger.info(" 根据id删除商品分类 id={}", id);
		// 判断此商品分类是否有子节点
		boolean hasChild = mdcProductCategoryService.checkCategoryHasChildCategory(id);
		if (hasChild) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "此商品分类含有子商品分类, 请先删除子商品分类");
		}

		int result = mdcProductCategoryService.deleteByKey(id);
		return super.handleResult(result);
	}
	
	@PostMapping(value = "/queryProductCategoryListByPid/{pid}")
    @ApiOperation(httpMethod = "POST", value = "根据pid查询商品分类集合")
	public Wrapper<PageInfo<MdcProductCategory>> queryProductCategoryListByPid(@ApiParam(name = "pid", value = "商品分类父id") @PathVariable Long pid,
	        @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
	    MdcProductCategory query = new MdcProductCategory();
	    query.setPid(pid == null ? 0L : pid);
	    query.setPageNum(pageNum);
	    query.setPageSize(pageSize);
	    PageHelper.startPage(query.getPageNum(), query.getPageSize());
	    query.setOrderBy("update_time desc");
        
	    List<MdcProductCategory> categoryVoList = mdcProductCategoryService.getProductCategoryListWithPage(query);
	    return WrapMapper.ok(new PageInfo<>(categoryVoList));
	}
	
	/**
           * 根据id修改商品分类的禁用状态
     *
     * @return the wrapper
     */
    @PostMapping(value = "/modifyNavStatus")
    @ApiOperation(httpMethod = "POST", value = "")
    @LogAnnotation
    public Wrapper updateMdcCategoryNavStatus(@RequestParam String ids, @RequestParam Integer navStatus) {
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        MdcProductCategory update = new MdcProductCategory();
        for (String id : ids.split(",")) {
            update.setId(Long.valueOf(id));
            update.setNavStatus(navStatus);
            update.setUpdateInfo(loginAuthDto);
            mdcProductCategoryService.updateMdcCategoryById(update, loginAuthDto);
        }
        
        return WrapMapper.ok();
    }
    
    /**
     * 根据id修改商品分类的禁用状态
    *
    * @return the wrapper
    */
    @PostMapping(value = "/modifyShowStatus")
    @ApiOperation(httpMethod = "POST", value = "")
    @LogAnnotation
    public Wrapper updateMdcCategoryShowStatus(@RequestParam String ids, @RequestParam Integer showStatus) {
      LoginAuthDto loginAuthDto = getLoginAuthDto();
      MdcProductCategory update = new MdcProductCategory();
      for (String id : ids.split(",")) {
          update.setId(Long.valueOf(id));
          update.setShowStatus(showStatus);
          update.setUpdateInfo(loginAuthDto);
          mdcProductCategoryService.updateMdcCategoryById(update, loginAuthDto);
      }
      
      return WrapMapper.ok();
    }
}