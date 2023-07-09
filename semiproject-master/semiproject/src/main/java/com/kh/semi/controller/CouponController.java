package com.kh.semi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.semi.repository.CouponDao;

@Controller
@RequestMapping("/coupon")
public class CouponController {

	// 의존성 주입
	@Autowired
	private CouponDao couponDao;
	
	// 이벤트 카테고리의 신규 오픈 기념 쿠폰 발급 Mapping
	@GetMapping("/open")
	public String open(HttpSession session, @RequestParam String couponId) {
		
		String memberId = (String)session.getAttribute("loginId");
		
		// 해당 회원에게 신규 오픈 기념 쿠폰 발급
		couponDao.insert(memberId);
		
		// 이벤트 카테고리 페이지로 강제 이동(redirect)
		return "redirect:/event";
	}
}
