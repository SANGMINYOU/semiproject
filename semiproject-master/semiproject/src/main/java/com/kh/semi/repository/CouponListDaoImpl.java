package com.kh.semi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.kh.semi.entity.CouponListDto;

public class CouponListDaoImpl implements CouponListDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CouponListDto> mapper = new RowMapper<CouponListDto>() {
		public CouponListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			CouponListDto couponListDto = new CouponListDto();
			couponListDto.setCouponListNo(rs.getInt("coupon_list_no"));
			couponListDto.setCouponName(rs.getString("coupon_name"));
			couponListDto.setCouponDiscount(rs.getInt("coupon_discount"));
			couponListDto.setCouponInfo(rs.getString("coupon_info"));
			return couponListDto;
		}
	};
	
	//쿠폰 등록
	@Override
	public void insert(CouponListDto couponListDto) {
		String sql = "insert into coupon_list "
					+ "(coupon_list_no, coupon_name, coupon_discount, coupon_info) "
						+"values(coupin_list_seq.nextval, ?, ?, ?)";
		Object[] param = {
				couponListDto.getCouponName(),
				couponListDto.getCouponDiscount(),
				couponListDto.getCouponInfo()
		};
		jdbcTemplate.update(sql,param);
	}
	
	//쿠폰 정보 수정
	@Override
	public boolean update(CouponListDto couponListDto) {
		String sql = "update coupon_list set "
					+ "coupon_name =? "
					+ "coupon_discount =?"
					+ "coupon_info =? "
						+ "where coupon_list_no=?";
		Object[] param = {
				couponListDto.getCouponListNo(),
				couponListDto.getCouponName(),
				couponListDto.getCouponDiscount(),
				couponListDto.getCouponInfo()
		};
		return jdbcTemplate.update(sql, param) > 0;
	}
	
	//쿠폰 조회
	@Override
	public List<CouponListDto> selectList(int couponListNo) {
		String sql = "select * from coupon_list where coupon_list_no = ?";
		Object[] param = {couponListNo};
		return jdbcTemplate.query(sql,  mapper);
	}
	
	//쿠폰 삭제
	@Override
	public boolean delete(int couponListNo) {
		String sql = "delete coupon_list where coupon_list_no=?";
		Object[] param = {couponListNo};
		return jdbcTemplate.update(sql, param) > 0;
	}

}
