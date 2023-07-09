package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.NoticeDto;
import com.kh.semi.vo.NoticeListSearchVO;

public interface NoticeDao {
	//공지 게시판
	
	//추상 메소드 - 공지글 등록(INSERT)
	void write(NoticeDto noticeDto);
	
	// 추상 메소드 - 공지 등록을 위한 시퀀스 번호 반환
	int noticeSequence();
	
	// 추상 메소드 - 문의글 통합 조회(SELECT) 관리자용
	// - 통합 조회
	List<NoticeDto> selectList(NoticeListSearchVO noticeListSearchVO);	
	// - 전체 조회
	List<NoticeDto> list(NoticeListSearchVO noticeListSearchVO);
	// - 검색 조회
	List<NoticeDto> search(NoticeListSearchVO noticeListSearchVO);	
	
	//검색과 목록의 총 데이터 개수를 구하는 메소드(마지막 페이지 번호, 페이징)
	// 조회 유형에 따른 총 조회 결과 갯수
	int count(NoticeListSearchVO noticeListSearchVO);
	// 전체 조회 시 총 결과 갯수
	int searchCount(NoticeListSearchVO noticeListSearchVO);
	// 검색 조회시 총 조회 결과 갯수
	int listCount(NoticeListSearchVO noticeListSearchVO);
	
	// 추상 메소드 - 공지글 상세(DETAIL)
	NoticeDto selectOne(int noticeNo);
	
	//추상 메소드 - 공지글 수정(UPDATE)
	boolean update(NoticeDto noticeDto);
	
	// 추상 메소드 오버라이딩 - 공지글 삭제(DELETE)
	boolean delete(int noticeNo);
	
	// 추상 메소드  - 공지글 조회수 증가(UPDATE)
	NoticeDto read(int noticeNo);	
	boolean updateReadcount(int noticeNo);	


}
