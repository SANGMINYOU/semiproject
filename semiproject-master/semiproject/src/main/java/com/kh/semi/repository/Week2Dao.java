package com.kh.semi.repository;

import com.kh.semi.entity.Week2Dto;

public interface Week2Dao {
	
	//프라이머리키 생성(조합번호)
	int sequence();
	
	//데이터 등록
	void insert(Week2Dto week2Dto);
}
