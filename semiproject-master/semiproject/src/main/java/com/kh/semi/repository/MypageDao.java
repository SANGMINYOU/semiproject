package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.vo.MypagePaymentInfoVO;

public interface MypageDao {
	List<MypagePaymentInfoVO> selectMyPaymentInfo(String memberId);
}
