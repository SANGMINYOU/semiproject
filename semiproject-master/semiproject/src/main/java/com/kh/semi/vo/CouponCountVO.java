package com.kh.semi.vo;

import java.sql.Date;

import com.kh.semi.entity.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CouponCountVO {
	private String memberId, memberPw, memberName, memberEmail;
	private String memberTel, memberPost, memberBaseAddress;
	private String memberDetailAddress, memberGender, memberGrade;
	private String memberBirth;
	private int memberPoint;
	private Date memberJoindate, memberLogindate;
	private int cnt; //쿠폰갯수가 포함된 VO

}
