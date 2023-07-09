package com.kh.semi.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CouponUseVO {
	//필드
	private String CouponName;
	private int CouponDiscount;
	private String CouponInfo;
	private Date CouponEnddate;
	private int CouponIssue;
	

}
