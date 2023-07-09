package com.kh.semi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewVO {

	// 필드
	private String reviewId; // 리뷰 작성자
	private int reviewNo; // 리뷰 번호
	private int reviewPaymentNo; // 리뷰 결제 정보
	private String reviewTitle; // 리뷰 제목
	private String reviewContent; // 리뷰 내용
	private int reviewGood; // 리뷰 별점
	private int paymentProductNo; // 결제 상품의 상품 번호
}
