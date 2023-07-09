package com.kh.semi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.entity.AttachmentDto;
import com.kh.semi.entity.InquireDto;
import com.kh.semi.entity.InquireReplyDto;
import com.kh.semi.repository.AttachmentDao;
import com.kh.semi.repository.InquireDao;
import com.kh.semi.repository.InquireReplyDao;
import com.kh.semi.repository.MemberDao;
import com.kh.semi.vo.InquireListSearchVO;

@Controller
@RequestMapping("/inquire")
public class InquireController {

	// 의존성 주입
	@Autowired
	private InquireDao inquireDao;
	
	// 의존성 주입
	@Autowired
	private AttachmentDao attachmentDao;
	
	// 1:1 문의 댓글 의존성 주입
	@Autowired
	private InquireReplyDao inquireReplyDao;
	
	@Autowired
	private MemberDao memberDao; 
	
	// 문의글 이미지 첨부파일 업로드를 위한 상위 경로(parent) 설정(상위 경로에 대한 File 클래스의 인스턴스 추가)
	private final File inquireDirectory = new File("D:\\upload\\KH10A\\inquireImg");
	
	// 상위 경로 생성
	// - 상위 경로 설정(File 클래스의 인스턴스 생성 = 의존성 주입) 후 설정한 상위 경로를 실제로 생성
	@PostConstruct
	public void createDirectory() {
		inquireDirectory.mkdirs();
	}
	
	// 1. 문의글 등록 Mapping
	// 1) 문의글 등록 페이지(write.jsp)로 연결
	@GetMapping("/write")
	public String write(HttpSession session, Model model) {
		
		// HttpSession에서 로그인 중인 회원 아이디 반환
		String loginId = (String)session.getAttribute("loginId");
		model.addAttribute("memberDto", memberDao.selectOne(loginId));
		// 로그인 상태에 따라 표시되는 페이지가 다르도록
		if(loginId == null) { // 비로그인 상태라면
			// 홈 화면으로 강제 이동(redirect)
			return "redirect:/";
		}
		else { // 로그인 중이라면
			// 문의글 등록 페이지(write.jsp)로 연결
			return "inquire/write";
		}
	}
	
	// 2) 문의글 등록(INSERT) 처리
	@PostMapping("/write")
	public String write(@ModelAttribute InquireDto inquireDto, 
						RedirectAttributes attr, 
						HttpSession session, 
						List<MultipartFile> attachmentInquireImg) throws IllegalStateException, IOException {
		
		// HttpSession에서 로그인 중인 회원 아이디를 반환
		String inquireId = (String)session.getAttribute("loginId");
		
//		if(inquireId == null) {
//			return "redirect:list";
//		}
		// 반환한 회원 아이디를 문의글 작성자로 설정
		inquireDto.setInquireId(inquireId);
		
		// 문의글 번호(inquireNo)를 위해 다음 시퀀스 번호 반환
		int inquireNo = inquireDao.nextSequence();
		
		// 반환한 시퀀스 번호를 View에서 입력받은 InquireDto의 inquireNo로 설정
		inquireDto.setInquireNo(inquireNo);
		
		// 설정한 InquireDto를 매개변수로 문의글 등록(INSERT) 실행
		inquireDao.writeInquire(inquireDto);
		
		// 문의글 이미지 첨부파일 업로드
		for(MultipartFile file : attachmentInquireImg) {
			if(!file.isEmpty()) { // 첨부파일이 존재한다면
				
				// 첨부파일 등록(INSERT)을 위해 다음 시퀀스 번호 반환
				int attachmentInquireNo = attachmentDao.sequence();
				
				// Builder 패턴으로 AttachmentDto의 인스턴스를 생성 후 첨부파일 테이블(attachment)에 등록(INSERT) 실행
				attachmentDao.insert(AttachmentDto.builder()
						.attachmentNo(attachmentInquireNo)	// 첨부파일 번호는 반환한 시퀀스 번호로
						.attachmentName(file.getOriginalFilename()) // 첨부파일 이름은 전달받은 MultipartFile의 원본 파일 이름
						.attachmentType(file.getContentType()) // 첨부파일의 파일 유형은 전달받은 MultipartFile의 파일 종류
						.attachmentSize(file.getSize()) // 첨부파일 크기는 전달받은 MultipartFile의 파일 크기
					.build());
				
				// 첨부파일 업로드 경로 생성
				// - 상위 경로(parent)에 대한 File 클래스의 인스턴스와 하위 경로(child)를 나타내는 문자열(String)으로 파일을 업로드할 경로 설정
				// - 첨부파일이 실제로 저장될 때 파일 이름을 첨부파일의 시퀀스 번호로 저장하도록 한다
				File target = new File(inquireDirectory, String.valueOf(attachmentInquireNo));
				
				// 해당 경로에 업로드한 첨부파일 저장
				file.transferTo(target);
				
				// 첨부파일 업로드 후 문의글 첨부파일 테이블(inquire_attachment)에도 등록(INSERT) 실행
				attachmentDao.inquireConnectAttachment(inquireNo, attachmentInquireNo);
			}
		}
		
		// 반환한 시퀀스 번호를 redirect시 파라미터의 값(value)으로 설정
		attr.addAttribute("inquireNo", inquireNo);
		
		// 작성한 문의글 상세 Mapping으로 강제 이동(redirect)
		return "redirect:detail";
	}
	
	// 2. 문의글 목록 Mapping (회원용)
	@GetMapping("/list")
	public String selectList(Model model, @ModelAttribute InquireListSearchVO inquireListSearchVO, HttpSession session) {
		
		// HttpSession에서 로그인 중인 회원 아이디 반환
		String loginId = (String) session.getAttribute("loginId");
		
		// 회원 아이디 꺼내옴
		// 주문자 정보 model로 출력준비
		model.addAttribute("memberDto", memberDao.selectOne(loginId));
		
		// 검색 분류(type)과 검색어(keyword) 값의 존재 여부에 따라 반환한 회원 아이디로 검색 조회/전체 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("inquireList", inquireDao.selectListUserInquire(inquireListSearchVO, loginId));
		
		// 문의글 목록 페이지(list.jsp)로 연결
		return "inquire/list";
	}
	
	// 2. 문의글 목록 Mapping (관리자용)
	@GetMapping("/listAdmin")
	public String selectList(Model model, @ModelAttribute InquireListSearchVO inquireListSearchVO) {
		
		int countTotalInquire = inquireDao.countTotalInquire(inquireListSearchVO);
		
		inquireListSearchVO.setCountTotalInquireNow(countTotalInquire);
		
		// 검색 분류(type)과 검색어(keyword) 값의 존재 여부에 따라 검색 조회/전체 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("inquireList", inquireDao.selectListInquire(inquireListSearchVO));
		
		// 문의글 목록 페이지(list.jsp)로 연결
		return "inquire/listAdmin";
	}
	
	// 3. 문의글 상세 Mapping
	@GetMapping("/detail")
	public String detail(@RequestParam int inquireNo, Model model) {
	
		// 하이퍼링크로 입력받은 inquireNo로 상세 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("inquireDto", inquireDao.selectOneInquire(inquireNo));
		
		// 하이퍼링크로 입력받은 inquireNo로 첨부파일 테이블에서 해당 문의글 원본 번호를 가진 문의글 첨부파일을 전체 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("inquireAttachmentList", attachmentDao.selectInquireAttachmentList(inquireNo));
		
		// inquireNo(댓글의 원본글 번호)로 해당하는 댓글 목록 전부조회
		model.addAttribute("inquireReplyList", inquireReplyDao.selectList(inquireNo));
		
		// 문의글 상세 페이지(detail.jsp)로 연결
		return "inquire/detail";
	}
	
	// 4. 문의글 수정
	// 1) 문의글 수정 페이지(edit.jsp)로 연결
	@GetMapping("/edit")
	public String edit(HttpSession session, @RequestParam int inquireNo, Model model, RedirectAttributes attr) {
		
		// 비로그인 상태일 경우 로그인 페이지(login.jsp)으로 연결 (임시)
		// HttpSession에서 로그인 중인 회원 아이디를 반환
		String loginId = (String)session.getAttribute("loginId");
		// 비로그인 상태라면 로그인 페이지(login.jsp)로 연결
		if(loginId == null) {
			return "/member/login";
		}
		
		// 반환한 inquireNo를 매개변수로 상세 조회 실행 후 그 결과를 변수 inquireDto에 저장
		InquireDto inquireDto = inquireDao.selectOneInquire(inquireNo);
		
		// inquireDto에서 문의글 작성자(inquireId) 반환
		String inquireId = inquireDto.getInquireId();
		
		// 수정 Mapping의 접근자가 해당 문의글 작성자인지 확인
		if(loginId.equals(inquireId)) { // 작성자라면
			// inquireDto를 Model에 첨부
			model.addAttribute("inquireDto", inquireDto);
			
			// 문의글 수정 페이지(edit.jsp)로 연결
			return "inquire/edit";
		}
		else { // 작성자가 아니라면
			// inquireNo를 redirect시 파라미터의 값(value)으로 설정
			attr.addAttribute("inquireNo", inquireNo);
			
			// 해당 문의글의 상세 Mapping으로 강제 이동
			return "redirect:detail";
		}
	}
	
	// 2) 문의글 수정(UPDATE) 처리
	@PostMapping("/edit")
	public String edit(@ModelAttribute InquireDto inquireDto, RedirectAttributes attr) {
		
		// View에서 입력받은 inquireDto를 매개변수로 문의글 수정(UPDATE) 실행
		inquireDao.updateInquire(inquireDto);
		
		// inquireDto에서 문의글 번호(inquireNo) 반환
		int inquireNo = inquireDto.getInquireNo();
		
		// 반환한 문의글 번호(inquireNo)를 redirect시 파라미터의 값(value)으로 설정
		attr.addAttribute("inquireNo", inquireNo);
		
		// 수정한 해당 문의글 상세 Mapping으로 강제 이동(redirect)
		return "redirect:detail";
	}
	

	
	// 5. 문의글 삭제(비활성화) Mapping
	@GetMapping("/delete")
	public String delete(@RequestParam int inquireNo) {
		
		// 하이퍼링크로 입력받은 문의글 번호(boardNo)로 문의글 삭제(UPDATE) 실행 - 비활성화 상태를 Y로 수정
		inquireDao.inactiveInquire(inquireNo);
		
		// 상품 목록 Mapping으로 강제 이동(redirect)
		return "redirect:list";
	}
	
	// *. 문의글 삭제(DELETE) Mapping (임시)
	@GetMapping("/deleteAdmin")
	public String delete(@RequestParam int inquireNo, HttpSession session) {
		
		// 하이퍼링크로 입력받은 문의글 번호(boardNo)로 문의글 삭제(DELETE) 실행
		inquireDao.deleteInquire(inquireNo);
		
		// 문의글 삭제 후 문의글 목록 Mapping으로 강제 이동(redirect)
		return "redirect:list";
	}
	
	//1:1문의 댓글 등록(insert) 서블릿
		@PostMapping("/inquireReply/write")
		public String Replywrite(@ModelAttribute InquireReplyDto inquireReplyDto,
				HttpSession session, RedirectAttributes attr) {
			//로그인된 세션을 가져온다
			String memberId = (String) session.getAttribute(SessionConstant.ID);
			//로그인된 세션아이디를 댓글작성자로 설정한다.
			inquireReplyDto.setInquireReplyId(memberId);
			//댓글 파라미터에서 요청이 들어온 값을 DB에 집어넣는다.
			inquireReplyDao.replyWrite(inquireReplyDto);
			
			//댓글작성시 문의글 답변완료으로 변경
			inquireDao.updateHasReply(inquireReplyDto.getInquireOriginNo());
			
			//1:1문의 원본글 
			attr.addAttribute("inquireNo",inquireReplyDto.getInquireOriginNo());
			
			//댓글을 다시 문의글 상세로 이동
			return "redirect:/inquire/detail";
		}
		
	//1:1문의 댓글 삭제(delete) 서블릿
	@GetMapping("/inquireReply/delete")
	public String ReplyDelete(@RequestParam int inquireReplyNo,
							@RequestParam int inquireOriginNo , RedirectAttributes attr) {
		//1:1문의 댓글 삭제
		inquireReplyDao.replyDelete(inquireReplyNo);
		
		attr.addAttribute("inquireNo",inquireOriginNo);
		return "redirect:/inquire/detail";
	}
	
	//1:1문의 댓글 수정(update) 서블릯
	@PostMapping("/inquireReply/edit")
	public String ReplyEdit(@ModelAttribute InquireReplyDto inquireReplyDto, RedirectAttributes attr) {
		
		//1:1문의 댓글 수정 DAO
		inquireReplyDao.replyEdit(inquireReplyDto);
		
		
		attr.addAttribute("inquireNo", inquireReplyDto.getInquireOriginNo());
		return "redirect:/inquire/detail";
	}
	
}
