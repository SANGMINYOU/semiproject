<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="메인페이지" name="title"/>
</jsp:include>

<script type="text/javascript">
	
 	$(function(){
		
		$("#coupon-issue").click(function(e){
			
			// 하이퍼링크를 누를 때 선택창의 선택값를 타겟으로 지정
			var isIssue = confirm("쿠폰을 발급받으시겠습니까?");
			
			// 선택값에 따라 이벤트 발생 또는 차단
			if(isIssue) { // 선택값이 true라면
				return true; // 통과
			}
			else { // false라면
				return false; // 차단
			}
		});
	}); 
	
</script>

<script src = "https://code.jquery.com/jquery-3.6.1.js"></script>

<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_event.css">

<section class="eventtitle">
    <div class="inner">
        <p>SALUV에 오신 여러분 환영합니다.<p>
        <p>${couponId}님을 위한 혜택<p>
        <div class="eventtitle1">
            <ul class="eventtitlesub">
                <li>
                	<img src="${pageContext.request.contextPath}/image/semi-banner1.png" alt="메인 홈 이벤트 배너">
                </li>
                <li>
                	<img src="${pageContext.request.contextPath}/image/semi-banner2.png" alt="메인 홈 이벤트 배너2">
                </li>
                <li>
                	<a href = "${pageContext.request.contextPath}/coupon/open?couponId=${couponId}" id = "coupon-issue">
                		<img src="${pageContext.request.contextPath}/image/coupon.png" alt="쿠폰" width="600px" heigth="300px">
                	</a>
                </li>
            </ul> <!--//eventtitlesub-->
        </div> <!--//eventtilte1-->
    </div> <!--//inner-->
</section> <!--//eventtitle-->
    

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>