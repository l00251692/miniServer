package com.paascloud.config.properties;
import lombok.Data;
import java.util.List;
import java.util.Map;


/**
 * The class Qiniu oss properties.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class DatabaseProperties {
	
	private String driverClassName ;
	
	private int initialSize ;
	
	private int minIdle ;
	
	private int maxActive ;
	
	private int timeBetweenEvictionRunsMillis;
	
	private int minEvictableIdleTimeMillis;
	
	private boolean testWhileIdle;
	
	private boolean testOnBorrow;
	
	private boolean testOnReturn;
	
	private DatasourceAttributes[] druid = {};
	
	private String defaultName4Uac;
	
	private String defaultName4Mdc;
	
	private String defaultName4Omc;
	
	private String defaultName4Opc;
	
}
