package com.kh.semi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.MemberDto;
import com.kh.semi.vo.MemberSearchVO;
import com.kh.semi.vo.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//회원가입
	@Override
	public void insert(MemberDto memberDto) {
		String sql = "insert into member(member_id, member_pw, "
				+ "member_name, member_email, member_tel, member_post, "
				+ "member_base_address, member_detail_address, "
				+ "member_birth, member_gender) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] param = {
				memberDto.getMemberId(), memberDto.getMemberPw(), 
				memberDto.getMemberName(), memberDto.getMemberEmail(), 
				memberDto.getMemberTel(), memberDto.getMemberPost(), 
				memberDto.getMemberBaseAddress(), memberDto.getMemberDetailAddress(), 
				memberDto.getMemberBirth(), memberDto.getMemberGender()
		};
		jdbcTemplate.update(sql, param);
	}
	
	private RowMapper<MemberDto> mapper = new RowMapper<MemberDto>() {
		@Override
		public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberDto dto = new MemberDto();
			dto.setMemberId(rs.getString("member_id"));
			dto.setMemberPw(rs.getString("member_pw"));
			dto.setMemberName(rs.getString("member_name"));
			dto.setMemberEmail(rs.getString("member_email"));
			dto.setMemberTel(rs.getString("member_tel"));
			dto.setMemberPost(rs.getString("member_post"));
			dto.setMemberBaseAddress(rs.getString("member_base_address"));
			dto.setMemberDetailAddress(rs.getString("member_detail_address"));
			dto.setMemberBirth(rs.getString("member_birth"));
			dto.setMemberGender(rs.getString("member_gender"));
			dto.setMemberGrade(rs.getString("member_grade"));
			dto.setMemberPoint(rs.getInt("member_point"));
			dto.setMemberJoindate(rs.getDate("member_joindate"));
			dto.setMemberLogindate(rs.getDate("member_logindate"));
			return dto;
		}
	};

	//회원조회
	@Override
	public List<MemberDto> selectList() {
		String sql = "select * from member";
		return jdbcTemplate.query(sql, mapper);
	}

	//회원검색조회
	@Override
	public List<MemberDto> selectList(String type, String keyword) {
		String sql = "select * from member where instr(#1, ?) > 0 order by #1 asc";
		sql = sql.replace("#1", type);
		Object[] param = {keyword};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
	private ResultSetExtractor<MemberDto> extractor = new ResultSetExtractor<MemberDto>() {
		@Override
		public MemberDto extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()) {
				MemberDto dto = new MemberDto();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberPw(rs.getString("member_pw"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberEmail(rs.getString("member_email"));
				dto.setMemberTel(rs.getString("member_tel"));
				dto.setMemberPost(rs.getString("member_post"));
				dto.setMemberBaseAddress(rs.getString("member_base_address"));
				dto.setMemberDetailAddress(rs.getString("member_detail_address"));
				dto.setMemberBirth(rs.getString("member_birth"));
				dto.setMemberGender(rs.getString("member_gender"));
				dto.setMemberGrade(rs.getString("member_grade"));
				dto.setMemberPoint(rs.getInt("member_point"));
				dto.setMemberJoindate(rs.getDate("member_joindate"));
				dto.setMemberLogindate(rs.getDate("member_logindate"));
				return dto;
			}
			else {
				return null;
			}
		}
	};

	//회원아이디로 조회
	@Override
	public MemberDto selectOne(String memberId) {
		String sql = "select * from member where member_id = ?";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	//회원정보수정
	@Override
	public boolean update(MemberDto memberDto) {
		String sql = "update member set member_name = ?, member_email = ?, "
				+ "member_tel = ?, member_post = ?, member_base_address = ?, "
				+ "member_detail_address = ?, member_grade = ?, member_point = ? "
				+ "where member_id = ?";
		Object[] param = {
				memberDto.getMemberName(), memberDto.getMemberEmail(), 
				memberDto.getMemberTel(), memberDto.getMemberPost(), 
				memberDto.getMemberBaseAddress(), memberDto.getMemberDetailAddress(), 
				memberDto.getMemberGrade(), memberDto.getMemberPoint(), 
				memberDto.getMemberId()
		};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//회원비밀번호변경
	@Override
	public boolean changePassword(String memberId, String memberPw) {
		String sql = "update member set member_pw = ? where member_id = ?";
		Object[] param = {memberPw, memberId};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//회원 삭제
	@Override
	public boolean delete(String memberId) {
		String sql = "delete member where member_id = ?";
		Object[] param = {memberId};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//개인정보변경
	@Override
	public boolean changeInformation(MemberDto memberDto) {
		String sql = "update member set member_name = ?, "
				+ "member_email = ?, member_tel = ?, member_pw = ? "
				+ "where member_id = ?";
		Object[] param = {
				memberDto.getMemberName(), memberDto.getMemberEmail(), 
				memberDto.getMemberTel(), memberDto.getMemberPw(), 
				memberDto.getMemberId()
		};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//로그인시간 갱신
	@Override
	public boolean updateLoginTime(String memberId) {
		String sql = "update member set member_logindate = sysdate where member_id = ?";
		Object[] param = {memberId};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//아이디찾기
	@Override
	public MemberDto findId(String memberName, String memberEmail) {
		String sql = "select * from member where member_name = ? and member_email = ?";
		Object[] param = {memberName, memberEmail};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	//비밀번호찾기
	@Override
	public MemberDto findPw(String memberId, String memberName, String memberTel) {
		String sql = "select * from member where member_id = ? and member_name = ? and member_tel = ?";
		Object[] param = {memberId, memberName, memberTel};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	private RowMapper<MemberVO> listMapper = new RowMapper<MemberVO>() {
		@Override
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MemberVO.builder()
					.memberId(rs.getString("member_id"))
					.memberPw(rs.getString("member_pw"))
					.memberName(rs.getString("member_name"))
					.memberEmail(rs.getString("member_email"))
					.memberTel(rs.getString("member_tel"))
					.memberPost(rs.getString("member_post"))
					.memberBaseAddress(rs.getString("member_base_address"))
					.memberDetailAddress(rs.getString("member_detail_address"))
					.memberBirth(rs.getString("member_birth"))
					.memberGender(rs.getString("member_gender"))
					.memberGrade(rs.getString("member_grade"))
					.memberPoint(rs.getInt("member_point"))
					.memberJoindate(rs.getDate("member_joindate"))
					.memberLogindate(rs.getDate("member_logindate"))
				.build();
		}
	};

	//주문완료 후 포인트(적립금) 차감
	@Override
	public boolean minusUsedPoint(String memberId, int point) {
		String sql="update member set member_point=member_point-? "
				+ "where member_id=?";
		Object[] param= {point, memberId};
		return jdbcTemplate.update(sql, param)>0;
	}

	//주문완료 후 총 결제금액(적립금) 차감
	@Override
	public boolean minusPayPrice(String memberId, int point) {
		String sql="update member set member_point=member_point-? "
				+ "where member_id=?";
		Object[] param= {point, memberId};
		return jdbcTemplate.update(sql, param)>0;
	}

	@Override
	public List<MemberDto> selectList(MemberSearchVO vo) {
		if(vo.isSearch()) {
			return search(vo);
		}
		else {
			return list(vo);
		}
	}

	@Override
	public List<MemberDto> list(MemberSearchVO vo) {
		String sql = "select * from ("
				+ "select rownum rn, TMP.* from("
					+ "select * from member order by member_id asc"
				+ ")TMP"
			+ ") where rn between ? and ?";
		Object[] param = {vo.startRow(), vo.endRow()};
		return jdbcTemplate.query(sql, mapper, param);
	}

	@Override
	public List<MemberDto> search(MemberSearchVO vo) {
		String sql = "select * from ("
				+ "select rownum rn, TMP.* from ("
					+ "select * from member where instr(#1,?) > 0 "
					+ "order by member_id asc"
				+ ")TMP"
			+ ") where rn between ? and ?";
		sql = sql.replace("#1", vo.getType());
		Object[] param = {
				vo.getKeyword(), vo.startRow(), vo.endRow()
		};
		return jdbcTemplate.query(sql, mapper, param);
	}

	@Override
	public int count(MemberSearchVO vo) {
		if(vo.isSearch()) { //검색이라면
			return searchCount(vo);
		}
		else { //목록이라면
			return listCount(vo);
		}
	}

	@Override
	public int searchCount(MemberSearchVO vo) {
		String sql = "select count(*) from member where instr(#1, ?) > 0";
		sql = sql.replace("#1", vo.getType());
		Object[] param = {vo.getKeyword()};
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}

	@Override
	public int listCount(MemberSearchVO vo) {
		String sql = "select count(*) from member";
		return jdbcTemplate.queryForObject(sql, int.class);	}
}
