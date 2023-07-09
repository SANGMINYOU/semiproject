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
public class CategoryHighDto {

	// 필드
	private int categoryHighNo;  // 상위 카테고리 번호
	private String categoryHighName;  // 상위 카테고리 이름
}
