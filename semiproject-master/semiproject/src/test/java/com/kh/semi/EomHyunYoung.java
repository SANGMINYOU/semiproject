package com.kh.semi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.semi.entity.InquireDto;
import com.kh.semi.repository.InquireDao;
import com.kh.semi.repository.ProductDao;
import com.kh.semi.vo.InquireListSearchVO;

@SpringBootTest
public class EomHyunYoung {
	
	// 의존성 주입
	@Autowired
	ProductDao productDao;
	
	@Autowired
	InquireDao inquireDao;
	
//	@Test
//	public void test() {	
//		
//	}
	
	// 문의글 통합 조회(SELECT) 회원용
//	@Test
//	public void test() {
//		List<InquireDto> result = inquireDao.selectListUserInquire(InquireListSearchVO.builder()
//																	.type("inquire_title")
//																	.keyword("테")
//																.build(), 
//																"hello1234");
//	}
	
	// 문의글 통합 조회(SELECT) 관리자용
//	@Test
//	public void test() {
//		List<InquireDto> result = inquireDao.selectListInquire(InquireListSearchVO.builder()
//																	.type("inquire_title")
//																	.keyword("테")
//																.build());
//	}
	
	// 문의글 수정(UPDATE)
//	@Test
//	public void test() {	
//		boolean result = inquireDao.updateInquire(InquireDto.builder()
//				.inquireTitle("테스트제목")
//				.inquireContent("테스트내용")
//				.inquireNo(312)
//				.build());
//	}

//	// 문의글 등록(INSERT)
//	@Test
//	public void test() {
//		for(int i = 1 ; i <= 300 ; i ++) {
//			int inquireNo = inquireDao.nextSequence();		
//			inquireDao.writeInquire(InquireDto.builder()
//					.inquireNo(inquireNo)
//					.inquireId("hello111")
//					.inquireTitle("테스트용 문의글 "+ inquireNo +"번")
//					.inquireContent("테스트용 문의글 "+ inquireNo +"번")
//					.build()
//					);
//		}
//	}

	// 문의글 상세(DETAIL)
//	@Test
//	public void test() {	
//		InquireDto result = inquireDao.selectOneInquire(12);
//	}
	
	// 문의글 수정(UPDATE)
//	@Test
//	public void test() {	
//		boolean result = inquireDao.updateInquire(InquireDto.builder()
//				.inquireTitle("테스트제목")
//				.inquireContent("테스트내용")
//				.inquireNo(312)
//				.build());
//	}
	
//	// 문의글 삭제(DELETE)
//	@Test
//	public void test() {	
//		boolean result = inquireDao.deleteInquire(309);
//	}
	
	// 문의글 삭제(UPDATE)
//	@Test
//	public void test() {	
//		boolean result = inquireDao.inactiveInquire(304);
//	}

	// 관리자 상품 등록(INSERT)
//	@Test
//	public void test() {	
//		// 상품 등록
//		for(int i = 1 ; i <= 30 ; i ++) {
//			int productNo = productDao.nextSequence();		
//			productDao.insertProduct(ProductDto.builder()
//										.productNo(productNo)
//										.categoryHighNo(27)
//										.categoryLowNo(19)
//										.productName("테스트 상품" + i)
//										.productPrice(10000)
//										.productInformation("테스트 상품 "+ i +"의 정보")
//										.productInventory(100)
//									.build()
//									);
//		}
//	}
}
