package com.kh.semi.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BasketVO {
	private int basketNo;
	private String basketId;
	private int basketProductNo, basketCountNumber;
	private Date basketAddDate;
	private String basketProductOption;
	private String productName;
	private int productPrice;
	
	//이미지 추가
	private int productOriginNo;
	private int productAttachmentNo;
	
	//리스트 추가
//	public List<BasketVO> orderList;
}
