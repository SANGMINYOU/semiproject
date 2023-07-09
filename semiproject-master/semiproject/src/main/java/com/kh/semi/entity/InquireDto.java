package com.kh.semi.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InquireDto {

	// 필드
	private int inquireNo;	// 문의글 번호
	private String inquireId;	// 문의글 작성자
	private String inquireTitle;	// 문의글 제목
	private String inquireContent;	// 문의글 내용
	private Date inquireWritetime;	// 문의글 작성일
	private Date inquireUpdatetime; // 문의글 수정일
	
	private boolean inquireHasReply; // 문의글 답변 상태 - 관리자 답변이 달리면 'Y'
	private boolean inquireInactive; // 문의글 삭제 상태 - 회원이 문의글 삭제시 'Y' (되돌리기 불가능)
}
