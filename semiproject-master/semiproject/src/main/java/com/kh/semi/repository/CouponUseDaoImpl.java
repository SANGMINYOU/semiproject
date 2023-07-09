package com.kh.semi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.CouponUseDto;
@Repository
public class CouponUseDaoImpl implements CouponUseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	private RowMapper<CouponUseDto> mapper = new RowMapper<CouponUseDto>() {
		public CouponUseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			CouponUseDto couponUseDto = new CouponUseDto();
			couponUseDto.setCouponUseNo(rs.getInt("coupon_use_no"));
			couponUseDto.setCouponUseOrderNo(rs.getInt("coupon_use_order_no"));
			couponUseDto.setCouponIssueNo(rs.getInt("coupon_issue_no"));
			couponUseDto.setCouponUseDate(rs.getDate("coupon_use_date"));			
			return couponUseDto;	
		}
	};
	
	//쿠폰 사용내역 추가
	@Override
	public void insert(int orderNo, Integer couponIssue) {
		String sql = "insert into coupon_use(coupon_use_no, coupon_use_order_no, "
								+ "coupon_issue_no) "
							+ "values(coupon_use_seq.nextval, ?, ?)";
		Object []param = {orderNo, couponIssue};
		jdbcTemplate.update(sql, param);		
	}
	
	//쿠폰 사용내역 삭제(매개변수 : 쿠폰사용번호)
	@Override
	public boolean delete(int couponUseNo) {
		String sql = "delete coupon_use where coupon_use_no?";
		Object [] param = {couponUseNo};
		return jdbcTemplate.update(sql, param) > 0;	
	}
//	//쿠폰 사용내역 수정(매개변수 : 쿠폰사용번호, coupon_valid를 사용으로 업데이트)
//	@Override
//	public boolean update(int couponIssue) {
//		String sql = "update coupon set coupon_valid='사용' where coupon_issue=?";
//		Object[] param = {couponIssue};
//		return jdbcTemplate.update(sql, param) > 0;
//	}
	
	//쿠폰 사용내역 조회(매개변수 : 주문 번호)
	@Override
	public List<CouponUseDto> selectList(int couponUseOrderNo) {
		String sql = "select * from coupon_use where coupon_use_order_no=?";
		Object[] param = {couponUseOrderNo};
		return jdbcTemplate.query(sql, mapper, param);
	}


}


