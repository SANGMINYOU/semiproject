package com.kh.semi.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewPaymentNoVO {

	// 필드
	private Integer orderNo; // 주문 번호
	private Integer reviewNo; // 리뷰 번호
	
	private Integer paymentNo; // 결제 번호
	private Integer paymentProductNo; // 결제 상품 번호
}
