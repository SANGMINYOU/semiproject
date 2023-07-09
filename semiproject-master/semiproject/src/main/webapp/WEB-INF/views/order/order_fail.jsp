<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="주문 ERROR" name="title"/>
	</jsp:include>
	
	<div class="container-800 mt-50">
		<div class="row center">
            <h1>주문실패</h1>
        </div>

        <div class="row center mt-50">
            <h3>죄송합니다</h3>
            <h4>주문이 정상적으로 완료되지 않았습니다.</h4>
        </div>

        <div class="row center">
            <a href="${pageContext.request.contextPath}/" class="btns btns-positive">메인페이지로 이동</a>
            <a href="basket" class="btns btns-positive">장바구니로 이동</a>
        </div>

	</div>
    
    <jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>