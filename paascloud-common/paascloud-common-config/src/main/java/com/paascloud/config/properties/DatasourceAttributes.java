package com.paascloud.config.properties;

import lombok.Data;

@Data
public class DatasourceAttributes {
    
    private String appId;

	private String name;
	
	private String host;
	
	private String port;
	
	private String username;
	
	private String password;
	
	private String initstance;
}
