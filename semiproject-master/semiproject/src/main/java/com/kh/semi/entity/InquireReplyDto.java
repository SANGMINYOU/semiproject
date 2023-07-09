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
public class InquireReplyDto {
	private int inquireReplyNo;
	private String inquireReplyId;
	private int inquireOriginNo;
	private Date inquireReplyWritedate;
	private Date inquireReplyUpdatedate;
	private String inquireReplyContent;
	
}
