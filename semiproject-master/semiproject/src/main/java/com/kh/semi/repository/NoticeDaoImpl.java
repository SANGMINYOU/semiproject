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

import com.kh.semi.entity.NoticeDto;
import com.kh.semi.vo.NoticeListSearchVO;

@Repository
public class NoticeDaoImpl implements NoticeDao{
	
	// 의존성 주입
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 추상 메소드 오버라이딩 - 공지 등록을 위한 시퀀스 번호 반환
	@Override
	public int noticeSequence() {
		String sql = "select notice_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);		
	}
	
	//추상 메소드 오버라이딩 - 공지글 등록(INSERT)
	@Override
	public void write(NoticeDto noticeDto) {
		String sql = "insert into notice "
					+ "(notice_no, notice_id, notice_title, notice_content, "
					+ "notice_read) "
						+ "values(?, ?, ?, ?, ?)";
		Object[] param = {
					noticeDto.getNoticeNo(), noticeDto.getNoticeId(),
					noticeDto.getNoticeTitle(), noticeDto.getNoticeContent(),
					noticeDto.getNoticeRead()};
		jdbcTemplate.update(sql, param);		
	}
	
	//추상 메소드 오버라이딩 - 공지글 수정(UPDATE)
	@Override
	public boolean update(NoticeDto noticeDto) {
		String sql = "update notice set notice_title =? , notice_content=? where notice_no=?";
		Object[] param = {
					noticeDto.getNoticeTitle(), noticeDto.getNoticeContent(),
					noticeDto.getNoticeNo()};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	// NoticeDto에 대한 RowMapper
	private RowMapper<NoticeDto> mapper = new RowMapper<NoticeDto>() {
		@Override
		public NoticeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return NoticeDto.builder()
																.noticeNo(rs.getInt("notice_no"))
																.noticeId(rs.getString("notice_id"))
																.noticeTitle(rs.getString("notice_title"))
																.noticeContent(rs.getString("notice_content"))
																.noticeWritedate(rs.getDate("notice_writedate"))
																.noticeUpdatedate(rs.getDate("notice_updatedate"))
																.noticeRead(rs.getInt("notice_read"))
														.build();									
		}
		
	};
	
	// NoticeDto에 대한 ResultSetExtractor
	private ResultSetExtractor<NoticeDto> extractor = new ResultSetExtractor<NoticeDto>() {
		@Override
		public NoticeDto extractData(ResultSet rs) throws SQLException ,DataAccessException {
					if(rs.next()) {
						return NoticeDto.builder()
														.noticeNo(rs.getInt("notice_no"))
														.noticeId(rs.getString("notice_id"))
														.noticeTitle(rs.getString("notice_title"))
														.noticeContent(rs.getString("notice_content"))
														.noticeWritedate(rs.getDate("notice_writedate"))
														.noticeUpdatedate(rs.getDate("notice_updatedate"))
														.noticeRead(rs.getInt("notice_read"))
												.build();			
					}
					else {
						return null;
					}		
			}
	};


	// 추상 메소드 오버라이딩 - 공지글 삭제(DELETE)
	@Override
	public boolean delete(int noticeNo) {
		String sql = "delete notice where notice_no=?";
		Object[] param = {noticeNo};
		return jdbcTemplate.update(sql, param) > 0;
	}

	// 추상 메소드 오버라이딩 - 공지글 조회수(UPDATE)
	@Override
	public NoticeDto read(int noticeNo) {
		this.updateReadcount(noticeNo);
		return this.selectOne(noticeNo);
	}
	
	// 추상 메소드 오버라이딩 - 공지글 조회수 증가(UPDATE)
	@Override
	public boolean updateReadcount(int noticeNo) {
		String sql = "update notice "
													+ "set notice_read = notice_read + 1 "
												+ "where notice_no=?";
		Object[] param = {noticeNo};
		return jdbcTemplate.update(sql, param) > 0;
	}

	// 추상 메소드 오버라이딩 - 공지글 상세(DETAIL)
	@Override
	public NoticeDto selectOne(int noticeNo) {
		String sql = "select * from notice where notice_no=?";
		Object[] param = {noticeNo};
		return jdbcTemplate.query(sql, extractor,param);
	}
	
	//추상 메소드 오버라이딩 - 공지글 통합 조회(SELECT)
	//통합 조회
	@Override
	public List<NoticeDto> selectList(NoticeListSearchVO noticeListSearchVO) {

		//검색 조회인지 판정
		if(noticeListSearchVO.isSearch()) { //검색조회라면
			return search(noticeListSearchVO); //검색조회 실행
		}
		else { //검색 조회가 아니라면 (전체 조회라면)
		 return list(noticeListSearchVO); //전체조회 실행
		}
	}

	//검색 조회
	@Override
	public List<NoticeDto> search(NoticeListSearchVO noticeListSearchVO) {
		String sql = "select * from ("
					+ "select rownum rn, TMP.* from ("
							+ "select * from notice "
							+ "where instr(#1, ?) > 0 "
							+ "order by notice_no desc"
						+ ")TMP"
					+ ") where rn between ? and ?";
		sql = sql.replace("#1", noticeListSearchVO.getType());
		Object[] param = {noticeListSearchVO.getKeyword(), noticeListSearchVO.startRow(), noticeListSearchVO.endRow()};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
	//전체 조회
	@Override
	public List<NoticeDto> list(NoticeListSearchVO noticeListSearchVO) {
		String sql = "select * from ("
						+ "select rownum rn, TMP.* from ("
							+ "select * from notice order by notice_no desc"
							+ ")TMP"
						+ ") where rn between ? and ?";
		Object[] param = {noticeListSearchVO.startRow(), noticeListSearchVO.endRow()};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
	//전체 조회와 검색 조회 시 조회 결과의 총 갯수(마지막 게시글의 rownum)
	// - 조회 유형에 따른 총 조회 결과 객수
	@Override
	public int count(NoticeListSearchVO noticeListSearchVO) {
		if(noticeListSearchVO.isSearch()) { //검색 조회일 경우
			return searchCount(noticeListSearchVO); //검색 조회의 조회 결과 총 갯수 반환
		}
		else { // 그렇지 않을 경우(전체 조회)
			return listCount(noticeListSearchVO); //전체 조회의 조회 결과 총 갯수 반환
		}
	}
	
	// - 검색 조회시 조희 결과의 총 갯수
	@Override
	public int searchCount(NoticeListSearchVO noticeListSearchVO) {
		String sql = "select count(*) from notice "
							+"where instr(#1, ?) < 0";
		sql = sql.replace("#1",  noticeListSearchVO.getType());
		Object[] param = {noticeListSearchVO.getKeyword()};
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}
	
	// - 전체 조회시 조회 결과의 총 갯수 , 총 공지 글 수 세기
	@Override
	public int listCount(NoticeListSearchVO noticeListSearchVO) {
		String sql ="select count(*) from notice";
		return jdbcTemplate.queryForObject(sql, int.class);
	}

}
