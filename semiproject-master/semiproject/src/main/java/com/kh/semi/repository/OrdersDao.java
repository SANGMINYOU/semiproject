package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.OrdersDto;
import com.kh.semi.vo.OrderVO;

public interface OrdersDao {
	
	//시퀀스 미리 생성
	public int sequence();

	//1. 주문 등록
	void insert(OrdersDto ordesDto);
	//2. OrderVO로 주문등록
	void insert(OrderVO ordervo);
	 
	//2. 주문 수정
	//2-1. 배송지 정보를 변경(일단 수취인 이름, 우편번호, 기본주소, 상세주소, 휴대폰, 배송요청사항)
	boolean updateInfo(OrdersDto ordersDto);
	//2-2. 주문상태, 주문상태변경일을 변경
	boolean updateStatus(String orderStatus, int orderNo);
	
	//3. 주문 조회
	//3-1. 전체 주문내역 조회
	List<OrdersDto> selectList();
	//3-2. 주문번호로 검색 후 해당건 조회
	OrdersDto selectOne(int orderNo);
	
	//4. 주문 삭제(주문번호로 검색 후 해당 주문건 삭제)
	boolean delete(int orderNo);
	
}
