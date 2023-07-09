package com.kh.semi.entity;

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
public class CategoryLowDto {

	// 필드
	private int categoryLowNo; // 하위 카테고리 번호
	private int categoryHighNo; // 상위 카테고리 번호 (상위 카테고리 번호 참조)
	private String categoryLowName; // 하위 카테고리 이름
}
