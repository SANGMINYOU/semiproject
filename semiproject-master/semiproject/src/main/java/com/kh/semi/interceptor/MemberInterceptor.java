package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.constant.SessionConstant;

@Component
public class MemberInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, //요청정보
			HttpServletResponse response, //응답정보
			Object handler)//요청을 실행할 매핑 정보
			throws Exception {

		//접속한 사용자의 로그인 상태 검사
		HttpSession session = request.getSession();//접속한 사용자 로그인 상태 검사
		String loginId = (String) session.getAttribute(SessionConstant.ID);//http세션에 loginId 값이 있는지 검사
		if(loginId == null) {//비회원
			response.sendRedirect("/member/login");//로그인 페이지로 보냄
			return false;//차단
		}
		else {
			return true;//통과
		}
	}
}
