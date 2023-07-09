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
public class ReviewDto {

	// 필드
	private int reviewNo; // 리뷰 번호
	private String reviewId; // 리뷰 작성자 아이디
	private int reviewPaymentNo; // 리뷰 결제 번호
	private String reviewTitle; // 리뷰 제목
	private String reviewContent; // 리뷰 내용
	private Date reviewWritetime; // 리뷰 작성 시간
	private Date reviewUpdatetime; // 리뷰 수정 시간
	private double reviewGood; // 리뷰 별점
	
	// 리뷰 비활성화를 위한 필드
	// - getter 메소드가 isProductInactive() 형태
	private String reviewInactive; // 리뷰 비활성화 여부 -> true일 때 char로 변환하여 DB에 등록되도록
}
