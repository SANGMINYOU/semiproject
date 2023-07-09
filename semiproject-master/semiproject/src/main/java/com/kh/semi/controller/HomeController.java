package com.kh.semi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/event")
	public String event(HttpSession session, Model model) {
		
		// HttpSession에서 회원 아이디 반환
		String loginId = (String)session.getAttribute("loginId");
		
		// 반환한 회원 아이디를 모델에 첨부
		model.addAttribute("couponId", loginId);
		
		// 이벤트 페이지(event.jsp)로 연결
		return "event";
	}
}
