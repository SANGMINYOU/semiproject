<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="회원가입" name="title"/>
</jsp:include>

<style>
	/* 메세지는 숨김처리 */
	.success-message,
	.fail-message { 
		display: none;
    }

	.success-message {
		font-size: 14px;
		color:green;
	}
	.fail-message {
		font-size: 14px;
		color:red;
	}

	/* 입력창의 상태에 따라 특정 메세지를 표시 */
	.input.success {
		border-color: green;
	}
	.input.success ~ .success-message {
		display: block;
	}
	.input.fail {
		border-color: red;
	}
	.input.fail ~ .fail-message {
		display: block;
	}
</style>

<!-- jquery를 사용하기 위하여 라이브러리 js 파일을 불러온다-->
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="daum-post-api.js"></script>
<script type="text/javascript">
	$(function(){

		$("input[name=memberId]").blur(function(){
			var text = $(this).val();
			var regex = /^[a-z][a-z0-9]{7,19}$/;
			var judge = regex.test(text);

			$(this).removeClass("success fail");
			if(judge){
				$(this).addClass("success");
			}
			else {
				$(this).addClass("fail");
			}
		});

		$("input[name=memberPw]").blur(function(){
			var text = $(this).val();
			var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$])[a-zA-Z0-9!@#$]{8,16}$/;
			var judge = regex.test(text);

			$(this).removeClass("success fail");
			if(judge){
				$(this).addClass("success");
			}
			else {
				$(this).addClass("fail");
			}
		});

		$("#password-check").blur(function(){
			var origin = $("input[name=memberPw]").val();
			var repeat = $(this).val();
			var judge = origin == repeat;

			$(this).removeClass("success fail");
			if(judge){
				$(this).addClass("success");
			}
			else {
				$(this).addClass("fail");
			}
		});

	});
	
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

<form action="join" method="post" enctype="multipart/form-data">
	<div class="container-450 mt-40 mb-40">
		<div class="row center">
			<h1>회원가입 정보 입력</h1>
		</div>
		
		<div class="row mt-40">
			<label>아이디</label>
			<input class="input w-100" name="memberId" type="text" onblur="checkId();" required autocomplete="off">
			<span class="success-message">멋진 아이디네요!</span>
			<span class="fail-message">영문 소문자로 시작하며 숫자가 포함된 8~20자로 작성하세요</span>
		</div>
		
		<div class="row left mt-30">
			<label>비밀번호</label>
			<input class="input w-100" name="memberPw" type="password" onblur="checkPassword();" required>
			<span class="success-message">올바른 비밀번호 형식입니다.</span>
			<span class="fail-message">8~16자로 반드시 영문 대/소문자, 숫자, 특수문자가 포함되어야 합니다.</span>
		</div>
		
		<div class="row left mt-30">
            <label>비밀번호 확인</label>
            <input type="password" class="input w-100" id="password-check" onblur="checkPasswordRe();" required>
            <span class="success-message">비밀번호가 일치합니다</span>
            <span class="fail-message">비밀번호가 일치하지 않습니다</span>
        </div>
		
		<div class="row left mt-30">
			<label>이름</label>
			<input class="input w-100" name="memberName" type="text" required autocomplete="off">
		</div>
		
		<div class="row left mt-30">
			<label>생년월일</label>
			<input class="input w-100" name="memberBirth" type="date" autocomplete="off">
		</div>
		
		<div class="row left mt-30">성별</div>
		<div>
			<input type="radio" name="memberGender" value="남자"> 남자
			<input type="radio" name="memberGender" value="여자"> 여자
		</div>

		<div class="row left mt-30">
			<label>이메일</label>
			<input class="input w-100" name="memberEmail" type="email" required autocomplete="off">
		</div>
		
		<div class="row left mt-30">
			<label>전화번호</label>
			<input class="input w-100" name="memberTel" type="tel" required autocomplete="off">
		</div>

		<div class="row left mt-30">
			<label>주소</label>
			<br>
			<input class="input" name="memberPost" type="text" maxlength="6" size="6" placeholder="우편번호" autocomplete="off">
			<button type="button" class="btn btn-find-address" onclick="openZipSearch()">
				<i class="fa-solid fa-house"></i> 검색
      		</button>
		</div>
		<div class="row left">
			<input class="input w-100" placeholder="기본주소" name="memberBaseAddress" type="text" autocomplete="off">
		</div>
		<div class="row left">
			<input class="input w-100" placeholder="상세주소" name="memberDetailAddress" type="text" autocomplete="off">
		</div>
		
		<div class="row center mt-30">
			<button type="submit" class="btn btn-positive w-100">가입하기</button>
		</div>
		
	</div>
</form>

<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>