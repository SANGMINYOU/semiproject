package com.kh.semi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.semi.interceptor.AdminInterceptor;
import com.kh.semi.interceptor.InquirePermissionCheckInterceptor;
import com.kh.semi.interceptor.MemberInterceptor;
import com.kh.semi.interceptor.NoticePemissionCheckInterceptor;
import com.kh.semi.interceptor.ReviewPermissionCheckInterceptor;
import com.kh.semi.interceptor.ReviewWriteInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{

	// 의존성 주입
	@Autowired
	private MemberInterceptor memberInterceptor;
	
	@Autowired
	private AdminInterceptor adminInterceptor;
	
	@Autowired
	private NoticePemissionCheckInterceptor noticeCheckInterceptor;
	
	@Autowired
	private ReviewPermissionCheckInterceptor reviewCheckInterceptor;
	
	@Autowired
	private InquirePermissionCheckInterceptor inquireCheckInterceptor;
	
	@Autowired
	private ReviewWriteInterceptor reviewWriteInterceptor;
	
	// 인터셉터 오버라이딩
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//인터셉터 작동하도록 설정(회원)
		registry.addInterceptor(memberInterceptor)
		.addPathPatterns(//인터셉터 감시주소
				"/member/**",//회원전체
				"/product/**", //상품전체
				"/notice/**", //공지전체
				"/mypage/**", //마이페이지 전체
				"/order/**", //주문 전체
				"/basket/**", //장바구니 전체
				"/review/**", //후기전체
				"/inquire/**" //문의글 전체"
				)
		.excludePathPatterns(//감시 제외 주소
				"/member/join*", //회원가입
				"/member/login", //로그인
				"/member/endSuccess", //탈퇴완료
				"/member/find_id*", //아이디 찾기
				"/member/find_pw*",//비밀번호 찾기
				"/product/category**",
				"/product/categoryAll**",
				"/product/detail**",
				"/product/detailSub**",
				"/notice/list",
				"/notice/detail"
				);
		
		//관리자용
		registry.addInterceptor(adminInterceptor)
		.addPathPatterns(//인터셉터 감시주소
				"/member/list", //회원목록
				"/member/change",//회원수정
				"/admin/**", //관리자페이지 전체
				"/notice/**"//공지사항 전체
				
				)
		.excludePathPatterns(
				"/notice/list",
				"/notice/detail"
				);
		
		//관리자만 공지사항을 등록할 수 있도록 검사하는 인터셉터
		registry.addInterceptor(noticeCheckInterceptor)
		.addPathPatterns(//인터셉터 감시주소
				"/notice/write",
				"/notice/editAdmin",
				"/notice/deleteAdmin"
				);
		
		// 로그인 중인 회원이 해당 주문 번호의 주문자 아이디일 경우 통과
		registry.addInterceptor(reviewWriteInterceptor)
		.addPathPatterns(
				"/review/write"
				);
		
		//소유자가 리뷰 수정/삭제 또는 관리자가 삭제 가능하도록 검사하는 인터셉터
		registry.addInterceptor(reviewCheckInterceptor)
		.addPathPatterns(
				"/review/delete"
				);
		
		//소유자가 문의글 수정/삭제 또는 관리자가 삭제 가능하도록 검사하는 인터셉터
		registry.addInterceptor(inquireCheckInterceptor)
		.addPathPatterns(
				"/inquire/edit",
				"/inquire/delete",
				"/inquire/detail"
				);
	}
}
