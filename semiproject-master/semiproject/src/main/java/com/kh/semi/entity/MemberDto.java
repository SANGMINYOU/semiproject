package com.kh.semi.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberDto {
	private String memberId, memberPw, memberName, memberEmail;
	private String memberTel, memberPost, memberBaseAddress;
	private String memberDetailAddress, memberGender, memberGrade;
	private String memberBirth;
	private int memberPoint;
	private Date memberJoindate, memberLogindate;
	
}
