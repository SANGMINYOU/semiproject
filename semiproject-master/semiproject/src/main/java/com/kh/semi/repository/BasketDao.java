package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.BasketDto;
import com.kh.semi.vo.BasketVO;

public interface BasketDao {
	//장바구니
	//현재 시퀀스 확인
	int sequence();
	
	//1. 상품 추가
	void insert(BasketDto basketDto);
	
	//2-1. 상품수량 수정(장바구니 내 수량버튼으로 수정하는 경우)
	boolean changeCount(BasketDto basketDto);
	//2-2. 상품수량 수정(장바구니 동일상품 확인 후 1씩 증가하는 경우)
	boolean changeOneCount(int productCount, int productNo);
	
	//3-1 조회(회원아이디로 조회)
	List<BasketDto> selectDtoList(String memberId);
	//3-2. VO(회원아이디로 조회)
	List<BasketVO> selectList(String memberId);
	//3-3. VO(회원아이디, 상품번호로 조회)
	BasketVO orderBeforeList(String memberId, int productNo);
	//3-4. 장바구니 내 동일 상품있는지 조회(옵션 없는 단일상품만!)
	BasketDto sameItem(String memberId, int productNo);
	//3-5. 선택상품 조회(장바구니 번호로 조회)
	BasketVO orderList(int basketNo);
	
	//4-1. 삭제(상품 번호로 삭제)
	boolean delete(int productNo);
	//4-2.삭제(장바구니 번호로 삭제)
	boolean clearbasket(int basketNo);
	
	
}
