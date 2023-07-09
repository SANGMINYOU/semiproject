package com.kh.semi.entity;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CouponListDto {
	private int couponListNo;
	private String couponName;
	private int couponDiscount;
	private String couponInfo; 
}
