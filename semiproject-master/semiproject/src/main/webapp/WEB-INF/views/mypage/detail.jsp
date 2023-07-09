<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="주문 상세" name="title"/>
	</jsp:include>
	
	<h1>주문 상세</h1>
	
    
    <c:forEach var="paymentListVO" items="${paymentListVO}">
    주문번호 : ${paymentListVO.paymentOrderNo}<br>
    결제번호 : ${paymentListVO.paymentNo}<br>
    상품명 : ${paymentListVO.productName}<br>
    상품옵션 : ${paymentListVO.paymentOption}<br>
    상품구매갯수 : ${paymentListVO.paymentCount}<br>
    상품결제가격 : ${paymentListVO.paymentPrice}<br>
    주문일자 : ${paymentListVO.orderDate}<br>
    상품이미지 : <img width=50 height=50 src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${paymentListVO.productAttachmentNo}">
    <hr>
    </c:forEach>
    
    
    
    <jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>