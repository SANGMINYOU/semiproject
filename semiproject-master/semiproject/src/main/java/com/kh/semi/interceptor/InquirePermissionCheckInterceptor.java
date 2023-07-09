package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.entity.InquireDto;
import com.kh.semi.repository.InquireDao;

@Component
public class InquirePermissionCheckInterceptor implements HandlerInterceptor{
	
	@Autowired
	private InquireDao inquireDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 통과기준
		//1. 세션 사용자와 작성자가 일치할 경우
		//2. 관리자가 삭제하는 경우
		
		//1.
		//세션아이디
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		//문의글 번호(파라미터값에서 얻어오기)
		int inquireNo = Integer.parseInt(request.getParameter("inquireNo")); 
		InquireDto inquireDto = inquireDao.selectOneInquire(inquireNo);
		String inquireId = inquireDto.getInquireId();
		
		// 관리자 등급 반환
		String grade = (String)session.getAttribute(SessionConstant.GRADE);
		boolean isAdmin = grade.equals("관리자");
		
		if(memberId.equals(inquireId) || isAdmin) {
			return true;
		}
		
		//2.
		boolean isDelete = request.getRequestURI().equals("/inquire/delete");
		if(isAdmin && isDelete) { //관리자이면서 경로 delete가 있을 경우
			return true;
		}
		
		response.sendError(403);
		return false;
	}
}
