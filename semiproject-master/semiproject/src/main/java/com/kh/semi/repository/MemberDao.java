package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.MemberDto;
import com.kh.semi.vo.MemberSearchVO;

public interface MemberDao {
	//회원가입
	void insert(MemberDto memberDto);
	
	//회원조회
	List<MemberDto> selectList();
	//회원검색조회
	List<MemberDto> selectList(String type, String keyword);
	
	//회원아이디로 조회
	MemberDto selectOne(String memberId);
	//아이디 찾기
	MemberDto findId(String memberName, String memberEmail);
	//비밀번호 찾기
	MemberDto findPw(String memberId, String memberName, String memberTel);
	
	//회원정보수정
	boolean update(MemberDto memberDto);
	//비밀번호변경
	boolean changePassword(String memberId, String memberPw);
	//회원삭제
	boolean delete(String memberId);
	//개인정보변경
	boolean changeInformation(MemberDto memberDto);
	//로그인시간 갱신
	boolean updateLoginTime(String memberId);
	//주문완료 후 포인트(적립금) 차감
	boolean minusUsedPoint(String memberId, int point);
	//주문완료 후 총 결제금액(적립금) 차감
	boolean minusPayPrice(String memberId, int point);
	
//	List<MemberDtO> selectList(MemberSearchVO vo);
//	List<MemberDtO> list(MemberSearchVO vo);
//	List<MemberDtO> search(MemberSearchVO vo);
	
	List<MemberDto> selectList(MemberSearchVO vo);
	List<MemberDto> list(MemberSearchVO vo);
	List<MemberDto> search(MemberSearchVO vo);
	
	int count(MemberSearchVO vo);
	int searchCount(MemberSearchVO vo);
	int listCount(MemberSearchVO vo);
	
}
