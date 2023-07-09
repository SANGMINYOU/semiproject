package com.kh.semi.entity;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CouponDto {
	private int couponIssue; 
	private int couponNo;
	private String couponId;
	private Date couponStartdate; 
	private Date couponEnddate; 
	private String couponValid;
	
}
     