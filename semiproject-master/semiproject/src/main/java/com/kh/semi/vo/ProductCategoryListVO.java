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
public class ProductCategoryListVO {

	// 필드
	private int categoryHighNo;  // 상위 카테고리 번호
	private String categoryHighName;  // 상위 카테고리 이름
	private String categoryHighSub;
	
	private int categoryLowNo; 			// 하위 카테고리 번호
	private String categoryLowName; 	// 하위 카테고리 이름
	
	// 메소드
	// 구독 상품인지 여부를 반환
	public boolean isCateghoryHighSub() {
		// categoryHighSub의 값이 null이 아니라면 true를 반환하도록
		return categoryHighSub != null;
	};
}
