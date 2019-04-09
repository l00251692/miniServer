package com.paascloud.provider.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paascloud.config.properties.PaascloudProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DynamicDSConfiguration {

	@Resource
	private PaascloudProperties paascloudProperties;
	
	@Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DynamicDataSource.class)
	DataSource dataSource (){
		DynamicDataSource ds = new DynamicDataSource();
		ds.setDsProperties(paascloudProperties);
		return ds;
    }
}
