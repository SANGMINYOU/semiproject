package com.kh.semi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.semi.entity.NoticeDto;
import com.kh.semi.repository.NoticeDao;
import com.kh.semi.vo.NoticeListSearchVO;

@Controller
@RequestMapping("/notice")

public class NoticeController {
	//등록 수정 삭제 관리자 권한 수정 추가필요
	
	// 의존성 주입
	@Autowired
	private NoticeDao noticeDao;	
	
	// 1. 공지 글 등록 Mapping
	// 1) 공지 글 등록 페이지(write.jsp)로 연결
	@GetMapping("/write")
	public String write(HttpSession session) {
		return "/notice/write";
	
	}
	
	// 2) 공지글 등록(INSERT) 처리 (관리자)
	@PostMapping("/write")
	public String write(@ModelAttribute NoticeDto noticeDto,
						RedirectAttributes attr,
						HttpSession session
						) throws  IllegalStateException, IOException {
		
		//HttpSession에서 로그인 중인 회원 아이디를 반환
		String noticeId = (String)session.getAttribute("loginId");
		
		//반환한 회원 아이디를 문의글 작성자로 설정
		noticeDto.setNoticeId(noticeId);
		
		//공지글 번호(noticeNo)를 위해 다음 시퀀스 번호 반환
		int noticeNo = noticeDao.noticeSequence();
		
		//반환한 시퀀스 번호를 View에서 입력받은 NoticeDto의 noticeNo로 설정
		noticeDto.setNoticeNo(noticeNo);
		
		//설정한 noticeDto를 매개변수롤 공지글 등록(INSERT) 실행
		noticeDao.write(noticeDto);
		
		// 반환한 시퀀스 번호를 redirect시 파라미터의 값(value)으로 설정
		attr.addAttribute("noticeNo", noticeNo);
		
		// 작성한 문의글 상세 Mapping으로 강제 이동(redirect)
		return "redirect:detail";
	}
	
	//2. 공지글 목록 Mapping
	@GetMapping("/list")
	public String list(Model model, @ModelAttribute NoticeListSearchVO noticeListSearchVO, 
								HttpSession session) {		
		
		// 조회 유형 판정과 실행시킬 메소드를 BoardDaoImpl에서 결정하도록 변경
		// 조회 유형에 따른 조회 결과의 총 갯수를 반환
		int count = noticeDao.count(noticeListSearchVO);
		// 반환한 조회 결과의 총 갯수(count)를 noticeListSearchVO의 count 필드의 값으로 설정
		noticeListSearchVO.setCount(count);

		// model에 조회 유형에 따른 조회 결과를 첨부
		model.addAttribute("list", noticeDao.selectList(noticeListSearchVO));
		
		// 게시글 목록(list.jsp)로 연결
		return "notice/list";
	
	}
	
	//3. 공지글 상세 Mapping
	@GetMapping("/detail")
	public String detail(@RequestParam int noticeNo, Model model) {
		
		noticeDao.updateReadcount(noticeNo);
		
		NoticeDto noticeDto = noticeDao.selectOne(noticeNo);
		
		
		// 하이퍼링크로 입력받은 inquireNo로 상세 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("noticeDto", noticeDto);		
		
		// 공지글 상세 페이지(detail.jsp)로 연결
		return "notice/detail";
	
	}
	
	//4. 공지글 수정 Mapping(UPDATE) (관리자)
	@GetMapping("/editAdmin")
	public String edit(HttpSession session, @RequestParam int noticeNo, Model model, RedirectAttributes attr) {
		
		// HttpSession에서 로그인 중인 회원 아이디를 반환
		String loginId = (String)session.getAttribute("loginId");
		
		// 반환한 noticeNo를 매개변수로 상세 조회 실행 후 그 결과를 변수 noticeeDto에 저장
		NoticeDto noticeDto = noticeDao.selectOne(noticeNo);
		
		// noticeDto에서 공지 작성자(noticeId) 반환
		String noticeId = noticeDto.getNoticeId();
		
		// 수정 Mapping의 접근자가 해당 공지 작성자인지 확인(관리자인 경우)
		if(loginId.equals(noticeId)) {
			// noticeDto를 Model에 첨부
			model.addAttribute("noticeDto", noticeDto);
			
			// 문의글 수정 페이지(edit.jsp)로 연결
			return "notice/edit";
		}
		else {
			// inquireNo를 redirect시 파라미터의 값(value)으로 설정
			attr.addAttribute("noticeNo", noticeNo);
			
			// 해당 문의글의 상세 Mapping으로 강제 이동
			return "redirect:detail";
			
		}
	}	
	
	
	@PostMapping("/editAdmin")
	public String edit(@ModelAttribute NoticeDto noticeDto, RedirectAttributes attr) {
		
		// View에서 입력받은 noticeDto를 매개변수로 문의글 수정(UPDATE) 실행
		noticeDao.update(noticeDto);
		
		// noticeDto에서 공지 번호(noticeNo) 반환
		int noticeNo = noticeDto.getNoticeNo();
		
		// 반환한 공지 번호(inquireNo)를 redirect시 파라미터의 값(value)으로 설정
		attr.addAttribute("noticeNo", noticeNo);
		
		// 수정한 해당 공지 상세 Mapping으로 강제 이동(redirect)
		return "redirect:detail";
	}	
	
	//5. 공지글 삭제 Mapping (관리자)
	@GetMapping("/deleteAdmin")
	public String delete(@RequestParam int noticeNo, HttpSession session) {
		
		// 하이퍼링크로 입력받은 공지글 번호(boardNo)로 공지글 삭제(DELETE) 실행
		noticeDao.delete(noticeNo);
		
		// 공지글 삭제 후 공지글 목록 Mapping으로 강제 이동(redirect)
		return "redirect:list";
	}
	

}








