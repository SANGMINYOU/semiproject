package com.kh.semi.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CouponListVO {
	
	//필드
	private int CouponNo;
	private String CouponName;
	private int CouponDiscount;
	private String CouponInfo;
	private Date CouponStartdate;
	private Date CouponEnddate;
	private Date CouponUseDate;
	private String CouponValid;

	

	

}
