package com.kh.semi.entity;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CouponUseDto {
	private int couponUseNo;
	private int couponUseOrderNo;
	private int couponIssueNo;
	private Date couponUseDate;
	
	
}
