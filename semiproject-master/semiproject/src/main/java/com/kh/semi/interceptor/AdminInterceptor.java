package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.constant.SessionConstant;

@Component
public class AdminInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		String mg = (String)session.getAttribute(SessionConstant.GRADE);
		boolean admin = mg != null && mg.equals("관리자");
		
		if(admin) {//관리자 통과
			return true;
		}
		else {//관리자아니면 차단
			response.sendError(403);
			return false;
		}
	}
}
