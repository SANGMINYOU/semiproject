 package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.CouponUseDto;

public interface CouponUseDao {
	//쿠폰 사용내역
	
	//1. 쿠폰 사용내역 추가
	void insert(int orderNo, Integer couponIssue);
	
	//2. 쿠폰 사용내역 삭제
	boolean delete(int couponUseNo);
	

	
	//3. 쿠폰 사용내역 조회(주문번호로 조회)
	List<CouponUseDto> selectList(int couponUseOrderNo);

}
