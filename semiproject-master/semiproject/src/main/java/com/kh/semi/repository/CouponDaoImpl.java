package com.kh.semi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.CouponDto;
import com.kh.semi.vo.CouponCountVO;
import com.kh.semi.vo.CouponListVO;
import com.kh.semi.vo.CouponUseVO;


@Repository
public class CouponDaoImpl implements CouponDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CouponDto> mapper = new RowMapper<CouponDto>() {
		@Override
		public CouponDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			CouponDto couponDto = new CouponDto();
			couponDto.setCouponIssue(rs.getInt("coupon_issue"));		
			couponDto.setCouponNo(rs.getInt("coupon_no"));
			couponDto.setCouponId(rs.getString("coupon_id"));
			couponDto.setCouponStartdate(rs.getDate("coupon_startdate"));
			couponDto.setCouponEnddate(rs.getDate("coupon_enddate"));
			couponDto.setCouponValid(rs.getString("coupon_valid"));
			return couponDto;
		}
	};
	
	//쿠폰 추가 (신규 가입 쿠폰)
	@Override
	public void insert(CouponDto couponDto) {
		String sql = "insert into coupon "
					+ "(coupon_issue, coupon_no, coupon_id, coupon_startdate, "
					+ "coupon_enddate) "
						+ "values (coupon_issue_seq.nextval, 1, ?, sysdate, sysdate+30)";
		Object[] param = {
					couponDto.getCouponId(),
		};
		jdbcTemplate.update(sql, param);		
	}
	
	//** 추상 메소드 오버라이딩 - 오픈기념쿠폰 발급
	@Override
	public void insert(String couponId) {
		String sql = "insert into coupon (coupon_issue, coupon_no, coupon_id, coupon_startdate, coupon_enddate) "
				+ "values (coupon_issue_seq.nextval, 25, ?, sysdate, sysdate+30)";
		Object[] param = new Object[] {couponId};
		jdbcTemplate.update(sql, param);
	}
	
	//쿠폰 옵션 수정
	@Override
	public boolean update(int couponIssue) {
		String sql = "update coupon set coupon_valid ='사용' "					
						+"where coupon_issue=?";
		Object[] param = {couponIssue};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//사용한 쿠폰 조회
	@Override
	public List<CouponDto> usedCoupon(String memberId) {
		String sql = "select * from coupon where coupon_id=? and coupon_valid='사용'";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql, mapper,param);
	}
	
	//미사용 쿠폰 조회
	@Override
	public List<CouponDto> unUsedCoupon(String memberId) {
		String sql = "select * from coupon where coupon_id=? and coupon_valid='미사용'";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql, mapper,param);
	}

	
	//쿠폰 삭제
	@Override
	public boolean delete(int couponNo) {
		String sql = "delete coupon where coupon_no=?";
		Object[] param = {couponNo};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//쿠폰 갯수를 세기 CouponCountVO에 대한 RowMapper
	private RowMapper<CouponCountVO> countMapper = new RowMapper<CouponCountVO>() {		
		@Override
		public CouponCountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CouponCountVO couponCountVO = new CouponCountVO();
			couponCountVO.setMemberId(rs.getString("member_id"));
			couponCountVO.setMemberPw(rs.getString("member_pw"));
			couponCountVO.setMemberName(rs.getString("member_name"));
			couponCountVO.setMemberEmail(rs.getString("member_email"));
			couponCountVO.setMemberTel(rs.getString("member_tel"));
			couponCountVO.setMemberPost(rs.getString("member_post"));
			couponCountVO.setMemberBaseAddress(rs.getString("member_base_address"));
			couponCountVO.setMemberDetailAddress(rs.getString("member_detail.address"));
			couponCountVO.setMemberGender(rs.getString("member_gender"));
			couponCountVO.setMemberGrade(rs.getString("member_grade"));
			couponCountVO.setMemberBirth(rs.getString("member_birth"));
			couponCountVO.setMemberPoint(rs.getInt("member_point"));
			couponCountVO.setMemberLogindate(rs.getDate("member_logindate"));			
			couponCountVO.setCnt(rs.getInt("cnt"));
			return couponCountVO;
		}

		};
		
		private ResultSetExtractor<CouponCountVO> extractor = (rs)->{
			if(rs.next()) {
				CouponCountVO couponCountVO = new CouponCountVO();
				couponCountVO.setMemberId(rs.getString("member_id"));
				couponCountVO.setMemberPw(rs.getString("member_pw"));
				couponCountVO.setMemberName(rs.getString("member_name"));
				couponCountVO.setMemberEmail(rs.getString("member_email"));
				couponCountVO.setMemberTel(rs.getString("member_tel"));
				couponCountVO.setMemberPost(rs.getString("member_post"));
				couponCountVO.setMemberBaseAddress(rs.getString("member_base_address"));
				couponCountVO.setMemberDetailAddress(rs.getString("member_detail_address"));
				couponCountVO.setMemberGender(rs.getString("member_gender"));
				couponCountVO.setMemberGrade(rs.getString("member_grade"));
				couponCountVO.setMemberBirth(rs.getString("member_birth"));
				couponCountVO.setMemberPoint(rs.getInt("member_point"));
				couponCountVO.setMemberLogindate(rs.getDate("member_logindate"));			
				couponCountVO.setCnt(rs.getInt("cnt"));
				return couponCountVO;
			}
			else {
				return null;
			}
		};
		
	@Override 
	public List<CouponCountVO> selectCountList() {
		String sql = "select coupon_id, count(*) cnt from coupon "
						+ "group by coupon_id";
		return jdbcTemplate.query(sql,  countMapper);
	}

	@Override
	public CouponCountVO selectOne(String memberId) {
		String sql ="select m.*, (select COUNT(*) cnt from coupon where coupon_id=?) cnt from coupon c inner join member m on c.coupon_id=m.member_id where m.member_id=?";
		Object[] param = {memberId, memberId};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	
	//쿠폰 리스트 조회를 위한 RowMapper 추가
	private RowMapper<CouponListVO> couponMapper = new RowMapper<>() {
		@Override
		public CouponListVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CouponListVO.builder()
					.CouponNo(rs.getInt("coupon_no"))
					.CouponName(rs.getString("coupon_name"))
					.CouponDiscount(rs.getInt("coupon_discount"))
					.CouponInfo(rs.getString("coupon_info"))
					.CouponStartdate(rs.getDate("coupon_startdate"))
					.CouponEnddate(rs.getDate("coupon_enddate"))
					.CouponUseDate(rs.getDate("coupon_use_date"))
					.CouponValid(rs.getString("coupon_valid"))					
				.build();		
		}
	};
	
	//쿠폰 셀렉트 박스 조회를 위한 RowMapper 추가
	private RowMapper<CouponListVO> couponSelectMapper = new RowMapper<>() {
		@Override
		public CouponListVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CouponListVO.builder()
					.CouponName(rs.getString("coupon_name"))
					.CouponDiscount(rs.getInt("coupon_discount"))
					.CouponEnddate(rs.getDate("coupon_enddate"))				
				.build();		
		}
	};
	
	private RowMapper<CouponUseVO> couponUseMapper = new RowMapper<>() {
		@Override
		public CouponUseVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CouponUseVO.builder()
					.CouponName(rs.getString("coupon_name"))
					.CouponDiscount(rs.getInt("coupon_discount"))
					.CouponEnddate(rs.getDate("coupon_enddate"))
					.CouponIssue(rs.getInt("coupon_issue"))
					.CouponInfo(rs.getString("coupon_info"))
					.build();
			
		}
		
	};
	
	//쿠폰리스트 출력, 사용되지않은 쿠폰리스트도 함께 출력하기 -> outer join 사용
	@Override
	public List<CouponListVO> couponList(String memberId) {
		String sql = "select CCL.coupon_no, CCL.coupon_name, CCL.coupon_discount, CCL.coupon_info, CCL.coupon_startdate, CCL.coupon_enddate, "
						+ "CU.coupon_use_date,CCL.coupon_valid from coupon_use CU right outer join(select * from coupon C inner join coupon_list CL on C.coupon_no=CL.coupon_list_no) "
						+ "CCL on CU.coupon_issue_no=CCL.coupon_issue where CCL.coupon_id=?";
		Object[] param = new Object[] {memberId};
		return jdbcTemplate.query(sql, couponMapper, param);
		
	}
	
	
	//사용가능한(미사용) 쿠폰만 출력
	@Override
	public int selectUsable(String memberId) {
		try {
			String sql ="select count(*) cnt from coupon where coupon_id=? and coupon_valid='미사용' group by coupon_id";
			Object[] param = {memberId};
			return jdbcTemplate.queryForObject(sql, int.class, param);
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	//주문페이지로 넘어갈 쿠폰 데이터 불러오기
	

	//주문 페이지 내 쿠폰 셀렉트박스 생성 및 관련 컬럼 데이터만 출력 -> outer join사용
	@Override
	public List<CouponListVO> selectCoupon(String memberId) {
		String sql =   "select CCL.coupon_discount, CCL.coupon_name, CCL.coupon_enddate	from coupon_use CU right outer join(select * from coupon C inner join coupon_list CL on C.coupon_no=CL.coupon_list_no) "
				 + "CCL on CU.coupon_issue_no=CCL.coupon_issue where CCL.coupon_id=?";
		Object[] param = new Object[] {memberId};
		return jdbcTemplate.query(sql, couponSelectMapper, param);
	}

	@Override
	public List<CouponUseVO> useCoupon(String couponId) {
		String sql =  "select CL.coupon_name, CL.coupon_discount, CL.coupon_Info, C.coupon_issue, C.coupon_enddate from coupon_list CL inner join coupon C on CL.coupon_list_no = C.coupon_no where coupon_id=? and coupon_valid='미사용'";
		Object[] param = new Object[] {couponId};
		return jdbcTemplate.query(sql, couponUseMapper, param);
	}	
}
