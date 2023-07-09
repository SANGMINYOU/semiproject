package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.CouponDto;
import com.kh.semi.vo.CouponCountVO;
import com.kh.semi.vo.CouponListVO;
import com.kh.semi.vo.CouponUseVO;

public interface CouponDao {
	//회원 개인이 보유하는 쿠폰
	
	//1. 보유쿠폰 추가 (신규 가입 쿠폰)
	void insert(CouponDto couponDto);
	
	//** 추상 메소드 - 오픈기념쿠폰 발급
	void insert(String couponId);
	
	//2. 보유쿠폰 수정(쿠폰번호로 수정)
	boolean update(int couponIssue);
	
	//3-1. 사용한 쿠폰 조회(회원아이디로 조회)
	List<CouponDto> usedCoupon(String memberId);
	//3-2. 미사용 보유쿠폰 조회(회원아이디로 조회)
	List<CouponDto> unUsedCoupon(String memberId);
	//3-3. 보유쿠폰 개수조회(회원아이디로 조회)
	CouponCountVO selectOne(String memberId);
	//3-4. 사용가능한 쿠폰 개수 조회***
	int selectUsable(String memberId);
	
	//4. 보유쿠폰 삭제(쿠폰번호로 삭제)
	boolean delete(int couponNo);
	
	//5. 회원별 쿠폰 리스트 출력
	List<CouponListVO> couponList(String memberId);	
	
	List<CouponCountVO> selectCountList();
	
	//6. 쿠폰 셀렉트박스 생성(주문 페이지에서 사용)
	public List<CouponListVO> selectCoupon(String memberId);
	
	//7. 주문관련 쿠폰 데이터 불러오기
	public List<CouponUseVO> useCoupon(String couponId);
	
	
}
