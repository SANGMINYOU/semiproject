package com.kh.semi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.semi.entity.BasketDto;
import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.ProductDao;

@Controller
public class MenuController {
	
	@Autowired
	private BasketDao basketDao;
	@Autowired
	private ProductDao productDao;
	
	//구독상품 전체목록으로 이동
	@GetMapping("/subscription")
	public String subs() {
		return "subscription/list";
	}
	
	//구독상품 상세페이지로 이동
	@GetMapping("/subscription/detail")
	public String subsDetail() {
		return "subscription/detail";
	}
	
	//옵션 선택 후 장바구니로 이동
	//장바구니 테이블 등록 필요 (2주 테이블 제거함)
	@PostMapping("/subscription/detail")
	public String detail(HttpServletRequest request, 
			@ModelAttribute BasketDto basketDto,
			HttpSession session) {
		
		// 파라미터 옵션항목값 배열로 가져오기
		String[] arrayParam = request.getParameterValues("basketProductOption");
		
		// 상품번호로 상품명 조회 후 가져오기(상품명만 나오게 toString 재정의)
		String option = "";
		for(int i=0; i<arrayParam.length; i++) {
			int productNo = Integer.parseInt(arrayParam[i]);
			option = option+productDao.selectName(productNo)+" / ";
		}
		
		//장바구니 옵션 컬럼에 들어갈 데이터 세팅
		basketDto.setBasketProductOption(option);
		
		//장바구니 테이블 데이터 등록
		basketDao.insert(basketDto);
		
		// 장바구니에 상품번호와 동일한 옵션이 있는지 확인
		// 1. 회원 아이디로 장바구니를 조회
		// 2. 번호가 있고 옵션이 있으면 수량증가
		// 3. 번호가 없거나 옵션이 다르면 등록

		return "redirect:/basket";
	}

	
	
	

}
