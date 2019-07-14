package com.paascloud.provider.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Table;

import com.paascloud.core.mybatis.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "pc_omc_order")
public class OmcOrderSummary extends BaseEntity {

	private static final long serialVersionUID = 6763112370762865905L;
	
	private String userId;
	
	private Integer cancel;
	
	private Integer unpaid;
	
	private Integer unship;
	
	private Integer unrecv;
	
	private Integer uncomment;
	
	private Integer total;	
}
