package com.kh.semi.entity;

import java.sql.Date;

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
public class ProductDto {

	// 필드
	private int productNo; // 상품 번호 - 시퀀스
	private int categoryHighNo; // 상위 카테고리 번호
	private int categoryLowNo; // 하위 카테고리 번호
	private String productName; // 상품 이름
	private int productPrice; // 상품 가격
	private String productInformation; // 상품 정보
	private int productInventory; // 상품 재고
	private double productGood; // 상품 별점 
	private Date productRegisttime; // 상품 등록일
	private Date productUpdatetime; // 상품 수정일
	
	// 상품 비활성화를 위한 필드
	// - getter 메소드가 isProductInactive() 형태
	private boolean productInactive; // 상품 비활성화 여부 -> true일 때 char로 변환
}
