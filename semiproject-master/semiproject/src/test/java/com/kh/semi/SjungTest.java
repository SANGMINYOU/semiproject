package com.kh.semi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.PaymentDao;
import com.kh.semi.vo.PaymentVO;

@SpringBootTest
public class SjungTest {
	
	@Autowired
	private BasketDao basketDao;
	@Autowired
	private PaymentDao paymentDao;

	@Test
	public void test1() {
		int paymentNo = paymentDao.sequence();
		paymentDao.insert(PaymentVO.builder()
				.paymentNo(paymentNo)
				.paymentOrderNo(100123)
				.paymentProductNo(1300)
				.paymentPrice(10000)
				.paymentOption("")
				.build());
	}
	
	
}