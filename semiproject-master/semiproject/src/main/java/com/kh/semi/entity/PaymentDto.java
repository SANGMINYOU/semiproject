package com.kh.semi.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDto {
	private int paymentNo;
	private int paymentOrderNo;
	private int paymentProductNo;
	private int paymentCount, paymentPrice;
	private String paymentOption;
} 
