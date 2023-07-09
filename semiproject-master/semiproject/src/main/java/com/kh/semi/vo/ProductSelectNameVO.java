package com.kh.semi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductSelectNameVO {
	
	private String productName;
	
	//상품명만 나오게 toString 재정의
	@Override
	public String toString() {
		return ""+this.getProductName();
	}
	

}
