<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_commons.css">
 <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_footer.css">

<footer class="footer">
               <div class="topfooter">
                   <div class="inner">
                       <ul class="sns1">
                           <li><a href="#" title="페이스북"><img src="${pageContext.request.contextPath}/image/facebook.icon.png" alt="페이스복"  width="30px" height="30px" alt="페이스북"></a></li>
                           <li><a href="#" title="인스타그램"><img src="${pageContext.request.contextPath}/image/insta.icon.png" alt="인스타그램" width="30px" height="30px" alt="인스타그램"></a></li>
                           <li><a href="#" title="카카오톡"><img src="${pageContext.request.contextPath}/image/kakao.icon.png" alt="카카오톡" width="30px" height="30px" alt="카카오톡"></a></li>
                           <li><a href="${pageContext.request.contextPath}/notice/list" title="공지사항"><h3>공지사항</h3></a></li>
                       </ul> <!--//sns1-->
                   </div> <!--//inner-->
               </div> <!--//topfooter-->
               <div class="btmfooter">
                   <div class="inner">
                       <ul>
                           <li>법인명(상호) : 주식회사 이레빌딩 / 대표 : 이현재 / TEL : 010-2226-5523 / FAX : 02-1577-1004 </li>
                           <li>사업자등록번호 : 346-77-00117 / 통신판매업신고번호 : 제2022-서울당산-0005호 사업자정보확인 </li>
                           <li>주소 : 서울 영등포구 선유동2로 57 이레빌딩 / 개인정보관리책임자 : 이현재 / E-Mail : saLuv@gmail.com </li>
                           <li> Copyrightⓒ suLuv.co.kr All rights reserved.</li>
                       </ul>
                       <div id="bottom"></div> <!--리모컨 bottom 버튼 위치 설정완료-->
                       <span class="footerlog"><img class="image" width="150px;" height="150px" src="${pageContext.request.contextPath}/image/semilogo.png"></span>
                   </div> <!--//inner-->
               </div> <!--//btmfooter-->
           </footer> <!--//footer-->

	<%-- 하단 정보 
	<div align = "center">
		<h2>Session에 대한 정보</h2>
	 
		로그인 아이디 : ${sessionScope.loginId}<br>
		로그인 여부 : ${sessionScope.loginId != null}<br>
		회원 등급 : ${sessionScope.mg}<br>
		관리자인지 : ${sessionScope.mg == '관리자'}<br>
	
	</div>
			--%>
</body>
</html>