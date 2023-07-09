package com.kh.semi.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  @NoArgsConstructor @AllArgsConstructor @Builder
public class InquireReplyVO {
//	inquireReply
	private int inquireReplyNo;
	private String inquireReplyId;
	private int inquireReplyOriginNo;
	private Date inquireReplyWritedate;
	private Date inquireReplyUpdatedate;
	private String inquireReplyContent;
	private String memberName;
	private String memberGrade;
}
