package com.kh.semi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.repository.ReviewDao;
import com.kh.semi.vo.ReviewVO;

@Component
public class ReviewPermissionCheckInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//통과기준 
		// 1. 세션 사용자와 작성자가 일치할 경우
		//2. 관리자가 삭제하는 경우
		
		//1.
		//세션아이디
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		//리뷰번호(파라미터값에서 얻어오기)
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo")); 
		ReviewVO reviewVO = reviewDao.selectOneReview(reviewNo);
		String reviewId = reviewVO.getReviewId();
		
		if(memberId.equals(reviewId)) {
			return true;
		}
		
		//2.
		String grade = (String)session.getAttribute(SessionConstant.GRADE);
		boolean isAdmin = grade.equals("관리자");
		boolean isDelete = request.getRequestURI().equals("/review/delete");
		if(isAdmin && isDelete) { //관리자이면서 경로 delete가 있을 경우
			return true;
		}
		
		//기본 차단
		response.sendError(403);
		return false;
		
	}
}
