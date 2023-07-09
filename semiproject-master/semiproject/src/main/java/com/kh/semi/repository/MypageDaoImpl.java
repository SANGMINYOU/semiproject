package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.vo.MypagePaymentInfoVO;

@Repository
public class MypageDaoImpl implements MypageDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<MypagePaymentInfoVO> selectMyPaymentInfo(String memberId) {
		String sql = "select * from (select  p.product_no, p.product_name, op.* from (select py.*, o.order_id, o.order_date from payment py "
					+ "inner join orders o on py.payment_order_no=o.order_no where o.order_id= ? order by o.order_no desc) op "
					+ "INNER JOIN product p on op.payment_product_no = p.product_no) OPP "
					+ "INNER JOIN product_attachment PA on opp.product_no=pa.product_origin_no";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql, mypaymentInfo, param);
	}
	
	RowMapper<MypagePaymentInfoVO> mypaymentInfo = (rs, idx)->{
		return MypagePaymentInfoVO.builder()
				.productOriginNo(rs.getInt("product_no"))
				.productName(rs.getString("product_name"))
				.paymentNo(rs.getInt("payment_no"))
				.paymentOrderNo(rs.getInt("payment_order_no"))
				.paymentProductNo(rs.getInt("payment_product_no"))
				.paymentCount(rs.getInt("payment_count"))
				.paymentPrice(rs.getInt("payment_price"))
				.paymentOption(rs.getString("payment_option"))
				.paymentReview(rs.getString("payment_review") != null)
				.orderId(rs.getString("order_id"))
				.orderDate(rs.getDate("order_date"))
				.productOriginNo(rs.getInt("product_origin_no"))
				.productAttachmentNo(rs.getInt("product_attachment_no"))
				.build();
	};
	
	
}
