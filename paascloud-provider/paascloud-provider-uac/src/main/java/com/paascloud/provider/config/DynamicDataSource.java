package com.paascloud.provider.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.paascloud.ThreadLocalMap;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.config.properties.DatasourceAttributes;
import com.paascloud.config.properties.PaascloudProperties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.WeakHashMap;

@Slf4j
//@Configuration
//public class DynamicDataSource extends AbstractRoutingDataSource {
public class DynamicDataSource extends AbstractDataSource {
    
	private Map<String, DataSource> dataSourceMap = new WeakHashMap<String, DataSource>();

	
	public void setDsProperties(PaascloudProperties paascloudProperties) {
		
        log.info("DynamicDataSource: paascloudProperties is starting");
        
        DatasourceAttributes druid[] = paascloudProperties.getDatabase().getDruid();
           
        for (DatasourceAttributes iterator : druid) { 
        	
        	if(iterator.getInitstance() != null && iterator.getInitstance().indexOf("uac")!=-1)
        	{ 
        		log.info("******************************************set dynamic database:");
        		DataSource dataSource = druidDataSource(paascloudProperties, iterator);
        		dataSourceMap.put(iterator.getName(),dataSource);
        	}
        }
	}
	
	
    /**
     * 此处数据库信配置,可以来源于redis等,然后再初始化所有数据源
     * 重点说明:一个DruidDataSource数据源,它里面本身就是线程池了,
     * 所以我们不需要考虑线程池的问题
     * @param no
     * @return
     */
    public DataSource druidDataSource(PaascloudProperties paascloudProperties, DatasourceAttributes attributes){
    	
        DruidDataSource datasource = new DruidDataSource();
        
        datasource.setUrl("jdbc:mysql://" +attributes.getHost() + ":" + attributes.getPort() + "/" + attributes.getName() + "?characterEncoding=utf8&useSSL=false");
        datasource.setUsername(attributes.getUsername());
        datasource.setPassword(attributes.getPassword());
        datasource.setDriverClassName(paascloudProperties.getDatabase().getDriverClassName());
        datasource.setInitialSize(paascloudProperties.getDatabase().getInitialSize());
        datasource.setMinIdle(paascloudProperties.getDatabase().getMinIdle());
        datasource.setMaxActive(paascloudProperties.getDatabase().getMaxActive());
        //datasource.setDbType("com.alibaba.druid.pool.DruidDataSource");
        datasource.setMaxWait(60000);
        datasource.setTimeBetweenEvictionRunsMillis(paascloudProperties.getDatabase().getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(paascloudProperties.getDatabase().getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery("SELECT 1 FROM DUAL");
        datasource.setTestWhileIdle(paascloudProperties.getDatabase().isTestWhileIdle());
        datasource.setTestOnBorrow(paascloudProperties.getDatabase().isTestOnBorrow());
        datasource.setTestOnReturn(paascloudProperties.getDatabase().isTestOnReturn());
        
        try {
            datasource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return datasource;
    }
    
    @Override
	public Connection getConnection() throws SQLException {
		String currentName = DataSourceHolder.getDataSource();
		if (null == currentName) {
		    currentName = (String) ThreadLocalMap.get(GlobalConstant.Sys.APP_ID);
		}
		
//		if (null == currentName) {
//		    currentName = "exampleId1234";
//		}
		
		DataSource currentDataSource = dataSourceMap.get(currentName);
		if(currentDataSource == null) {
			throw new SQLException("there is no datasource configuration for the organization with name " + currentName);
		}
		return currentDataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
}