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

import com.kh.semi.entity.CategoryHighDto;
import com.kh.semi.entity.CategoryLowDto;
import com.kh.semi.entity.ProductDto;
import com.kh.semi.vo.OrderVO;
import com.kh.semi.vo.ProductDetailVO;
import com.kh.semi.vo.ProductListSearchAllVO;
import com.kh.semi.vo.ProductListSearchCategoryVO;
import com.kh.semi.vo.ProductListSearchVO;
import com.kh.semi.vo.ProductListVO;
import com.kh.semi.vo.ProductNoNameVO;
import com.kh.semi.vo.ProductSelectNameVO;

@Repository
public class ProductDaoImpl implements ProductDao {

	// 의존성 주입
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 회원이 보는 페이지 관련
	// 카테고리 조회를 위한 ProductDto에 대한 RowMapper (product_inactive 추가)
	private RowMapper<ProductListVO> mapperCategory = new RowMapper<>() {
		@Override
		public ProductListVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ProductListVO.builder()
						.productNo(rs.getInt("product_no"))
						.productName(rs.getString("product_name"))
						.productPrice(rs.getInt("product_price"))
						.productGood(rs.getDouble("product_good"))
						.productInactive(rs.getString("product_inactive") != null)
						.productAttachmentNo(rs.getInt("product_attachment_no"))
						.categoryHighSub(rs.getNString("category_high_sub") != null)
					.build();
		}
	};
	
	// 추상 메소드 오버라이딩 - 카테고리 상품 조회(SELECT)
	// 1) 카테고리 상품 통합 조회
	@Override
	public List<ProductListVO> selectListCategory(ProductListSearchCategoryVO productListSearchCategoryVO) {
		// 검색 조회인지 판정
		boolean isSearch = productListSearchCategoryVO.isSearch();

		if(isSearch) { // 검색 조회라면
			return searchListCategory(productListSearchCategoryVO);
		}
		else { // 검색 조회가 아니라면(전체 조회라면)
			return allListCategory(productListSearchCategoryVO);
		}
	}
	
	// 2) 카테고리 상품 전체 조회
	@Override
	public List<ProductListVO> allListCategory(ProductListSearchCategoryVO productListSearchCategoryVO) {
		// 하위 카테고리 번호 존재 여부 판쟁
		if(productListSearchCategoryVO.getCategoryLowNo() != 0) { // 하위 카테고리가 존재한다면
			String sql = "select * from (select TMP.*, rownum rn from (select phl.category_high_sub, phl.product_no, phl.product_name, phl.product_price, phl.product_good, phl.product_inactive, pa.product_attachment_no from product_attachment pa right outer join (select * from product p inner join (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?) hl on p.category_low_no = hl.category_low_no) phl on pa.product_origin_no = phl.product_no)TMP) where rn between ? and ? order by product_name asc";
			Object[] param = new Object[] {productListSearchCategoryVO.getCategoryHighNo(), productListSearchCategoryVO.getCategoryLowNo(), productListSearchCategoryVO.rownumStart(), productListSearchCategoryVO.rownumEnd()};
			// 해당 상위 카테고리의 해당 하위 카테고리 상품 전체 조회
			return jdbcTemplate.query(sql, mapperCategory, param);
		}
		else { // 하위 카테고리 번호가 존재하지 않는다면
			String sql = "select * from (select TMP.*, rownum rn from (select phl.category_high_sub, phl.product_no, phl.product_name, phl.product_price, phl.product_good, phl.product_inactive, pa.product_attachment_no from product_attachment pa right outer join (select * from product p inner join (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ?) hl on p.category_low_no = hl.category_low_no) phl on pa.product_origin_no = phl.product_no)TMP) where rn between ? and ? order by product_name asc";
			Object[] param = new Object[] {productListSearchCategoryVO.getCategoryHighNo(), productListSearchCategoryVO.rownumStart(), productListSearchCategoryVO.rownumEnd()};
			// 해당 상위 카테고리의 상품 전체 조회
			return jdbcTemplate.query(sql, mapperCategory, param);
		}
	}
	
	// 3) 카테고리 상품 검색 조회
	@Override
	public List<ProductListVO> searchListCategory(ProductListSearchCategoryVO productListSearchCategoryVO) {
		// 하위 카테고리 번호 존재 여부 판정
		if(productListSearchCategoryVO.getCategoryLowNo() != 0) { // 하위 카테고리 번호가 존재한다면
			String sql = "select * from (select TMP.*, rownum rn from (select phl.category_high_sub, phl.product_no, phl.product_name, phl.product_price, phl.product_good, phl.product_inactive, pa.product_attachment_no from product_attachment pa right outer join (select * from product p inner join (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?) hl on p.category_low_no = hl.category_low_no where instr(#1, ?) > 0) phl on pa.product_origin_no = phl.product_no)TMP) where rn between ? and ? order by product_name asc";
			sql = sql.replace("#1", productListSearchCategoryVO.getType());
			Object[] param = new Object[] {productListSearchCategoryVO.getCategoryHighNo(), productListSearchCategoryVO.getCategoryLowNo(), productListSearchCategoryVO.getKeyword(), productListSearchCategoryVO.rownumStart(), productListSearchCategoryVO.rownumEnd()};
			// 해당 상위 카테고리의 해당 하위 카테고리 상품 검색 조회 
			return jdbcTemplate.query(sql, mapperCategory, param);
		}
		else { // 하위 카테고리 번호가 존재하지 않는다면
			String sql = "select * from (select TMP.*, rownum rn from (select phl.category_high_sub, phl.product_no, phl.product_name, phl.product_price, phl.product_good, phl.product_inactive, pa.product_attachment_no from product_attachment pa right outer join (select * from product p inner join (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ?) hl on p.category_low_no = hl.category_low_no where instr(#1, ?) > 0) phl on pa.product_origin_no = phl.product_no)TMP) where rn between ? and ? order by product_name asc";
			sql = sql.replace("#1", productListSearchCategoryVO.getType());
			Object[] param = new Object[] {productListSearchCategoryVO.getCategoryHighNo(), productListSearchCategoryVO.getKeyword(), productListSearchCategoryVO.rownumStart(), productListSearchCategoryVO.rownumEnd()};
			// 해당 상위 카테고리의 상품 검색 조회
			return jdbcTemplate.query(sql, mapperCategory, param);
		}
	}
	
	// 추상 메소드 - 회원이 header에 엤는 검색창으로 상품 조회
	@Override
	public List<ProductListVO> selectSearchListProduct(ProductListSearchAllVO productListSearchAllVO) {
		String sql = "select * from (select TMP.*, rownum rn from (select phl.category_high_sub, phl.product_no, phl.product_name, phl.product_price, phl.product_good, phl.product_inactive, pa.product_attachment_no from product_attachment pa right outer join (select * from product p inner join (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no) hl on p.category_low_no = hl.category_low_no where instr(p.product_name, ?) > 0) phl on pa.product_origin_no = phl.product_no)TMP) where rn between ? and ? order by product_name asc";
		Object[] param = new Object[] {productListSearchAllVO.getKeyword(), productListSearchAllVO.rownumStart(), productListSearchAllVO.rownumEnd()};
		return jdbcTemplate.query(sql, mapperCategory, param);
	}
	
	// 추상 메소드 - 회원이 header에 엤는 검색창으로 상품 조회시 상품의 총 갯수
	@Override
	public int countSelectSearchProduct(ProductListSearchAllVO productListSearchAllVO) {
		String sql = "select count(*) from product where instr(product_name, ?) > 0";
		Object[] param = new Object[] {productListSearchAllVO.getKeyword()};
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}
	
	// ProductDto에 대한 ResultSetExtractor (product_inactive 추가)
	private ResultSetExtractor<ProductDto> extractor = new ResultSetExtractor<>() {
		@Override
		public ProductDto extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()) {
				return ProductDto.builder()
						.productNo(rs.getInt("product_no"))
						.categoryHighNo(rs.getInt("category_high_no"))
						.categoryLowNo(rs.getInt("category_low_no"))
						.productName(rs.getString("product_name"))
						.productPrice(rs.getInt("product_price"))
						.productInformation(rs.getString("product_information"))
						.productInventory(rs.getInt("product_inventory"))
						.productGood(rs.getDouble("product_good"))
						.productRegisttime(rs.getDate("product_registtime"))
						.productUpdatetime(rs.getDate("product_updatetime"))
						.productInactive(rs.getString("product_inactive") != null)
					.build();
			}
			else {
				return null;
			}
		}
	};
	
	// 추상 메소드 오버라이딩 - 회원용 상품 상세(DETAIL)
	@Override
	public ProductDetailVO selectOneProductUser(int productNo) {
		String sql = "select p.product_no, p.product_name, p.product_price, p.product_good, p.product_inactive, pa.product_attachment_no from product p inner join product_attachment pa on p.product_no = pa.product_origin_no where p.product_no = ?";
		Object[] param = new Object[] {productNo};
		return jdbcTemplate.query(sql, extractorDetail, param);
	}
	
	// 관리자 페이지 관련 -------------------------------------------------------------------------------
	// 추상 메소드 오버라이딩 - 상위 카테고리 다음 시퀀스 번호 반환
	@Override
	public int sequencecategoryHigh() {
		String sql = "select category_high_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	
	// 추상 메소드 오버라이딩 - 상위 카테고리 생성 (일반 상품용)
	@Override
	public void createCategoryHigh(int categoryHighNo, String categoryHighName) {
		String sql = "insert into category_high(category_high_no, category_high_name) values (?, ?)";
		Object[] param = new Object[] {categoryHighNo, categoryHighName};
		jdbcTemplate.update(sql, param);
	}
	
	// 추상 메소드 오버라이딩 - 상위 카테고리 생성 (구독 상품용)
	@Override
	public void createCategoryHigh(int categoryHighNo, String categoryHighName, String categoryHighSub) {
		String sql = "insert into category_high(category_high_no, category_high_name, category_high_sub) values (?, ?, ?)";
		Object[] param = new Object[] {categoryHighNo, categoryHighName, categoryHighSub};
		jdbcTemplate.update(sql, param);
	}
	
	// 추상 메소드 오버라이딩 - 하위 카테고리 다음 시퀀스 번호 반환
	@Override
	public int sequencecategoryLow() {
		String sql = "select category_high_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}

	// 추상 메소드 오버라이딩 - 하위 카테고리 생성
	@Override
	public void createCategoryLow(int categoryHighNo, int categoryLowNo, String categoryLowName) {
		String sql = "insert into category_low(category_high_no, category_low_no, category_low_name) values(?, ?, ?)";
		Object[] param = new Object[] {categoryHighNo, categoryLowNo, categoryLowName};
		jdbcTemplate.update(sql, param);
	}
	
	// CategoryHighDto에 대한 RowMapper
	private RowMapper<CategoryHighDto> categoryHighMapper = new RowMapper<CategoryHighDto>() {
		@Override
		public CategoryHighDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CategoryHighDto.builder()
						.categoryHighNo(rs.getInt("category_high_no"))
						.categoryHighName(rs.getString("category_high_name"))
					.build();
		}
	};
	
	// 추상 메소드 오버라이딩 - 상위 카테고리 항목 조회
	@Override
	public List<CategoryHighDto> selectCategoryHighList() {
		String sql = "select * from category_high order by category_high_no asc";
		return jdbcTemplate.query(sql, categoryHighMapper);
	}
	
	// CategoryLowDto에 대한 RowMapper
	private RowMapper<CategoryLowDto> categoryLowMapper = new RowMapper<CategoryLowDto>() {
		@Override
		public CategoryLowDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CategoryLowDto.builder()
						.categoryHighNo(rs.getInt("category_high_no"))
						.categoryLowNo(rs.getInt("category_low_no"))
						.categoryLowName(rs.getString("category_low_name"))
					.build();
		}
	};
	
	// 추상 메소드 오버라이딩 - 상위 카테고리 항목에 연결된 하위 카테고리 항목 조회
	@Override
	public List<CategoryLowDto> selectCategoryLowList(int categoryHighNo) {
		// 특정 상위 카테고리 컬럼에 연결된 하위 카테고리의 컬럼을 조회
		String sql = "select * from category_low where category_high_no = ? order by category_low_no desc";
		// 바인드 변수 배열 생성
		Object[] param = new Object[] {categoryHighNo};
		// List<ProductCategoryListVO> 형태의 조회 결과를 반환
		return jdbcTemplate.query(sql, categoryLowMapper, param);
	}
	
	// 추상 메소드 오버라이딩 - 상품 등록을 위한 다음 시퀀스 번호 반환
	@Override
	public int nextSequence() {
		String sql = "select product_seq.nextval from dual";
		int productNo = jdbcTemplate.queryForObject(sql, int.class);
		return productNo;
	}

	// 추상 메소드 오버라이딩 - 관리자 상품 등록(INSERT)
	@Override
	public void insertProduct(ProductDto productDto) {
		// 상품 등록 SQL
		String sql = "insert into product(product_no, category_high_no, category_low_no, product_name, product_price, product_information, product_inventory) values(?, ?, ?, ?, ?, ?, ?)";
		// ProductDto의 getter값으로 바인드 변수 배열 생성
		Object[] param = new Object[] {productDto.getProductNo(), productDto.getCategoryHighNo(), productDto.getCategoryLowNo(), productDto.getProductName(), productDto.getProductPrice(), productDto.getProductInformation(), productDto.getProductInventory()};
		// 등록 실행
		jdbcTemplate.update(sql, param);
	}
	
	// 추상 메소드 오버라이딩 - 상품 이미지 첨부파일 기록 등록(INSERT)
	@Override
	public void connectAttachment(int productNo, int attachmentNo) {
		String sql = "insert into product_attachment VALUES(?, ?)";
		Object[] param = {productNo, attachmentNo};	
		jdbcTemplate.update(sql, param);
	}
	
	// ProductDto에 대한 RowMapper (product_inactive 추가)
	private RowMapper<ProductDto> mapper = new RowMapper<>() {
		@Override
		public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ProductDto.builder()
						.productNo(rs.getInt("product_no"))
						.categoryHighNo(rs.getInt("category_high_no"))
						.categoryLowNo(rs.getInt("category_low_no"))
						.productName(rs.getString("product_name"))
						.productPrice(rs.getInt("product_price"))
						.productInformation(rs.getString("product_information"))
						.productInventory(rs.getInt("product_inventory"))
						.productGood(rs.getDouble("product_good"))
						.productRegisttime(rs.getDate("product_registtime"))
						.productUpdatetime(rs.getDate("product_updatetime"))
						.productInactive(rs.getString("product_inactive") != null)
					.build();
		}
	};

	// 추상 메소드 오버라이딩 - 모든 상품 조회(SELECT)
	// 1) 통합 조회
	@Override
	public List<ProductDto> selectListProduct(ProductListSearchVO productListSearchVO) {
		// 검색 조회인지 전체 조회인지 판정
		if(productListSearchVO.isSearch()) { // 검색 조회라면
			// 매개변수인 productListSearchVO의 type과 keyword를 반환하여 검색 조회 실행
			return searchListProduct(productListSearchVO);
		}
		else { // 전체 조회라면
			// 전체 조회 실행
			return allListProduct(productListSearchVO);
		}
	}

	// 2) 전체 조회
	@Override
	public List<ProductDto> allListProduct(ProductListSearchVO productListSearchVO) {
		// Top N Query SQL문 - 전체 조회시 한 페이지에 조회될 레코드의 수를 제한
		String sql = "select * from (select TMP.*, rownum rn from (select * from product order by product_no desc)TMP) where rn between ? and ?";
		Object[] param = new Object[] {productListSearchVO.rownumStart(), productListSearchVO.rownumEnd()};
		return jdbcTemplate.query(sql, mapper, param);
	}

	// 3) 검색 조회
	@Override
	public List<ProductDto> searchListProduct(ProductListSearchVO productListSearchVO) {
		// Top N Query SQL문 - 전체 조회시 한 페이지에 조회될 레코드의 수를 제한
		String sql = "select * from (select TMP.*, rownum rn from (select * from product where instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?";
		sql = sql.replace("#1", productListSearchVO.getType());
		Object[] param = new Object[] {productListSearchVO.getKeyword(), productListSearchVO.rownumStart(), productListSearchVO.rownumEnd()};
		return jdbcTemplate.query(sql, mapper, param);
	}
	
	// 추상 메소드 오버라이딩 - 관리자 상품 상세(DETAIL)
	@Override
	public ProductDto selectOneProduct(int productNo) {
		String sql = "select * from product where product_no = ?";
		Object[] param = new Object[] {productNo};
		return jdbcTemplate.query(sql, extractor, param);
	}
	
	// ReviewProductVO에 대한 ResultSetExtractor
	private ResultSetExtractor<ProductDetailVO> extractorDetail = new ResultSetExtractor<>() {

		@Override
		public ProductDetailVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()) {
				return ProductDetailVO.builder()
							.productNo(rs.getInt("product_no"))
							.productName(rs.getString("product_name"))
							.productPrice(rs.getInt("product_price"))
							.productGood(rs.getDouble("product_good"))
							.productInactive(rs.getString("product_inactive") != null)
							.productAttachmentNo(rs.getInt("product_attachment_no"))
						.build();
			}
			else {
				return null;
			}
		}
		
	};
	
	// ProductNoNameVO에 대한 Mapper
	private RowMapper<ProductNoNameVO> mapperNoName = new RowMapper<>() {
		@Override
		public ProductNoNameVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ProductNoNameVO.builder()
					.productNo(rs.getInt("product_no"))
					.productName(rs.getNString("product_name"))
					.build();
		}
	};
	
	// 추상 메소드 - 상품 번호와 이름 전체 조회
	@Override
	public List<ProductNoNameVO> selectNoName() {
		String sql = "select product_no, product_name from product where category_high_no = 42 or category_high_no = 43";
		return jdbcTemplate.query(sql, mapperNoName);
	}

	// 추상 메소드 오버라이딩 - 관리자 상품 수정(UPDATE)
	@Override
	public boolean updateProduct(ProductDto productDto) {
		String sql = "update product set "
						+ "category_high_no = ?, "
						+ "category_low_no = ?, "
						+ "product_name = ?, "
						+ "product_price = ?, "
						+ "product_information = ?, "
						+ "product_inventory = ? "
					+ "where product_no = ?";
		Object[] param = new Object[] {
						productDto.getCategoryHighNo(), 
						productDto.getCategoryLowNo(), 
						productDto.getProductName(), 
						productDto.getProductPrice(),
						productDto.getProductInformation(),
						productDto.getProductInventory(),
						productDto.getProductNo()
						};
		return jdbcTemplate.update(sql, param) > 0;
	}

	// 추상 메소드 오버라이딩 - 상품 수정시 수정시간 갱신
	@Override
	public void updateProductRegistTime(int productNo) {
		String sql = "update product set product_updatetime = sysdate where product_no = ?";
		Object[] param = new Object[] {productNo};
		jdbcTemplate.update(sql, param);
	}

	// 추상 메소드 오버라이딩 - 관리자 상품 삭제(비활성화로 UPDATE)
	@Override
	public boolean inactiveProduct(int productNo, boolean isInactiveProduct) {
		String sql = "update product set product_inactive = ? where product_no = ?";
		String isInactive = isInactiveProduct ? "Y" : null;
		Object[] param = new Object[] {isInactive, productNo};
		return jdbcTemplate.update(sql, param) > 0;
	}

	// 추상 메소드 오버라이딩 - 관리자 상품 삭제(DELETE)
	@Override
	public boolean deleteProduct(int productNo) {
		String sql = "delete product where product_no = ?";
		Object[] param = new Object[] {productNo};
		return jdbcTemplate.update(sql, param) > 0;
	}

	// 추상 메소드 오버라이딩 - 모든 상품 조회에서 상품 총 갯수
	// 1) 통합 조회시 상품의 총 갯수
	@Override
	public int countTotalProduct(ProductListSearchVO productListSearchVO) {
		// 검색 조회인지 판정
		if(productListSearchVO.isSearch()) { // 검색 조회라면
			return countSearchProduct(productListSearchVO);
		}
		else { // 검색 조회가 아니라면(전체 조회라면)
			return countAllProduct();
		}
	}

	// 2) 전체 조회시 상품의 총 갯수
	@Override
	public int countAllProduct() {
		String sql = "select count(*) from product";
		return jdbcTemplate.queryForObject(sql, int.class);
	}

	// 3) 검색 조회시 상품의 총 갯수
	@Override
	public int countSearchProduct(ProductListSearchVO productListSearchVO) {
		String sql = "select count(*) from product where instr(#1, ?) > 0";
		sql = sql.replace("#1", productListSearchVO.getType());
		Object[] param = new Object[] {productListSearchVO.getKeyword()};
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}
	
	// 추상 메소드 오버라이딩 - 카테고리 상품 조회에서 상품 총 갯수
	// 1) 통합 조회시 상품의 총 갯수
	@Override
	public int countTotalCategory(ProductListSearchCategoryVO productListSearchAllVO) {
		// 검색 조회인지 판정
		if(productListSearchAllVO.isSearch()) { // 검색 조회라면
			return countSearchCategory(productListSearchAllVO);
		}
		else { // 검색 조회가 아니라면(전체 조회라면)
			return countAllCategory(productListSearchAllVO);
		}
	}

	// 2) 전체 조회시 상품의 총 갯수
	@Override
	public int countAllCategory(ProductListSearchCategoryVO productListSearchAllVO) {
		String sql;
		Object[] param;
		// 상위 카테고리만 조회인지 판정
		if(productListSearchAllVO.getCategoryLowNo() == 0) { // 상위 카테고리 조회라면
			sql = "select count(*) from product where category_high_no = ?";
			param = new Object[] {productListSearchAllVO.getCategoryHighNo()};
		}
		else { // 상위 카테고리 조회가 아니라면
			sql = "select count(*) from product where category_high_no = ? and category_low_no = ?";
			param = new Object[] {productListSearchAllVO.getCategoryHighNo(), productListSearchAllVO.getCategoryLowNo()};
		}
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}

	// 3) 검색 조회시 상품의 총 갯수
	@Override
	public int countSearchCategory(ProductListSearchCategoryVO productListSearchAllVO) {
		String sql;
		Object[] param;
		// 상위 카테고리 검색 조회인지 판정
		if(productListSearchAllVO.getCategoryLowNo() == 0) { // 상위 카테고리 검색 조회라면
			sql = "select count(*) from product where category_high_no = ? and instr(#1, ?) > 0";
			sql = sql.replace("#1", productListSearchAllVO.getType());
			param = new Object[] {productListSearchAllVO.getCategoryHighNo(), productListSearchAllVO.getKeyword()};
		}
		else { // 상위 카테고리 검색 조회가 아니라면(하위 카테고리 검색 조회라면)
			sql = "select count(*) from product where category_high_no = ? and category_low_no = ? and instr(#1, ?) > 0";
			sql = sql.replace("#1", productListSearchAllVO.getType());
			param = new Object[] {productListSearchAllVO.getCategoryHighNo(), productListSearchAllVO.getCategoryLowNo(), productListSearchAllVO.getKeyword()};
		}
		return jdbcTemplate.queryForObject(sql, int.class, param);
	}

	//상품이름 조회 테스트
	private ResultSetExtractor<ProductSelectNameVO> nameExtractor =(rs)->{
		if(rs.next()) {
			ProductSelectNameVO vo = new ProductSelectNameVO();
			vo.setProductName(rs.getString("product_name"));
			return vo;
		}else {
			return null;
		}
	};
	
	@Override
	public ProductSelectNameVO selectName(int productNo) {
		String sql = "select product_name from product where product_no=?";
		Object[] param= {productNo};
		return jdbcTemplate.query(sql, nameExtractor, param);
	}
	
	//상품재고 변경 구문
	@Override
	public boolean updateProductInventory(int paymentCount, int paymentProductNo) {
		String sql = "update product set product_inventory = product_inventory - ? where product_no = ?";
		Object[] param = {paymentCount, paymentProductNo};
		return jdbcTemplate.update(sql,param)>0;
	}	
}