package com.kh.semi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.CouponDao;
import com.kh.semi.repository.CouponUseDao;
import com.kh.semi.repository.MemberDao;
import com.kh.semi.service.OrderService;
import com.kh.semi.vo.BasketVO;
import com.kh.semi.vo.CouponCountVO;
import com.kh.semi.vo.OrderVO;

@Controller
@RequestMapping("/order")
public class OrdersController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private BasketDao basketDao;

	//장바구니에서 주문서로 넘어오는 화면 + 쿠폰 사용 내역에서 불러오는 화면
	@GetMapping("/order_ck")
	public String order(HttpServletRequest request,
			HttpSession session, Model model) {
		// 회원 아이디 꺼내옴
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		// 주문자 정보 model로 출력준비
		model.addAttribute("memberDto", memberDao.selectOne(memberId));
		
		// basketNo로 넘어오는 값 처리
		String[] arr = request.getParameterValues("basketNo");
		if(arr!=null) { //장바구니에서 주문서로
			List<BasketVO> orderList = new ArrayList<>();
			for(int i=0; i<arr.length; i++) {
				BasketVO vo = basketDao.orderList(Integer.parseInt(arr[i]));
				orderList.add(vo);
			}
			// 넘어오는 상품만 model로 출력준비
			model.addAttribute("basketList", orderList);
		}else { //상품상세에서 주문서로
			int basketNo = (Integer)session.getAttribute("basketNo");
			model.addAttribute("purchaseList", basketDao.orderList(basketNo));
		}
				
		// 미사용 쿠폰 model로 출력준비
		//model.addAttribute("unusedCoupon", couponDao.unUsedCoupon(memberId));		
		model.addAttribute("couponUsable", couponDao.selectUsable(memberId));
		model.addAttribute("couponUse", couponDao.useCoupon(memberId));		
		return "order/order_ck";
	}

	
	@PostMapping("/order_ck")
	public String order(@ModelAttribute OrderVO orderVO, 
			HttpSession session, @RequestParam(required = false) Integer couponIssue, 
      @RequestParam(required = false)  Integer couponNo, 
      @RequestParam(required = false)  Integer couponUseNo ) {

		String memberId = (String)session.getAttribute(SessionConstant.ID);
		orderVO.setOrderId(memberId);
    
		// 전체 변수 orderVO로 받아 등록처리 + couponIssue
		orderService.buy(orderVO, couponIssue);
		return "redirect:_1";
	}
	
	//주문성공
	@GetMapping("/_1")
	public String success() {
		return "order/order_success";
	}
	
	//주문실패
	@GetMapping("/_2")
	public String fail() {
		return "order/order_fail";
	}
	
	//쿠폰 셀렉트박스 생성 Mapping - 안쓰는중 나중에 삭제필요
	@PostMapping("/coupon_select")
	public String couponSelect(Model model,  HttpSession session) {
		String memberId = (String)session.getAttribute("loginId");	
		
		model.addAttribute("couponList", couponDao.couponList(memberId));
		
		String loginId = (String) session.getAttribute("loginId");
		CouponCountVO couponMember = couponDao.selectOne(loginId);
		//3. 불러온 회원 정보를 모델에 첨부한다
		model.addAttribute("memberDto", couponMember);	
		model.addAttribute("couponUsable" , couponDao.selectCoupon(loginId));
			
		//쿠폰 셀렉트 박스 등록			
		//주문 페이지로(order_ck.jsp) 연결
		return "redirect:order_ck";
	}
}

