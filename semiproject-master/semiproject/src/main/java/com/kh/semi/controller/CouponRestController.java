package com.kh.semi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.semi.repository.CouponDao;

@CrossOrigin(origins = {"http://127.0.0.1:5500"})
@RestController
@RequestMapping("")
public class CouponRestController {
	
	//의존성 주입
	@Autowired
	CouponDao couponDao;
	
	

}
