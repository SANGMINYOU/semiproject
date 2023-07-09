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
@ToString
@Builder
public class AttachmentDto {

	//필드
	private int attachmentNo; 		//첨부파일 번호
	private String attachmentName; 	//첨부파일명
	private String attachmentType;	//첨부파일 유형
	private Long attachmentSize;		//첨부파일 크기
	private Date attachmentDate;	//첨부파일 업로드 시각
}
