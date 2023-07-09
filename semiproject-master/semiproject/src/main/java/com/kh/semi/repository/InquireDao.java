package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.InquireDto;
import com.kh.semi.vo.InquireListSearchVO;

public interface InquireDao {

	// 추상 메소드 - 문의글 등록을 위한 다음 시퀀스 번호 반환
	int nextSequence();
	
	// 추상 메소드 - 문의글 등록(INSERT)
	void writeInquire(InquireDto inquireDto);
	
	// 추상 메소드 - 문의글 통합 조회(SELECT) 관리자용
	// - 통합 조회
	List<InquireDto> selectListInquire(InquireListSearchVO inquireListSearchVO);
	
	// - 검색 조회
	List<InquireDto> searchListInquire(InquireListSearchVO inquireListSearchVO);
	
	// - 전체 조회
	List<InquireDto> allListInquire(InquireListSearchVO inquireListSearchVO);
	
	// 추상 메소드 - 문의글 통합 조회(SELECT) 회원용
	// - 통합 조회
	List<InquireDto> selectListUserInquire(InquireListSearchVO inquireListSearchVO, String loginId);
	
	// - 검색 조회(로그인 중인 회원이 작성한  중 검색 조회
	List<InquireDto> searchListUserInquire(InquireListSearchVO inquireListSearchVO, String loginId);
	
	// - 전체 조회(로그인 중인 회원이 작성한 글만 전체 조회)
	List<InquireDto> allListUserInquire(String loginId);
	
	// 추상 메소드 - 문의글 상세(DETAIL)
	InquireDto selectOneInquire(int inquireNo);
	
	// 추상 메소드 - 문의글 수정(UPDATE)
	boolean updateInquire(InquireDto inquireDto);
	
	// 추상 메소드 - 문의글 삭제(DELETE)
	boolean deleteInquire(int inquireNo);
	
	// 추상 메소드 - 문의글 삭제(UPDATE)
	boolean inactiveInquire(int inquireNo);
	
	//통합 조회시 문의글의 총 갯수
	int countTotalInquire(InquireListSearchVO inquireListSearchVO);
	
	//전체 조회시 문의글의 총 갯수
	int countAllInquire();
	
	//검색조회시 문의글의 총 갯수
	int countSearchInquire(InquireListSearchVO inquireListSearchVO);
	
	//답변작성시에 답변확인 완료 보이게
	boolean updateHasReply(int inquireNo);
}
