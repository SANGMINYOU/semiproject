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
public class ProductDetailVO {

	// 리뷰 작성시 해당 페이지에 상품 정보와 이미지를 표시하기 위함
	
	// 필드
	private int productNo; // 상품 번호
	private String productName; // 상품 이름
	private int productPrice; // 상품 가격
	private Double productGood; // 상품 	별점
	private boolean productInactive; // 상품 비활성화
	private int productAttachmentNo; // 상품 첨부파일
}
