/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcAlipayServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

//import com.alipay.api.AlipayResponse;
//import com.alipay.api.response.AlipayTradePrecreateResponse;
//import com.alipay.demo.trade.config.Configs;
//import com.alipay.demo.trade.model.ExtendParams;
//import com.alipay.demo.trade.model.GoodsDetail;
//import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
//import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
//import com.alipay.demo.trade.service.AlipayTradeService;
//import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
//import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.paascloud.BigDecimalUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.generator.UniqueIdGenerator;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.PtcPayInfoMapper;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.constant.PtcApiConstant;
import com.paascloud.provider.model.domain.OmcOrder;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.model.domain.PtcPayInfo;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.attachment.OptUploadFileByteInfoReqDto;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import com.paascloud.provider.service.OmcOrderDetailService;
import com.paascloud.provider.service.OmcOrderService;
import com.paascloud.provider.service.OpcOssService;
import com.paascloud.provider.service.PtcAlipayService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The class Ptc alipay service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class PtcAlipayServiceImpl implements PtcAlipayService {


	@Value("${paascloud.alipay.callback.url}")
	private String alipayCallbackUrl;
	@Value("${paascloud.alipay.qrCode.pcPath}")
	private String qrCodePcPath;
	@Value("${paascloud.alipay.qrCode.qiniuPath}")
	private String qrCodeQiniuPath;

	/**
	 * Pay wrapper.
	 *
	 * @param orderNo      the order no
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper pay(String orderNo, LoginAuthDto loginAuthDto) {
		
		return WrapMapper.ok();
	}

	/**
	 * 简单打印应答
	 * private void dumpResponse(AlipayResponse response)
	 */
	private void dumpResponse() {
		/*
		if (response != null) {
			log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
			if (StringUtils.isNotEmpty(response.getSubCode())) {
				log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
						response.getSubMsg()));
			}
			log.info("body:" + response.getBody());
		}*/
	}

	@Override
	public Wrapper aliPayCallback(Map<String, String> params) {
		log.info("支付宝回调. - aliPayCallback. params={}", params);

		return WrapMapper.ok();
	}
}
