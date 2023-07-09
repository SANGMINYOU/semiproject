package com.kh.semi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentVO {
	private int basketNo;
	private int paymentNo;
	private int paymentOrderNo;
	private int paymentProductNo;
	private int paymentCount, paymentPrice;
	private String paymentOption;
}
