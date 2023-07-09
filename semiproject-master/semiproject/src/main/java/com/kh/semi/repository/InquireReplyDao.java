package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.InquireReplyDto;
import com.kh.semi.vo.InquireReplyVO;

//1:1문의 댓글 DAO
public interface InquireReplyDao {
	//1:1문의 댓글 관리자 입력기능
	void replyWrite(InquireReplyDto inquireReplyDto);
	
	//1:1문의 댓글 조회
	//원본게시글번호로 거기에 해당하는 전체리스트 조회
	List<InquireReplyVO> selectList(int inquireOriginNo);
	
	//1:1문의 하나만 조회
	//1:1문의번호로
	InquireReplyDto selectOne(int inquireReplyNo);
	
	//1:1문의 댓글 수정
	boolean replyEdit(InquireReplyDto inquireReplyDto);
	
	//1:1문의 댓글 삭제
	boolean replyDelete(int inquireReplyNo);
}
	