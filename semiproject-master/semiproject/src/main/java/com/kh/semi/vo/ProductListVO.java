package com.kh.semi.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductListVO {

	// 필드
	private int productNo; // 상품 번호
	private String productName; // 상품명
	private int productPrice; // 상품 가격
	private double productGood; // 상품 별점
	private boolean productInactive; // 상품 비활성화 상태
	private int productAttachmentNo; // 첨부파일 번호
	private boolean categoryHighSub; // 구독 카테고리 여부
}
