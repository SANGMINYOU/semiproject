package com.kh.semi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.entity.BasketDto;
import com.kh.semi.repository.AttachmentDao;
import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.ProductDao;
import com.kh.semi.vo.BasketVO;

@Controller
@RequestMapping("/basket")
public class BasketController {
	
	@Autowired
	private BasketDao basketDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private ProductDao productDao;
	
	//장바구니 등록
	@GetMapping("/insert")
	public String insert(@RequestParam int productNo,
			@RequestParam int productCount,
			HttpServletRequest request,
			HttpSession session, Model model) {
		//세션 아이디 가져옴
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		//장바구니 번호 생성
		int basketNo = basketDao.sequence();
		//바로구매 조회 시 필요한 장바구니 번호 세션에 저장
		session.setAttribute("basketNo", basketNo);
		
		BasketDto vo = new BasketDto();
		vo.setBasketId(memberId);
		vo.setBasketNo(basketNo);
		vo.setBasketProductNo(productNo);
		vo.setBasketCountNumber(productCount);
		
		String[] arrayParam = request.getParameterValues("productOption");
		if(arrayParam==null) { //단일상품 및 옵션없음
			vo.setBasketProductOption(""); //옵션에 빈값넣기  
         }else { //구독상품 및 옵션있음
            // 상품번호로 상품명 조회 후 가져오기(상품명만 나오게 toString 재정의)
        	
            String option = "";
            for(int i=0; i<arrayParam.length; i++) {
               int no = Integer.parseInt(arrayParam[i]);
               option = option+productDao.selectName(no)+" / ";
            }
            //마지막 / 구분자 문자열 자르기
            option= option.substring(0, option.length()-2);
            vo.setBasketProductOption(option);
         }
		basketDao.insert(vo);
		return "redirect:/order/order_ck";
	}
	
	//장바구니 페이지로 이동
	@GetMapping("/list")
	public String basket(HttpSession session, Model model) {
		//세션 아이디 가져옴
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		
		List<BasketVO> list = basketDao.selectList(memberId);
		// basketVO(장바구니와 상품테이블 이너조인) model로 출력준비
		model.addAttribute("basketVO", list);
		return "order/basket";
	}

	//장바구니 상품 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam int basketNo) {
		basketDao.clearbasket(basketNo);
		return "redirect:/basket/list";
	}
	
	//선택상품 삭제
	@GetMapping("/delete_2")
	@ResponseBody
	public String selectDelete(@RequestParam int basketNo) {
		basketDao.clearbasket(basketNo);
		return "success";
	}
	
	@GetMapping("/update")
	@ResponseBody
	public String update(@RequestParam int basketNo,
			@RequestParam int cnt) {
		BasketDto dto = new BasketDto();
		dto.setBasketNo(basketNo);
		dto.setBasketCountNumber(cnt);
		basketDao.changeCount(dto);
		return "success";
	}
	

}
