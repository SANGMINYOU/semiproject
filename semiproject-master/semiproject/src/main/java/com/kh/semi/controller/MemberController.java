package com.kh.semi.controller;

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

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.entity.CouponDto;
import com.kh.semi.entity.MemberDto;
import com.kh.semi.repository.CouponDao;
import com.kh.semi.repository.MemberDao;
import com.kh.semi.vo.MemberSearchVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	//회원 의존성
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private CouponDao couponDao;
	
	//회원가입
	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
	
	//회원가입 처리
	@PostMapping("/join")
	public String join(@ModelAttribute MemberDto memberDto, CouponDto couponDto) {
		//DB등록
		memberDao.insert(memberDto);
		String memberId =  memberDto.getMemberId();
		couponDao.insert(CouponDto.builder()
				.couponIssue(couponDto.getCouponIssue())
				.couponNo(couponDto.getCouponNo())
				.couponId(memberId)
				.couponStartdate(couponDto.getCouponStartdate())
				.couponEnddate(couponDto.getCouponEnddate())
				.couponValid(couponDto.getCouponValid())			
				.build()						
				);
		
		return "redirect:join_finish";
	}
	
	//회원가입완료
	@GetMapping("/join_finish")
	public String joinFinish() {
		return "member/joinFinish";
	}
	
//	//회원목록
//	@GetMapping("/list")
//	public String list(Model model, 
//			@RequestParam(required = false)	String type, 
//			@RequestParam(required = false) String keyword) {
//		boolean isSearch = type != null && keyword != null;
//		if(isSearch) {
//			model.addAttribute("list", memberDao.selectList(type, keyword));
//		}
//		else {
//			model.addAttribute("list", memberDao.selectList());
//		}
//		return "member/list";
//	}
//	
	
	//회원목록(페이징처리)
	@GetMapping("/list")
	public String list(Model model,
			@ModelAttribute(name="vo") MemberSearchVO vo) {
	
	int count = memberDao.count(vo);
	vo.setCount(count);
	
	model.addAttribute("list",memberDao.selectList(vo));
	model.addAttribute("param",vo);
	return "member/list";
	}
	
	//회원상세
	@GetMapping("/detail")
	public String detail(Model model, @RequestParam String memberId, HttpSession session) {
		
		// 세션에 들어있는 아이디를 꺼낸다
		//(참고) 세션에 데이터는 Object 형태로 저장되므로 꺼내려면 다운캐스팅 필요
		String loginId = (String) session.getAttribute("loginId");
		
		MemberDto memberDto = memberDao.selectOne(loginId);
		model.addAttribute("memberDto", memberDto);
		return "member/detail";
	}
	
	//로그인
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	//로그인 처리
	@PostMapping("/login")
	public String login(@ModelAttribute MemberDto inputDto, 
			HttpSession session) {
		//DB에서 아이디에 해당하는 정보를 가져옴
		MemberDto findDto = memberDao.selectOne(inputDto.getMemberId());
		if(findDto == null) {
			return "redirect:login?error";
		}
		
		//DB에서 가져온 비밀번호와 사용자가 입력한 비밀번호가 맞는지 비교
		boolean passwordMatch = inputDto.getMemberPw().equals(findDto.getMemberPw());
		if(passwordMatch) {
			session.setAttribute(SessionConstant.ID, inputDto.getMemberId());
			session.setAttribute(SessionConstant.GRADE, findDto.getMemberGrade());
			
			//로그인 시간 갱신
			memberDao.updateLoginTime(inputDto.getMemberId());
			
			return "redirect:/";
		}
		else {
			return "redirect:login?error";
		}
	}
	
	//정보변경
	@GetMapping("/change")
	public String change(Model model, @RequestParam String memberId) {
		model.addAttribute("memberDto", memberDao.selectOne(memberId));
		return "member/change";
	}
	
	//정보변경 처리
	@PostMapping("/change")
	public String change(@ModelAttribute MemberDto memberDto, RedirectAttributes attr) {
		boolean result = memberDao.update(memberDto);
		if(result) {
			attr.addAttribute("memberId", memberDto.getMemberId());
			return "redirect:list";
		}
		else {
			return "redirect:change_fail";
		}
	}
	
	//정보변경실패
	@GetMapping("/change_fail")
	public String changeFail() {
		return "member/changeFail";
	}
	
	//회원삭제
	@GetMapping("/cut")
	public String cut(@RequestParam String memberId) {
		boolean result = memberDao.delete(memberId);
		if(result) {
			return "redirect:list";
		}
		else {
			return "member/changeFail";
		}
	}
	
	//비밀번호변경
	@GetMapping("/change_pw")
	public String changePw() {
		return "member/changePw";
	}
	
	//비밀번호변경 처리
	@PostMapping("/change_pw")
	public String changePw(HttpSession session, 
			@RequestParam String beforePw, //기존비밀번호
			@RequestParam String afterPw) {//변경할비밀번호
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		try {
			//비밀번호검사
			MemberDto memberDto = memberDao.selectOne(memberId);
			boolean passwordMatch = beforePw.equals(memberDto.getMemberPw());
			if(!passwordMatch) {
				throw new Exception();
			}
			
			//비밀번호변경
			memberDao.changePassword(memberId, afterPw);
			return "redirect:change_pw_success";
		}
		catch (Exception e) {
			return "redirect:change_pw?error";
		}
	}
	
	//비밀번호변경완료
	@GetMapping("/change_pw_success")
	public String changePwSuccess() {
		return "member/changePwSuccess";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(SessionConstant.ID);
		session.removeAttribute(SessionConstant.GRADE);
		return "redirect:/";
	}
	
	//개인정보변경
	@GetMapping("/information")
	public String information(HttpSession session, Model model) {
		//본인 아이디 획득
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		
		//아이디로 정보 조회
		MemberDto memberDto = memberDao.selectOne(memberId);
		
		//조회한 정보 전달
		model.addAttribute("memberDto", memberDto);
		
		return "member/information";
	}
	
	//개인정보변경 처리
	@PostMapping("/information")
	public String information(HttpSession session, @ModelAttribute MemberDto inputDto, RedirectAttributes attr) {
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		inputDto.setMemberId(memberId);
		
		//비밀번호검사
		MemberDto findDto = memberDao.selectOne(memberId);
		boolean passwordMatch = inputDto.getMemberPw().equals(findDto.getMemberPw());
		if(passwordMatch) {
			//정보변경처리
			memberDao.changeInformation(inputDto);
			
			attr.addAttribute("memberId", memberId);
			return "redirect:detail";
		}
		else {
			return "redirect:information?error";
		}
	}
	
	//회원탈퇴
	@GetMapping("/end")
	public String end() {
		return "member/end";
	}
	
	@PostMapping("/end")
	public String end(HttpSession session, 
			@RequestParam String memberPw) {
		String memberId = (String)session.getAttribute(SessionConstant.ID);
		MemberDto memberDto = memberDao.selectOne(memberId);
		boolean passwordMatch = memberPw.equals(memberDto.getMemberPw());
		if(passwordMatch) {
			//회원탈퇴
			memberDao.delete(memberId);
			//로그아웃
			session.removeAttribute(SessionConstant.ID);
			session.removeAttribute(SessionConstant.GRADE);
			return "redirect:endSuccess";
		}
		else {
			return "redirect:end?error";
		}
	}
	
	@GetMapping("/endSuccess")
	public String endSuccess() {
		return "member/endSuccess";
	}
	
	@GetMapping("/find_id")
	public String findId() {
		return "member/findId";
	}
	
	@PostMapping("/find_id")
	public String findId(Model model, @ModelAttribute MemberDto inputDto, 
			HttpSession session) {
		//DB에서 회원 이름과 이메일에 해당하는 정보를 불러옴
		MemberDto findDto = memberDao.findId(
				inputDto.getMemberName(), 
				inputDto.getMemberEmail());
		if(findDto == null) {
			return "redirect:find_id?error";
		}
		else {
			model.addAttribute("memberDto", findDto);
			return "member/findIdSuccess";
		}
	}
	
	@GetMapping("/find_pw")
	public String findPw() {
		return "member/findPw";
	}
	
	@PostMapping("/find_pw")
	public String findPw(Model model, @ModelAttribute MemberDto inputDto, 
			HttpSession session) {
		//DB에서 회원 아이디, 이름, 전화번호에 해당하는 정보를 불러옴
		MemberDto findDto = memberDao.findPw(
				inputDto.getMemberId(), 
				inputDto.getMemberName(), 
				inputDto.getMemberTel());
		if(findDto == null) {
			return "redirect:find_pw?error";
		}
		else {
			model.addAttribute("memberDto", findDto);
			return "member/findPwSuccess";
		}
	}
}
