package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.repository.ReviewDao;

@Component
public class ReviewWriteInterceptor implements HandlerInterceptor {

	@Autowired
	ReviewDao reviewDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// request에 있는 주문 번호(orderNo) 반환
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		
		// 주문 번호(orderNo)를 매개변수로 주문자 아이디 조회
		String orderId = reviewDao.selectOrderId(orderNo);
	
		// HttpSession 반환
		HttpSession session = request.getSession();
		
		// HttpSession에서 로그인 중인 회원 아이디 반환
		String loginId = (String)session.getAttribute("loginId");
		
		// 해당 주문 번호의 주문자가이 로그인 중인 작성자인지 판정
		if(orderId.equals(loginId)) {
			return true;
		}
		
		// 주문자가 작성자가 아니라면 403 에러 발생
		response.sendError(403);
		return false;
	}
	
	
}
