package com.kh.semi.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @Builder

public class NoticeDto {
	private int noticeNo;
	private String noticeId;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeWritedate;
	private Date noticeUpdatedate;
	private int noticeRead;	

}
