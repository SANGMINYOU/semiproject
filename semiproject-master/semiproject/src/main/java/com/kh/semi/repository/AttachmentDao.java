package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.AttachmentDto;
import com.kh.semi.vo.ProductAttachmentVO;

//첨부파일 DAO
public interface AttachmentDao {
	//시퀀스 발급
	int sequence();
	//첨부파일 등록
	void insert(AttachmentDto attachmentDto); 
	// 첨부파일 리스트 조회
	List<AttachmentDto> selectList(); 
	//첨부파일 하나만
	AttachmentDto selectOne(int attachmentNo); 
	
	//상품썸네일이미지 삽입 기능
	void productConnectAttachment(int productNo, int attachmentNo);
	//상품 이미지 조회(product_attachment_view)
	public List<AttachmentDto> selectProductAttachmentList(int productOriginNo);
	//상품 설명 이미지 조회(product_explain_view)
	public List<AttachmentDto> selectProductExplainList(int productOriginNo);
	//상품설멍이미지 삽입 기능
	void explainConnectAttachment(int productNo, int attachmentNo);
	
	// 추상 메소드 - 문의 게시판 첨부파일 업로드 기록 등록(INSERT)
	void inquireConnectAttachment(int inquireNo, int attachmentInquireNo);
	
	// 추상 메소드 - 문의 게시판 첨부파일 업로드 기록 조회(SELECT)
	public List<AttachmentDto> selectInquireAttachmentList(int inquireAttachmentOriginNo);
	
	//리뷰첨부파일 연결테이블 삽입기능
	void reviewConnectAttachment(int reviewAttachmentOriginNo, int attachmentNo);
	
	//리뷰 첨부파일 조회
	public List<AttachmentDto> selectReviewAttahmentList(int reviewAttachmentOriginno);
	
}
