package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.InquireReplyDto;
import com.kh.semi.vo.InquireReplyVO;

@Repository
public class InquireReplyDaoImpl implements InquireReplyDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//1:1 문의 댓글 작성기능
	@Override
	public void replyWrite(InquireReplyDto inquireReplyDto) {
		String sql = "insert INTO inquire_reply "
				+ " values(INQUIRE_REPLY_SEQ.nextval,"
				+ " ?, ?,sysdate,sysdate, ?)";
		Object[] param = {inquireReplyDto.getInquireReplyId(),
				inquireReplyDto.getInquireOriginNo(),
				inquireReplyDto.getInquireReplyContent()};
		jdbcTemplate.update(sql, param);
	}

	//1:1 문의 댓글 조회
	//1:1 게시판 원본글을 조회하여 전체출력
	@Override
	public List<InquireReplyVO> selectList(int inquireOriginNo) {
		String sql = "select m.member_name, m.member_grade, r.* from"
				+ " inquire_reply R left OUTER join member M "
				+ "on r.inquire_reply_id=m.member_id where r.inquire_origin_no = ?";
		Object[] param = {inquireOriginNo};
		return jdbcTemplate.query(sql, mapper, param);
	}

	@Override
	public InquireReplyDto selectOne(int inquireReplyNo) {
		String sql = "select * from inquire_reply where inquire_reply_no= ?";
		Object[] param = {inquireReplyNo};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	//1:1문의 댓글 수정기능
	@Override
	public boolean replyEdit(InquireReplyDto inquireReplyDto) {
		String sql = "UPDATE inquire_reply set inquire_reply_content=? where inquire_reply_no=?";
		Object[] param= {inquireReplyDto.getInquireReplyContent(), inquireReplyDto.getInquireReplyNo()};
		return jdbcTemplate.update(sql, param)>0;
	}
	
	//1:1문의 댓글 삭제기능
	@Override
	public boolean replyDelete(int inquireReplyNo) {
		String sql = "delete inquire_reply where inquire_reply_no=?";
		Object[] param = {inquireReplyNo};
		return jdbcTemplate.update(sql,param)>0;
	}
	
	RowMapper<InquireReplyVO> mapper = (rs, idx)->{
		return InquireReplyVO.builder()
				.inquireReplyNo(rs.getInt("inquire_reply_no"))
				.inquireReplyId(rs.getString("inquire_reply_id"))
				.inquireReplyOriginNo(rs.getInt("inquire_origin_no"))
				.inquireReplyWritedate(rs.getDate("inquire_reply_writedate"))
				.inquireReplyUpdatedate(rs.getDate("inquire_reply_updatedate"))
				.inquireReplyContent(rs.getString("inquire_reply_content"))
				.memberName(rs.getString("member_name"))
				.memberGrade(rs.getString("member_grade"))
				.build();
	};
	
	ResultSetExtractor<InquireReplyDto> extractor = (rs)->{
		if(rs.next()) {
			return InquireReplyDto.builder()
					.inquireReplyNo(rs.getInt("inquire_reply_no"))
					.inquireOriginNo(rs.getInt("inquire_origin_no"))
					.inquireReplyId(rs.getString("inquire_reply_id"))
					.inquireReplyWritedate(rs.getDate("inquire_reply_writedate"))
					.inquireReplyUpdatedate(rs.getDate("inquire_reply_updatedate"))
					.inquireReplyContent(rs.getString("inquire_reply_content"))
					.build();
		}
		else return null;
	};

	
	
}
