package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.CouponListDto;

public interface CouponListDao {
	//관리자가 쿠폰 발급 및 관리 
	
	//1. 쿠폰  등록
	void insert(CouponListDto couponListDto);
	
	//2. 쿠폰 정보 수정(쿠폰번호로 수정)
	boolean update(CouponListDto couponListDto);
	
	//3. 쿠폰 리스트 조회(쿠폰번호로 조회)
	List<CouponListDto> selectList(int couponListNo);
	
	//4. 쿠폰 삭제(쿠폰번호로 삭제)
	boolean delete(int couponListNo);

}
