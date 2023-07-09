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
public class OrdersDto {
	private int orderNo;
	private String orderId;
	private String orderName;
	private String orderPost;
	private String orderBaseAddress;
	private String orderDetailAddress;
	private String orderTel;
	private String orderMemo;
	private Date orderDate;
	private String orderStatus;
	private Date orderChangeDate;
	private int orderPrice, orderPayPrice;
	private String orderType;
	private int orderUsePoint, orderPoint;

}
