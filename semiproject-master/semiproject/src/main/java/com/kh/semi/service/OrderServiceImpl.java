package com.kh.semi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.semi.entity.PaymentDto;
import com.kh.semi.repository.BasketDao;
import com.kh.semi.repository.CouponDao;
import com.kh.semi.repository.CouponUseDao;
import com.kh.semi.repository.MemberDao;
import com.kh.semi.repository.OrdersDao;
import com.kh.semi.repository.PaymentDao;
import com.kh.semi.repository.ProductDao;
import com.kh.semi.vo.OrderVO;
import com.kh.semi.vo.PaymentVO;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private BasketDao basketDao;
	@Autowired
	private MemberDao memberDao; 
	@Autowired
	private CouponUseDao couponUseDao;
	@Autowired
	private CouponDao couponDao;
	

	@Override
	public void buy(OrderVO orderVO, Integer CouponIssue) {
		// 주문번호 시퀀스로 미리 생성 후 세팅
		int orderNo = ordersDao.sequence();
		orderVO.setOrderNo(orderNo);
		// 주문테이블에 데이터 등록
		ordersDao.insert(orderVO);
	      	      
		//결제테이블에 데이터 등록
		if(orderVO.getPayment()==null) { //단일상품일 경우
			int paymentNo = paymentDao.sequence();
		   paymentDao.insert(PaymentVO.builder()
					.paymentNo(paymentNo)
					.paymentOrderNo(orderNo)
					.paymentProductNo(orderVO.getPaymentProductNo())
					.paymentCount(orderVO.getPaymentCount())
					.paymentPrice(orderVO.getPaymentPrice())
					.paymentOption(orderVO.getPaymentOption())
					.build());
		   //productDao.updateProductInventory(orderVO);
		   productDao.updateProductInventory(orderVO.getPaymentCount(), orderVO.getPaymentProductNo());
		   basketDao.clearbasket(orderVO.getBasketNo());
		}else { //구독상품일 경우
			for(PaymentVO dto : orderVO.getPayment()) {
				// 결제번호 시퀀스 생성
				int paymentNo = paymentDao.sequence();
				paymentDao.insert(PaymentVO.builder()
						.paymentNo(paymentNo)
						.paymentOrderNo(orderNo)
						.paymentProductNo(dto.getPaymentProductNo())
						.paymentCount(dto.getPaymentCount())
						.paymentPrice(dto.getPaymentPrice())
						.paymentOption(dto.getPaymentOption())
						.build());

				//결제테이블에 해당하는 결제상품 
				//productDao.updateProductInventory(orderVO);
				productDao.updateProductInventory(dto.getPaymentCount(), dto.getPaymentProductNo());
				// 결제상품 장바구니에서 제거(장바구니 번호로 삭제)
				basketDao.clearbasket(dto.getBasketNo());
			}
		}
		
			// 적립금 사용분 회원테이블 적립금 마이너스
			memberDao.minusUsedPoint(orderVO.getOrderId(), orderVO.getOrderUsePoint());
			// 결제금액만큼 보유 포인트 차감
			memberDao.minusPayPrice(orderVO.getOrderId(), orderVO.getOrderPayPrice());
			
			if(CouponIssue != null) {			
				// 쿠폰사용했을 경우 쿠폰사용내역추가 보유쿠폰 테이블 정보 변경
				couponUseDao.insert(orderNo, CouponIssue);
				// couponIssue를 가지고 쿠폰 사용 여부를 바꾸기
				couponDao.update(CouponIssue);
			}
			else {
				return;
			}
	}
}
