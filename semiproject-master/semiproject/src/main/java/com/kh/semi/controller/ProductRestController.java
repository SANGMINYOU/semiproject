package com.kh.semi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.semi.entity.CategoryLowDto;
import com.kh.semi.repository.ProductDao;

@CrossOrigin(origins = {"http://127.0.0.1:5500"})
@RestController
@RequestMapping("/rest/product")
public class ProductRestController {

	// 의존성 주입
	@Autowired
	ProductDao productDao;
	
	// 상위 카테고리에 연결된 하위 카테고리 조회
	@GetMapping("/categoryLow")
	public List<CategoryLowDto> categoryLow(Model model, @RequestParam int categoryHighNo) {
		
		// 입력받은 상위 카테고리 번호(categoryHighNo)로 해당 상위 카테고리에 연결된 하위 카테고리 목록 조회 후 조회한 결과 반환
		return productDao.selectCategoryLowList(categoryHighNo);
	}
}
