package com.kh.semi.vo;

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
public class ReviewProductVO {

	// 필드
	private String reviewId;
	private Date reviewWritetime;
	private String paymentOption;
	private int reviewGood;
	private String reviewTitle;
	private String reviewContent;
	private int reviewAttachmentNo;
}
