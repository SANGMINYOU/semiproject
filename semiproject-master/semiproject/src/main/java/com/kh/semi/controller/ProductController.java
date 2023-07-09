package com.kh.semi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.semi.constant.SessionConstant;
import com.kh.semi.entity.BasketDto;
import com.kh.semi.repository.AttachmentDao;
import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.ProductDao;
import com.kh.semi.repository.ReviewDao;
import com.kh.semi.vo.ProductListSearchAllVO;
import com.kh.semi.vo.ProductListSearchCategoryVO;

@Controller
@RequestMapping("/product")
public class ProductController {

	// 의존성 주입
	@Autowired
	private ProductDao productDao;
	
	// 리뷰 의존성 주입
	@Autowired
	private ReviewDao reviewDao;
	
	//장바구니 의존성 주입
	@Autowired
	private BasketDao basketDao;
	
	//첨부파일 의존성 주입
	@Autowired
	private AttachmentDao attachmentDao;
		
	// 회원용 상품 목록 Mapping
	@GetMapping("/category")
	public String selectCategory(Model model, @ModelAttribute ProductListSearchCategoryVO productListSearchCategoryVO) {
		
		// View에서 입력받은 ProductListSearchCategoryVO를 매개변수로 조회 결과에 따른 상품의 총 수 반환
		// - 검색 조회일 경우 검색 조회의 결과에 대한 count(*)의 값 반환
		// - 전체 조회일 경우 전체 조회의 결과에 대한 count(*)의 값 반환
		int countTotalCategory = productDao.countAllCategory(productListSearchCategoryVO);
		
		// 반환한 상품의 총 수를 다시 ProductListSearchVO의 등록된 상품의 총 수(countTotalProduct) 필드의 값으로 설정
		productListSearchCategoryVO.setCountTotalProduct(countTotalCategory);
		
		// 설정된 ProductListSearchVO로 통합 조회를 실행한 후 그 결과를 Model에 첨부
		model.addAttribute("productList", productDao.selectListCategory(productListSearchCategoryVO));
		
		// 상품 목록 페이지(list.jsp)로 연결
		return "product/category";
	}
	
	// 회원용 검색창 조회 Mapping
	@GetMapping("/categoryAll")
	public String selectList(Model model, @ModelAttribute ProductListSearchAllVO productListSearchAllVO) {
		
		// View에서 입력받은 productListSearchAllVO를 매개변수로 조회 결과에 따른 상품의 총 수 반환하여 
		// ProductListSearchVO의 등록된 상품의 총 수(countTotalProduct) 필드의 값으로 설정
		productListSearchAllVO.setCountTotalProduct(productDao.countSelectSearchProduct(productListSearchAllVO));
		
		// 설정된 ProductListSearchVO로 조회를 실행한 후 그 결과를 Model에 첨부
		model.addAttribute("productList", productDao.selectSearchListProduct(productListSearchAllVO));
		
		// 상품 전체 목록 페이지(categoryAll.jsp)로 연결
		return "product/categoryAll";
	}
	
	// 회원용 상품 상세 Mapping
	// 1) 상품 상세페이지로 이동
	@GetMapping("/detail")
	public String detail(Model model, @RequestParam int productNo, @RequestParam(required = false) boolean categoryHighSub) {
		
		// 하이퍼링크로 받은 productNo로 상세 조회 실행 후 그 결과를 Model에 첨부
		model.addAttribute("productDto", productDao.selectOneProduct(productNo));
		
		//상품썸네일 이미지 뷰테이블 조회
		model.addAttribute("productTumbnailList",attachmentDao.selectProductAttachmentList(productNo));
		
		//상품썸네일 이미지 뷰테이블 조회
		model.addAttribute("productDetailList",attachmentDao.selectProductExplainList(productNo));
		
		// 모든 상품 번호와 이름을 조회하고 첨부
		model.addAttribute("productNoNameList", productDao.selectNoName());
		
		// 해당 상품에 달린 모든 리뷰 조회
		model.addAttribute("productReviewList", reviewDao.selectProductAllReview(productNo));
		
		if(categoryHighSub) {
			// 상품 상세 페이지(detail.jsp)로 연결
			return "product/detailSub";
		}
		else {
			// 상품 상세 페이지(detail.jsp)로 연결
			return "product/detail";
		}
	}

	//2) 장바구니로 이동
   @PostMapping("/detail")
   public String detail(@ModelAttribute BasketDto basketDto,
         @RequestParam int productNo,
         @RequestParam int productCount,
         HttpServletRequest request, HttpSession session) {
      // 세션아이디 꺼내와서 세팅
      String memberId = (String)session.getAttribute(SessionConstant.ID);
      basketDto.setBasketId(memberId);
      //상품번호 세팅
      basketDto.setBasketProductNo(productNo);
      //상품 수량 세팅
      basketDto.setBasketCountNumber(productCount);
      //장바구니 번호 세팅
      int basketNo = basketDao.sequence();
      basketDto.setBasketNo(basketNo);
      // 파라미터 옵션항목값 배열로 가져오기(옵션값)
      String[] arrayParam = request.getParameterValues("productOption");
      //동일한 상품이 있는지 확인 후 없으면 등록, 있으면 수량 증가
      if(basketDao.sameItem(memberId, productNo)==null) {
         if(arrayParam==null) { //단일상품 및 옵션없음
            basketDto.setBasketProductOption(""); //옵션에 빈값넣기
            
         }else { //구독상품 및 옵션있음
            // 상품번호로 상품명 조회 후 가져오기(상품명만 나오게 toString 재정의)
            String option = "";
            for(int i=0; i<arrayParam.length; i++) {
               int no = Integer.parseInt(arrayParam[i]);
               option = option+productDao.selectName(no)+" / ";
            }
            //마지막 / 구분자 문자열 자르기
            option= option.substring(0, option.length()-2);
            //장바구니 옵션 컬럼에 들어갈 데이터 세팅
            basketDto.setBasketProductOption(option);
         }
         // 등록
         basketDao.insert(basketDto);
      }else {
         // 수량 수정
         basketDao.changeOneCount(productCount,productNo);
      }
      return "redirect:/basket/list";
   }
	
}	