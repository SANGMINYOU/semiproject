package com.kh.semi.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class ReviewListSearchVO {
	
	// 필드
	private String type;		// 분류
	private String keyword;		// 검색어
	
	// 검색 조회인지 전체 조회인지 판정하여 boolean 반환
	public boolean isSearch() {
		return type != null && keyword != null;
	}
	
	// 페이징 
	// 1. 현재 페이지에 표시될 리뷰의 시작과 끝 rownum 관련 
	// 필드
	private int pageNow = 1; // 현재 페이지 번호 (기본값을 1로 한다)
	private int countReviewNow = 10; // 현재 페이지에 표시될 리뷰의 수
	
	// 메소드
	// - 현재 페이지에 표시될 리뷰의 시작 rownum
	@ToString.Include
	public int rownumStart() {		
		// 한 페이지에 리뷰을 10개씩(countReviewNow = 10) 표시한다고 할 때
		// 11번 페이지(pageNow = 11)에 표시될 리뷰의 시작 rownum인 101은
		// 마지막 rownum인 110(pageNow * countProduct)에서 10(countReviewNow)을 뺀 값에서 1을 더한 값이다
		// ** 101에서 110 사이의 숫자의 갯수는 110(pageNow * countProduct) - 101(시작 rownum) + 1 = 10(countReviewNow) 이다
		return rownumEnd() - countReviewNow + 1;
	}
	
	// - 현재 페이지에 표시될 리뷰의 마지막 rownum
	@ToString.Include
	public int rownumEnd() {
		// 한 페이지에 리뷰을 10개씩(countReviewNow = 10) 표시한다고 할 때
		// 11번 페이지(pageNow = 11)에 표시될 리뷰의 마지막 rownum은 110(pageNow * countReviewNow)이다
		return pageNow * countReviewNow;
	}
	
	// 2. 전체 페이지에 대한 페이지 블럭의 시작과 끝 번호 관련
	// 필드
	private int countTotalReview;	// 등록된 리뷰의 총 수 - 조회한 총 리뷰 수(count(*))를 값으로 가질 예정
	private int countBlockNow = 5; // 현재 페이지에 표시될 페이지 블럭의 수
	
	// - 전체 페이지에 대한 첫 페이지 블럭 번호
	@ToString.Include
	public int blockFirst() {
		// 첫 페이지 블럭 번호는 1로 한다
		return 1;
	}
	
	// - 리뷰 존재하는 페이지 블럭의 마지막 번호 (= 마지막 페이지 번호)
	@ToString.Include
	public int countBlock() {
		// 
		return (countTotalReview + (countReviewNow - 1)) / countReviewNow;
	}
	
	// - 전체 페이지에 마지막 페이지 블럭 번호
	@ToString.Include
	public int blockLast() {
		return countBlock();
	}
	
	// 3. 현재 페이지에 표시될 페이지 블럭의 시작과 끝 페이지 블럭 번호 관련
	// 메소드
	// - 현재 페이지에서 페이지 블럭의 시작 번호
	@ToString.Include
	public int blockStart() {
		return (pageNow - 1) / countBlockNow * countBlockNow + 1;
	}
	
	// - 현재 페이지에서 페이지 블럭의 끝 번호
	@ToString.Include
	public int blockEnd() {
		int value = blockStart() + countBlockNow - 1;
		return Math.min(value, blockLast());
	}
	
	// 4. 페이지 이동시 해당 페이지에서 표시할 페이지 블럭 번호 관련
	// 메소드
	// - 이전 블럭의 번호 반환
	@ToString.Include
	public int blockPrev() {
		return blockStart() - 1;
	}
	
	// - 다음 블럭의 번호 반환
	@ToString.Include
	public int blockNext() {
		return blockEnd() + 1;
	}
	
	// - 현재 블럭이 첫 번째 블럭인지 판정
	@ToString.Include
	public boolean isFirst() {
		return pageNow == 1;
	}
	
	// - 현재 블럭이 마지막 블럭인지 판정
	@ToString.Include
	public boolean isLast() {
		return blockEnd() == blockLast();
	}
	
	// - 이전 번호의 블럭이 있는지 판정
	@ToString.Include
	public boolean hasPrev() {
		return blockStart() > 1;
	}
	
	// - 다음 번호의 블럭이 있는지 판정
	@ToString.Include
	public boolean hasNext() {
		return blockEnd() < blockLast();
	}
	
	// 검색 중에도 페이징이 유지될 수 있도록 Query String 생성
	@ToString.Include
	public String queryString() {
		if(isSearch()) {
			return "countReviewNow=" + countReviewNow + "&type=" + type + "&keyword=" + keyword;
		}
		else {
			return "countReviewNow=" + countReviewNow;
		}
	}
}
