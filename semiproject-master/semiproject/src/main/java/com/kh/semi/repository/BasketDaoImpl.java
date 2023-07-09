package com.kh.semi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.BasketDto;
import com.kh.semi.vo.BasketVO;


@Repository
public class BasketDaoImpl implements BasketDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int sequence() {
		String sql ="select basket_seq.nextval from dual";
		int basketNo = jdbcTemplate.queryForObject(sql, int.class);
		return basketNo;
	}
	//장바구니 추가
	@Override
	public void insert(BasketDto basketDto) {
		String sql = "insert into basket (basket_no, basket_id, "
				+ "basket_product_no, basket_count_number, "
				+ "basket_adddate, basket_product_option) "
				+ "values(?,?, ?, ?, sysdate,?)";
		Object[] param = {basketDto.getBasketNo(),
				basketDto.getBasketId(),
				basketDto.getBasketProductNo(),
				basketDto.getBasketCountNumber(),
				basketDto.getBasketProductOption()};
		jdbcTemplate.update(sql,param);
	}

	//장바구니 수량수정
	@Override
	public boolean changeCount(BasketDto basketDto) {
		String sql = "update basket set "
				+ "basket_count_number=?, "
				+ "basket_adddate=sysdate "
				+ "where basket_no=?";
		Object[] param = {basketDto.getBasketCountNumber(),
				basketDto.getBasketNo()};
		return jdbcTemplate.update(sql, param)>0;
	}
	
	//장바구니 수량수정
		@Override
		public boolean changeOneCount(int productCount, int productNo) {
			String sql = "update basket set "
					+ "basket_count_number=basket_count_number+?, "
					+ "basket_adddate=sysdate "
					+ "where basket_product_no=?";
			Object[] param = {	productCount,productNo};
			return jdbcTemplate.update(sql, param)>0;
		}

	//BasketDto Mapper
	private RowMapper<BasketDto> mapper =(rs, idx)->{
		return BasketDto.builder()
				.basketNo(rs.getInt("basket_no"))
				.basketId(rs.getString("basket_id"))
				.basketProductNo(rs.getInt("basket_product_no"))
				.basketCountNumber(rs.getInt("basket_count_number"))
				.basketAddDate(rs.getDate("basket_adddate"))
				.basketProductOption(rs.getString("basket_product_option"))
				.build();
	};
	
	//Extractor
	private ResultSetExtractor<BasketDto> extractor = (rs)->{
		if(rs.next()) {
			return BasketDto.builder()
					.basketNo(rs.getInt("basket_no"))
					.basketId(rs.getString("basket_id"))
					.basketProductNo(rs.getInt("basket_product_no"))
					.basketCountNumber(rs.getInt("basket_count_number"))
					.basketAddDate(rs.getDate("basket_adddate"))
					.basketProductOption(rs.getString("basket_product_option"))
					.build();
		}else {
			return null;
		}
	};

	
	//장바구니 조회(BasketDto, 매개변수:회원ID)
	@Override
	public List<BasketDto> selectDtoList(String memberId) {
		String sql = "select * from basket where basket_id=?";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql,  mapper, param);
	}
	
	//BasketVO Mapper
	private RowMapper<BasketVO> voMapper =(rs, idx)->{
		return BasketVO.builder()
				.basketNo(rs.getInt("basket_no"))
				.basketId(rs.getString("basket_id"))
				.basketProductNo(rs.getInt("basket_product_no"))
				.basketCountNumber(rs.getInt("basket_count_number"))
				.basketAddDate(rs.getDate("basket_adddate"))
				.basketProductOption(rs.getString("basket_product_option"))
				.productName(rs.getString("product_name"))
				.productPrice(rs.getInt("product_price"))
				.productOriginNo(rs.getInt("product_origin_no"))
				.productAttachmentNo(rs.getInt("product_attachment_no"))
				.build();
	};
	//BasketVO Extractor
	private ResultSetExtractor<BasketVO> voExtractor =(rs)->{
		if(rs.next()) {
			return BasketVO.builder()
					.basketNo(rs.getInt("basket_no"))
					.basketId(rs.getString("basket_id"))
					.basketProductNo(rs.getInt("basket_product_no"))
					.basketCountNumber(rs.getInt("basket_count_number"))
					.basketAddDate(rs.getDate("basket_adddate"))
					.basketProductOption(rs.getString("basket_product_option"))
					.productName(rs.getString("product_name"))
					.productPrice(rs.getInt("product_price"))
					.productOriginNo(rs.getInt("product_origin_no"))
					.productAttachmentNo(rs.getInt("product_attachment_no"))
					.build();
		}else {
			return null;
		}
	};
	
	//3-2. 장바구니 조회(BasketVO, 매개변수:회원ID) + 이미지 추가
	@Override
	public List<BasketVO> selectList(String memberId) {
		String sql = "select b.*, p.product_name, p.product_price, att.* from basket b "
				+ "inner join product p on b.basket_product_no=p.product_no "
				+ "inner join product_attachment att on p.product_no=att.product_origin_no "
				+ "where basket_id=? order by basket_no desc";
		Object[] param = {memberId};
		return jdbcTemplate.query(sql, voMapper, param);
	}
	
	//3-3. 장바구니 조회(BasketVO, 매개변수 : 회원ID, 상품번호)
	@Override
	public BasketVO orderBeforeList(String memberId, int productNo) {
		String sql = "select b.*, p.product_name, p.product_price "
				+ "from basket b inner join product p "
				+ "on b.basket_product_no=p.product_no "
				+ "where basket_id=? and basket_product_no=?";
		Object[] param= {memberId,productNo};
		return jdbcTemplate.query(sql, voExtractor, param);
	}
	
	//3-4. 장바구니 내 동일 상품있는지 조회(옵션 없는 단일상품만!)
	public BasketDto sameItem(String memberId, int productNo) {
		String sql = "select * from basket where basket_id=? and basket_product_no=?";
		Object[] param= {memberId,productNo};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	//3-5. 선택상품 조회(장바구니 번호로 조회)
	@Override
	public BasketVO orderList(int basketNo) {
		String sql ="select b.*, p.product_name, p.product_price, att.* "
				+ "from basket b "
				+ "inner join product p on b.basket_product_no=p.product_no "
				+ "inner join product_attachment att on p.product_no=att.product_origin_no "
				+ "where basket_no=?";
		Object[] param= {basketNo};
		return jdbcTemplate.query(sql, voExtractor, param);
	}
	
	
	//4-1. 장바구니 상품삭제(매개변수:장바구니 번호)
	@Override
	public boolean delete(int productNo) {
		String sql = "delete basket where basket_product_no=?";
		Object[] param= {productNo};
		return jdbcTemplate.update(sql, param)>0;
	}
	
	//4-2. 장바구니 상품삭제(매개변수:장바구니 번호)
	@Override
	public boolean clearbasket(int basketNo) {
		String sql = "delete basket where basket_no=?";
		Object[] param= {basketNo};
		return jdbcTemplate.update(sql, param)>0;
	}



	
}
