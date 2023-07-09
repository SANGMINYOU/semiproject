package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.OrdersDto;
import com.kh.semi.vo.OrderVO;

@Repository
public class OrdersDaoImpl implements OrdersDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//시퀀스 미리 생성
	@Override
	public int sequence() {
		String sql ="select orders_seq.nextval from dual";
		int orderNo = jdbcTemplate.queryForObject(sql, int.class);
		return orderNo;
	}

	//1. 주문 등록
	@Override
	public void insert(OrdersDto ordersDto) {
		String sql = "insert into orders(order_no, order_id, order_name,"
				+ "order_post, order_base_address, order_detail_address, "
				+ "order_tel, order_memo, order_date, order_status, "
				+ "order_changedate, order_price, order_payprice,"
				+ "order_type, order_use_point, order_point) values("
				+ "?, ?, ?, ?, ?,?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
		Object[] param = {
				ordersDto.getOrderNo(), 
				ordersDto.getOrderId(),	ordersDto.getOrderName(),
				ordersDto.getOrderPost(), ordersDto.getOrderBaseAddress(),
				ordersDto.getOrderDetailAddress(), ordersDto.getOrderTel(),
				ordersDto.getOrderMemo(), ordersDto.getOrderStatus(), 
				ordersDto.getOrderPrice(), ordersDto.getOrderPayPrice(),
				ordersDto.getOrderType(), ordersDto.getOrderUsePoint(),
				ordersDto.getOrderPoint()
		};
		jdbcTemplate.update(sql, param);
	}
	
	//1-2. orderVO로 등록
	@Override
	public void insert(OrderVO ordervo) {
		String sql = "insert into orders(order_no, order_id, order_name,"
				+ "order_post, order_base_address, order_detail_address, "
				+ "order_tel, order_memo, order_date, order_status, "
				+ "order_changedate, order_price, order_payprice,"
				+ "order_type, order_use_point, order_point) values("
				+ "?, ?, ?, ?, ?,?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
		Object[] param = {
				ordervo.getOrderNo(), 
				ordervo.getOrderId(),	ordervo.getOrderName(),
				ordervo.getOrderPost(), ordervo.getOrderBaseAddress(),
				ordervo.getOrderDetailAddress(), ordervo.getOrderTel(),
				ordervo.getOrderMemo(), ordervo.getOrderStatus(), 
				ordervo.getOrderPrice(), ordervo.getOrderPayPrice(),
				ordervo.getOrderType(), ordervo.getOrderUsePoint(),
				ordervo.getOrderPoint()
		};
		jdbcTemplate.update(sql, param);
		
	}

	//2. 주문 수정
	//2-1. 배송지 정보를 변경
	//(일단 수취인 이름, 우편번호, 기본주소, 상세주소, 휴대폰, 배송요청사항)
	@Override
	public boolean updateInfo(OrdersDto ordersDto) {
		String sql = "update orders set order_name=?, order_post=?,"
				+ "order_base_address=?, order_detail_address=?, "
				+ "order_tel=?, order_memo=? where order_no=?";
		Object[] param = {
				ordersDto.getOrderName(),
				ordersDto.getOrderPost(),
				ordersDto.getOrderBaseAddress(),
				ordersDto.getOrderDetailAddress(),
				ordersDto.getOrderTel(),
				ordersDto.getOrderMemo(), ordersDto.getOrderNo()};
		return jdbcTemplate.update(sql, param)>0;
	}

	//2-2. 주문상태, 주문상태변경일을 변경
	@Override
	public boolean updateStatus(String orderStatus, int orderNo) {
		String sql = "update orders set order_status=?, order_changedate=sysdate "
				+ "where order_no=?";
		Object[] param = {orderStatus, orderNo};
		return jdbcTemplate.update(sql, param)>0;
	}
	
	//mapper 생성
	private RowMapper<OrdersDto> mapper = (rs, idx) ->{
		OrdersDto ordersDto = new OrdersDto();
		ordersDto.setOrderNo(rs.getInt("order_no"));
		ordersDto.setOrderId(rs.getString("order_id"));
		ordersDto.setOrderName(rs.getString("order_name"));
		ordersDto.setOrderPost(rs.getString("order_post"));
		ordersDto.setOrderBaseAddress(rs.getString("order_base_address"));
		ordersDto.setOrderDetailAddress(rs.getString("order_detail_address"));
		ordersDto.setOrderTel(rs.getString("order_tel"));
		ordersDto.setOrderMemo(rs.getString("order_memo"));
		ordersDto.setOrderDate(rs.getDate("order_date"));
		ordersDto.setOrderStatus(rs.getString("order_status"));
		ordersDto.setOrderChangeDate(rs.getDate("order_changedate"));
		ordersDto.setOrderPrice(rs.getInt("order_price"));
		ordersDto.setOrderPayPrice(rs.getInt("order_payprice"));
		ordersDto.setOrderType(rs.getString("order_type"));
		ordersDto.setOrderUsePoint(rs.getInt("order_use_point"));
		ordersDto.setOrderPoint(rs.getInt("order_point"));
		return ordersDto;
	};
	
	//extractor 생성
	private ResultSetExtractor<OrdersDto> extractor = (rs) ->{
		if(rs.next()) {
			OrdersDto ordersDto = new OrdersDto();
			ordersDto.setOrderNo(rs.getInt("order_no"));
			ordersDto.setOrderId(rs.getString("order_id"));
			ordersDto.setOrderName(rs.getString("order_name"));
			ordersDto.setOrderPost(rs.getString("order_post"));
			ordersDto.setOrderBaseAddress(rs.getString("order_base_address"));
			ordersDto.setOrderDetailAddress(rs.getString("order_detail_address"));
			ordersDto.setOrderTel(rs.getString("order_tel"));
			ordersDto.setOrderMemo(rs.getString("order_memo"));
			ordersDto.setOrderDate(rs.getDate("order_date"));
			ordersDto.setOrderStatus(rs.getString("order_status"));
			ordersDto.setOrderChangeDate(rs.getDate("order_changedate"));
			ordersDto.setOrderPrice(rs.getInt("order_price"));
			ordersDto.setOrderPayPrice(rs.getInt("order_payprice"));
			ordersDto.setOrderType(rs.getString("order_type"));
			ordersDto.setOrderUsePoint(rs.getInt("order_use_point"));
			ordersDto.setOrderPoint(rs.getInt("order_point"));
			return ordersDto;
		}else {
			return null;
		}
	};
	

	//3. 주문 조회
	//3-1. 전체 주문내역 조회
	@Override
	public List<OrdersDto> selectList() {
		String sql = "select * from orders order by order_no asc";
		return jdbcTemplate.query(sql, mapper);
	}
	
	//3-2. 주문번호로 검색 후 해당건 조회
	@Override
	public OrdersDto selectOne(int orderNo) {
		String sql = "select * from orders where order_no=?";
		Object[] param= {orderNo};
		return jdbcTemplate.query(sql, extractor, param);
	}

	//4. 주문 삭제(주문번호로 검색 후 해당 주문건 삭제)
	@Override
	public boolean delete(int orderNo) {
		String sql = "delete orders where order_no=?";
		Object[] param = {orderNo};
		return jdbcTemplate.update(sql, param)>0;
	}





}
