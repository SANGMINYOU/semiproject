package com.kh.semi.repository;

import java.util.List;

import com.kh.semi.entity.CategoryHighDto;
import com.kh.semi.entity.CategoryLowDto;
import com.kh.semi.entity.ProductDto;
import com.kh.semi.vo.ProductDetailVO;
import com.kh.semi.vo.ProductListSearchAllVO;
import com.kh.semi.vo.ProductListSearchCategoryVO;
import com.kh.semi.vo.ProductListSearchVO;
import com.kh.semi.vo.ProductListVO;
import com.kh.semi.vo.ProductNoNameVO;
import com.kh.semi.vo.ProductSelectNameVO;

public interface ProductDao {
	
	// 추상 메소드 - 상위 카테고리 다음 시퀀스 번호 반환
	int sequencecategoryHigh();
	
	// 추상 메소드 - 상위 카테고리 생성 (일반 상품용)
	void createCategoryHigh(int categoryHighNo, String categoryHighName);

	// 추상 메소드 - 상위 카테고리 생성 (구독 상품용)
	void createCategoryHigh(int categoryHighNo, String categoryHighName, String categoryHighSub);
	
	// 추상 메소드 - 하위 카테고리 다음 시퀀스 번호 반환
	int sequencecategoryLow();
	
	// 추상 메소드 - 하위 카테고리 생성
	void createCategoryLow(int categoryHighNo, int categoryLowNo, String categoryLowName);
	
	// 추상 메소드 - 상위 카테고리 항목 조회
	List<CategoryHighDto> selectCategoryHighList();
	
	// 추상 메소드 - 상위 카테고리 항목에 연결된 하위 카테고리 항목 조회
	List<CategoryLowDto> selectCategoryLowList(int categoryHighNo);
	
	// 추상 메소드 - 상품 등록을 위한 다음 시퀀스 번호 반환
	int nextSequence();
	
	// 추상 메소드 - 관리자 상품 등록(INSERT)
	void insertProduct(ProductDto productDto);
	
	// 추상 메소드 - 상품 이미지 첨부파일 기록 등록(INSERT)
	void connectAttachment(int productNo, int attachmentNo);
	
	// 추상 메소드 - 모든 상품 조회(SELECT)
	// 1) 통합 조회
	List<ProductDto> selectListProduct(ProductListSearchVO productListSearchVO);
	
	// 2) 전체 조회
	List<ProductDto> allListProduct(ProductListSearchVO productListSearchVO);
	
	// 3) 검색 조회
	List<ProductDto> searchListProduct(ProductListSearchVO productListSearchVO);
	
	// 추상 메소드 - 카테고리 상품 조회(SELECT) : 카테고리 번호로 해당 카테고리 상품을 조회할 수 있도록
	// 1) 카테고리 상품 통합 조회
	List<ProductListVO> selectListCategory(ProductListSearchCategoryVO productListSearchCategoryVO);
	
	// 2) 카테고리 상품 전체 조회
	List<ProductListVO> allListCategory(ProductListSearchCategoryVO productListSearchCategoryVO);
	
	// 3) 카테고리 상품 검색 조회
	List<ProductListVO> searchListCategory(ProductListSearchCategoryVO productListSearchCategoryVO);
	
	// 추상 메소드 - 회원이 header에 엤는 검색창으로 상품 조회
	// - 검색 조회
	List<ProductListVO> selectSearchListProduct(ProductListSearchAllVO productListSearchAllVO);
	
	// 추상 메소드 - 회원이 header에 엤는 검색창으로 상품 조회시 상품의 총 갯수
	int countSelectSearchProduct(ProductListSearchAllVO productListSearchAllVO);
	
	// 추상 메소드 - 관리자 상품 상세(DETAIL)
	ProductDto selectOneProduct(int productNo);
	
	// 추상 메소드 - 회원용 상품 상세(DETAIL)
	ProductDetailVO selectOneProductUser(int productNo);
	
	// 추상 메소드 - 상품 번호와 이름 전체 조회
	List<ProductNoNameVO> selectNoName();
	
	// 추상 메소드 - 관리자 상품 수정(UPDATE)
	boolean updateProduct(ProductDto productDto);
	
	// 추상 메소드 - 관리자 상품 삭제(UPDATE)
	boolean inactiveProduct(int productNo, boolean isProductInactive);
	
	// 추상 메소드 - 상품 수정시 수정시간 갱신
	void updateProductRegistTime(int productNo);
	
	// 추상 메소드 - 관리자 상품 삭제(DELETE)
	boolean deleteProduct(int productNo);
	
	// 추상 메소드 - 관리자 상품 조회에서 상품 총 갯수
	// 1) 통합 조회시 상품의 총 갯수
	int countTotalProduct(ProductListSearchVO productListSearchVO);
	
	// 2) 전체 조회시 상품의 총 갯수
	int countAllProduct();
	
	// 3) 검색 조회시 상품의 총 갯수
	int countSearchProduct(ProductListSearchVO productListSearchVO);
	
	// 추상 메소드 - 회원 상품 조회에서 상품 총 갯수
	// 1) 통합 조회시 상품의 총 갯수
	int countTotalCategory(ProductListSearchCategoryVO productListSearchAllVO);
	
	// 2) 전체 조회시 상품의 총 갯수
	int countAllCategory(ProductListSearchCategoryVO productListSearchAllVO);
	
	// 3) 검색 조회시 상품의 총 갯수
	int countSearchCategory(ProductListSearchCategoryVO productListSearchAllVO);
	
	//승정 구독상품 상세옵션 상품조회
	ProductSelectNameVO selectName(int productNo);

	//상품 재고 수량변경 메소드
	boolean updateProductInventory(int paymentCount, int paymentProductNo);
}
