<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 관리자 메뉴일 경우와 아닐 경우 다른 헤더를 설정 --%>
<c:choose>
	<c:when test="${mg == '관리자'}">
		<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
			<jsp:param value="${memberDto.memberId} 회원 상세 정보" name="title"/>
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="/WEB-INF/views/template/header.jsp">
			<jsp:param value="${memberDto.memberId} 회원 상세 정보" name="title"/>
		</jsp:include>
	</c:otherwise>
</c:choose>

<style>
/* 확장스타일 : 테두리가 있는 테이블 */
	.table.table-border {
		border:1px solid gray;
	}
	.table.table-border > thead > tr > th, 
	.table.table-border > thead > tr > td, 
	.table.table-border > tbody > tr > th, 
	.table.table-border > tbody > tr > td, 
	.table.table-border > tfoot > tr > th, 
	.table.table-border > tfoot > tr > td {
		border: 1px solid gray;
	}

/* 확장스타일 : 옆트임 테이블 */
	.table.table-slit {
		border: 3px solid gray;
	}
	.table.table-slit > thead {
		border-bottom: 2px solid gray;
	}
	.table.table-slit > tfoot {
		border-top: 2px solid gray;
	}
     
	a{
		text-decoration: none;
	}
	
	/* 테이블 텍스트 가운데 */
	th, td {
    	vertical-align : middle;
    }
    /* 확장 스타일 : 줄무늬 테이블*/
	.table > tbody > tr > th
	{
		background-color: #dfe6e9;
		background: #efefef;
	}
</style>

<div class="container-500 mt-40 mb-40">
	<div class="row center">
		<h1>${memberDto.memberId} 회원 상세정보</h1>
		<table class="table table-slit table-border mt-20" border="1" width="450">
			<tbody>
				<tr>
					<th width="25%">아이디</th>
					<td>${memberDto.memberId}</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>${memberDto.memberName}</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${memberDto.memberEmail}</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>${memberDto.memberTel}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>
						<c:if test="${memberDto.memberPost != null}">
						[${memberDto.memberPost}]
						${memberDto.memberBaseAddress}
						${memberDto.memberDetailAddress}
						</c:if>
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>${memberDto.memberBirth}</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>${memberDto.memberGender}</td>
				</tr>
				<tr>
					<th>등급</th>
					<td>${memberDto.memberGrade}</td>
				</tr>
				<tr>
					<th>포인트</th>
					<td>
						<fmt:formatNumber value="${memberDto.memberPoint}" pattern="#,##0"/> 포인트
					</td>
				</tr>
				<tr>
					<th>가입일시</th>
					<td>
						<fmt:formatDate value="${memberDto.memberJoindate}" pattern="y년 M월 d일 E a h시 m분 s초"/>
					</td>
				</tr>
				<tr>
					<th>로그인일시</th>
					<td>
						<fmt:formatDate value="${memberDto.memberLogindate}" pattern="y년 M월 d일 E a h시 m분 s초"/>
					</td>
				</tr>
			</tbody>
		</table>
		
		<c:choose>
			<c:when test="${mg == '관리자'}">
			<!-- 관리자 -->
				<div class="row mt-20">
					<a href="list">목록 보기</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
					<a href="change?memberId=${memberDto.memberId}">회원정보 변경</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
					<a href="cut?memberId=${memberDto.memberId}">회원삭제 처리</a>
				</div>
			</c:when>
			<c:otherwise>
			<!-- 회원 -->
				<div class="row mt-20">
					<a href="change_pw">비밀번호 변경</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
					<a href="information">개인정보 변경</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
					<a href="end">회원 탈퇴</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
	
<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>