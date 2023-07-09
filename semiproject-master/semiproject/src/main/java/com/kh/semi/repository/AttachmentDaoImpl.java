package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.AttachmentDto;


//첨부파일 조회 등록 기능
@Repository
public class AttachmentDaoImpl implements AttachmentDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//전체 조회를 위한 mapper
	private RowMapper<AttachmentDto> mapper  = (rs, rowNum) -> {
		AttachmentDto dto = new AttachmentDto();
		dto.setAttachmentNo(rs.getInt("attachment_no"));
		dto.setAttachmentName(rs.getString("attachment_name"));
		dto.setAttachmentType(rs.getString("attachment_type"));
		dto.setAttachmentSize(rs.getLong("attachment_size"));
		dto.setAttachmentDate(rs.getDate("attachment_date"));
		return dto;
	};
	//단일조회를 위한 mapper
	private ResultSetExtractor<AttachmentDto> extractor = (rs) -> {
			AttachmentDto dto = new AttachmentDto();
			if(rs.next()) {
				dto.setAttachmentNo(rs.getInt("attachment_no"));
				dto.setAttachmentName(rs.getString("attachment_name"));
				dto.setAttachmentType(rs.getString("attachment_type"));
				dto.setAttachmentSize(rs.getLong("attachment_size"));
				dto.setAttachmentDate(rs.getDate("attachment_date"));
				return dto;
			}else {
				return null;
			}
	}; 

	
	//시퀀스 번호를 발급받기위한 메소드
	@Override
	public int sequence() {
		String sql = "select attachment_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}

	
	//등록 구문
	@Override
	public void insert(AttachmentDto attachmentDto) {
		String sql = "insert into attachment("
				+ "attachment_no, attachment_name,"
				+ "attachment_type, attachment_size"
			+ ") values(?, ?, ?, ?)";
				Object[] param = {
				attachmentDto.getAttachmentNo(), 
				attachmentDto.getAttachmentName(),
				attachmentDto.getAttachmentType(),
				attachmentDto.getAttachmentSize()
				};
				jdbcTemplate.update(sql, param);
						
		}

	//첨부파일 조회
	@Override
	public List<AttachmentDto> selectList() {
		String sql = "select * from attachment";
		return jdbcTemplate.query(sql, mapper);
	}

	//첨부파일 단일 조회
	@Override
	public AttachmentDto selectOne(int attachmentNo) {
		String sql = "select * from attachment where attachment_no = ?";
		Object[] param = {attachmentNo};
		return jdbcTemplate.query(sql, extractor, param);
	}

	//상품 첨부파일(product_attachment) 삽입기능
	@Override
	public void productConnectAttachment(int productNo, int attachmentNo) {
		String sql = "insert into product_attachment VALUES(?, ?)";
		Object[] param = {productNo, attachmentNo};
		jdbcTemplate.update(sql, param);
		
	}
	
	//상품 설명(product_attachment) 삽입기능
		@Override
		public void explainConnectAttachment(int productNo, int attachmentNo) {
			String sql = "insert into product_explain VALUES(?, ?)";
			Object[] param = {productNo, attachmentNo};
			jdbcTemplate.update(sql, param);
			
		}

	//뷰 조회 구문(product_attachment_view)
	//상품 원본 번호로 조회
	@Override
	public List<AttachmentDto> selectProductAttachmentList(int productOriginNo) {
		String sql = "select * from product_attachment_view "
				+ "where product_origin_no = ?";
		Object[] param = {productOriginNo};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
	//뷰 조회 구문(product_explain_view)
	//상품 원본 번호로 조회
	@Override
	public List<AttachmentDto> selectProductExplainList(int productOriginNo) {
		String sql = "select * from product_explain_view "
				+ "where product_explain_no = ?";
		Object[] param = {productOriginNo};
		return jdbcTemplate.query(sql, mapper, param);
	}


	// 추상 메소드 - 문의 게시판 첨부파일 업로드 기록 등록(INSERT)
	@Override
	public void inquireConnectAttachment(int inquireNo, int attachmentInquireNo) {
		String sql = "insert into inquire_attachment(inquire_attachment_origin_no, inquire_attachment_no) values(?, ?)";
		Object[] param = new Object[] {inquireNo, attachmentInquireNo};
		jdbcTemplate.update(sql, param);
	}

	// 추상 메소드 - 문의 게시판 첨부파일 업로드 기록 조회(SELECT)
	@Override
	public List<AttachmentDto> selectInquireAttachmentList(int inquireAttachmentOriginNo) {
		// 문의글 원본 번호(inquireAttachmentOriginNo)로 해당 문의글 번호에 연결된 첨부파일 번호 조회(상세 조회)
		String sql = "select A.* from inquire_attachment I inner join attachment A on I.inquire_attachment_no = A.attachment_no where inquire_attachment_origin_no = ?";
		Object[] param = new Object[] {inquireAttachmentOriginNo};
		return jdbcTemplate.query(sql, mapper, param);
	}


	//리뷰 이미지 업로드 등록
	@Override
	public void reviewConnectAttachment(int reviewAttachmentOriginNo, int attachmentNo) {
		String sql = "insert into review_attachment values(?, ?)";
		Object[] param = {reviewAttachmentOriginNo, attachmentNo};
		jdbcTemplate.update(sql, param);
		
	}


	@Override
	public List<AttachmentDto> selectReviewAttahmentList(int reviewAttachmentOriginno) {
		String sql = "select A.* from review_attachment R inner join attachment A on r.review_attachment_no = A.attachment_no where r.review_attachment_origin_no= ?";
		Object[] param = {reviewAttachmentOriginno};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
}