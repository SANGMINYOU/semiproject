<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="회원정보 변경" name="title"/>
</jsp:include>

<!-- jquery를 사용하기 위하여 라이브러리 js 파일을 불러온다-->
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="daum-post-api.js"></script>
<script type="text/javascript">
	function findAddress() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수
	
	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }
	
	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.querySelector("[name=memberPost]").value = data.zonecode
	            document.querySelector("[name=memberBaseAddress]").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.querySelector("[name=memberDetailAddress").focus();
	        }
	    }).open();
	}
	
	$(function(){
	    $(".btn-find-address").click(findAddress);
	});
</script>

<form action="change" method="post">
<input type="hidden" name="memberId" value="${memberDto.memberId}">
	
<div class="container-450 mt-30">
	<div class="row center">
		<h1>회원정보 변경</h1>
	</div>
	
	<div class="row left mt-30">
		<label>이름</label>
		<input class="input w-100" type="text" name="memberName" value="${memberDto.memberName}" required autocomplete="off">
	</div>
	
	<div class="row left mt-20">
		<label>이메일</label>
		<input class="input w-100" type="email" name="memberEmail" value="${memberDto.memberEmail}" required autocomplete="off">
	</div>
	
	<div class="row left mt-20">
		<label>전화번호</label>
		<input class="input w-100" type="tel" name="memberTel" value="${memberDto.memberTel}" required autocomplete="off">
	</div>
	
	<div class="row left mt-20">
		<label>주소</label> <br>
		<input class="input" type="text" name="memberPost" value="${memberDto.memberPost}" placeholder="우편번호" size="6" maxlength="6" autocomplete="off">
		<button type="button" class="btn btn-find-address" onclick="openZipSearch()">
			<i class="fa-solid fa-house"></i> 검색
      	</button>
	</div>
	<div class="row left">
		<input class="input w-100" type="text" name="memberBaseAddress" value="${memberDto.memberBaseAddress}" placeholder="기본주소" autocomplete="off">
	</div>
	<div class="row left">
		<input class="input w-100" type="text" name="memberDetailAddress" value="${memberDto.memberDetailAddress}" placeholder="상세주소" autocomplete="off">
	</div>
	
	<div class="row left mt-20">
		<label>등급</label>
		<c:choose>
			<c:when test="${memberDto.memberGrade == '일반'}">
				<input type="radio" name="memberGrade" value="일반" checked> 일반
			</c:when>
			<c:otherwise>
				<input type="radio" name="memberGrade" value="일반"> 일반
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${memberDto.memberGrade == 'VIP'}">
				<input type="radio" name="memberGrade" value="VIP" checked> VIP
			</c:when>
			<c:otherwise>
				<input type="radio" name="memberGrade" value="VIP"> VIP
			</c:otherwise>
		</c:choose>
	</div>
		
	<div class="row left mt-20">
		<label>포인트</label>
		<input class="input w-100" type="number" name="memberPoint" value="${memberDto.memberPoint}" autocomplete="off">
	</div>
		
	<div class="row center mt-20 mb-20">
		<button class="btn btn-negative w-100" type="submit">수정</button>
	</div>
</div>

</form>
	
<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>