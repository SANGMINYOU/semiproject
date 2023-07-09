package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.PaymentDto;
import com.kh.semi.vo.PaymentVO;

public interface PaymentDao {
	//결제(&통계)
	
	//주문번호 불러오기
	int sequence();
	
	//1. 등록(추가)
	void insert(PaymentDto paymentDto);
	//1-2. 등록(추가)
	void insert(PaymentVO payment);
	
	//2. 수정(상품번호로 조회 후 수량/가격을 변경)
	boolean update(PaymentDto paymentDto); 
	
	//3. 조회
	List<PaymentDto> selectList();
	
	//3-1 조회(주문번호로 조회)
	PaymentDto selectOne(int orderNo);
	
	//4. 삭제(주문번호로 조회 후 삭제)
	// 나중에 넘어오는 값 확인 후 매개변수 수정 예정
	boolean delete(int orderNo); 
	
	//4-1. 삭제(주문번호와 상품번호로 조회 후 삭제)
	// 나중에 넘어오는 값 확인 후 매개변수 수정 예정 
	boolean delete(int orderNo, int productNo);
}
