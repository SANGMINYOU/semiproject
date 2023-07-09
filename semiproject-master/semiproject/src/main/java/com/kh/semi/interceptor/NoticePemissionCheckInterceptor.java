package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.constant.SessionConstant;

@Component
public class NoticePemissionCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//통과 기준 - 관리자일경우
		HttpSession session = request.getSession();
		String memberGrade = (String)session.getAttribute(SessionConstant.GRADE);
		if(memberGrade.equals("관리자")) {
			return true;
		}
		
		//나머지는 다 차단
		response.sendError(403);
		return false;
	}
}
