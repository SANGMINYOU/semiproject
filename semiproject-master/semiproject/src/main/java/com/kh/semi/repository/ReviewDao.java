package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.ReviewDto;
import com.kh.semi.vo.ProductListSearchVO;
import com.kh.semi.vo.ReviewListSearchVO;
import com.kh.semi.vo.ReviewMypageVO;
import com.kh.semi.vo.ReviewProductVO;
import com.kh.semi.vo.ReviewVO;

public interface ReviewDao {

	// 추상 메소드 - 리뷰 등록을 위한 다음 시퀀스 번호 반환
	public int nextSequence();
	
	// 추상 메소드 - 리뷰 작성
	public void writeReview(ReviewDto reviewDto);
	
	// 추상 메소드 - 리뷰 작성 전 상품의 총 별점
	public int scoreBeforeWrite(int productNo);
	
	// 추상 메소드 - 리뷰 작성 전 리뷰의 총 갯수
	public int countBeforeWrite(int productNo);
	
	// 추상 메소드 - 리뷰 등록시 해당 상품의 리뷰 점수 수정(UPDATE)
	public boolean updateProductGood(double insertScore, int productNo);
	
	// 추상 메소드 - 리뷰 등록시 리뷰에 대한 결제 번호를 매개변수로 하여 리뷰 등록 여부 수정(UPDATE)
	public boolean updatePaymentReview(int paymentNo);
	
	// 추상 메소드 - 상품에 표시될 리뷰 조회
	public List<ReviewProductVO> selectProductAllReview(int productNo);
	
	// 추상 메소드 - 내가 작성한 리뷰 목록
	public List<ReviewMypageVO> selectMypageAllReview(String reviewId);
	
	// 추상 메소드 - 결제 번호를 매개변수로 하여 해당 결제에 대한 리뷰 정보 조회(마이페이지의 결제 내역에서 리뷰 수정과 연결하기 위해 반드시 필요함)
	ReviewVO selectOneReviewMyPage(int paymentNo);
	
	// 추상 메소드 오버라이딩 - 리뷰 번호로 리뷰 조회 (상품 후기에서 리뷰 수정 jsp와 연결하기 위해 반드시 필요함)
	ReviewVO selectOneReview(int reviewNo);
	
	ReviewDto selectOneDtoReview(int reviewNo);
	
	//리뷰 작성 수정(UPDATE)
	boolean updateReview(ReviewDto reviewDto);
	
	//리뷰 작성 삭제
	boolean delete(int reviewNo);
	
	//모든 리뷰의 조회
	// 1) 통합 조회
	List<ReviewDto> selectListReview(ReviewListSearchVO reviewListSearchVO);
	
	// 2) 전체 조회
	List<ReviewDto> allListReview(ReviewListSearchVO reviewListSearchVO);
	
	// 3) 검색 조회
	List<ReviewDto> searchListReview(ReviewListSearchVO reviewListSearchVO);
	
	// 모든 리뷰의 총 갯수
	// 1) 통합 조회시 리뷰의 총 갯수
	int countTotalReview(ReviewListSearchVO reviewListSearchVO);
	
	// 2) 전체 조회시 리뷰의 총 갯수
	int countAllReview();
	
	// 3) 검색 조회시 리뷰의 총 갯수
	int countSearchReview(ReviewListSearchVO reviewListSearchVO);
	
	// 추상 메소드 - 리뷰 번호로 회원 아이디 조회(인터셉터용)
	String selectOrderId(int reviewNo);
}
