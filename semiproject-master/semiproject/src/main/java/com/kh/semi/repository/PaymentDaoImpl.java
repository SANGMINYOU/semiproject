package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.PaymentDto;
import com.kh.semi.vo.PaymentVO;

@Repository
public class PaymentDaoImpl implements PaymentDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//주문번호 불러오기
	@Override
	public int sequence() {
		String sql ="select payment_seq.nextval from dual";
		int paymentNo = jdbcTemplate.queryForObject(sql, int.class);
		return paymentNo;
	}

	
	//1. 등록(추가)
	@Override
	public void insert(PaymentDto paymentDto) {
		String sql = "insert into payment("
				+ "payment_no, payment_order_no, payment_product_no, "
				+ "payment_count, payment_price, payment_option) values("
				+ "?, ?, ?, ?, ?, ?)";
		Object[] param = {
				paymentDto.getPaymentNo(),
				paymentDto.getPaymentOrderNo(),
				paymentDto.getPaymentProductNo(),
				paymentDto.getPaymentCount(),
				paymentDto.getPaymentPrice(),
				paymentDto.getPaymentOption()
		};
		jdbcTemplate.update(sql, param);
	}
	
	//1-2. 등록(추가)
	public void insert(PaymentVO payment) {
		String sql = "insert into payment("
				+ "payment_no, payment_order_no, payment_product_no, "
				+ "payment_count, payment_price, payment_option) values("
				+ "?, ?, ?, ?, ?, ?)";
		Object[] param = {
				payment.getPaymentNo(),
				payment.getPaymentOrderNo(),
				payment.getPaymentProductNo(),
				payment.getPaymentCount(),
				payment.getPaymentPrice(),
				payment.getPaymentOption()
		};
		jdbcTemplate.update(sql, param);
		
	}

	//2. 수정(상품번호로 조회 후 수량/가격을 변경)
	@Override
	public boolean update(PaymentDto paymentDto) {
		String sql = "update payment set "
				+ "payment_count=?, payment_price=? "
				+ "where payment_product_no=?;";
		Object[] param = {paymentDto.getPaymentCount(),
				paymentDto.getPaymentPrice(),
				paymentDto.getPaymentProductNo()};
		return jdbcTemplate.update(sql, param)>0;
	}

	//mapper 생성
	private RowMapper<PaymentDto> mapper =(rs, idx)->{
		PaymentDto dto = new PaymentDto();
		dto.setPaymentNo(rs.getInt("payment_no"));
		dto.setPaymentOrderNo(rs.getInt("payment_order_no"));
		dto.setPaymentProductNo(rs.getInt("payment_product_no"));
		dto.setPaymentCount(rs.getInt("payment_count"));
		dto.setPaymentPrice(rs.getInt("payment_price"));
		dto.setPaymentOption(rs.getString("payment_option"));
		return dto;
	};
	
	
	//3. 조회
	@Override
	public List<PaymentDto> selectList() {
		String sql = "select * from payment";
		return jdbcTemplate.query(sql, mapper);
	}
	
	//extractor 생성
	private ResultSetExtractor<PaymentDto> extractor = (rs) ->{
		if(rs.next()) {
			PaymentDto dto = new PaymentDto();
			dto.setPaymentNo(rs.getInt("payment_no"));
			dto.setPaymentOrderNo(rs.getInt("payment_order_no"));
			dto.setPaymentProductNo(rs.getInt("payment_product_no"));
			dto.setPaymentCount(rs.getInt("payment_count"));
			dto.setPaymentPrice(rs.getInt("payment_price"));
			dto.setPaymentOption(rs.getString("payment_option"));
			return dto;
		}else {
			return null;
		}
	};
	
	//3-1. 조회(주문번호로 조회)
	@Override
	public PaymentDto selectOne(int orderNo) {
		String sql="select * from payment where payment_order_no=?";
		Object[] param= {orderNo};
		return jdbcTemplate.query(sql, extractor, param);
	}

	//4. 삭제(주문번호로 조회 후 삭제)
	// 나중에 넘어오는 값 확인 후 매개변수 수정 예정
	@Override
	public boolean delete(int orderNo) {
		String sql = "delete payment where payment_order_no=?";
		Object[] param = {orderNo};
		return jdbcTemplate.update(sql, param)>0;
	}
	
	//4-1. 삭제(주문번호와 상품번호로 조회 후 삭제)
	// 나중에 넘어오는 값 확인 후 매개변수 수정 예정 
	@Override
	public boolean delete(int orderNo, int productNo) {
		String sql = "delete payment where payment_order_no=? and payment_product_no=?";
		Object[] param = {orderNo, productNo};
		return jdbcTemplate.update(sql, param)>0;
	}



}
