<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="회원 관리" name="title"/>
</jsp:include>

<style>
	a{
	text-decoration: none;
	}
	
	/* 확장 스타일 : 줄무늬 테이블*/
    .table.table-slit > thead > tr,
    .table.table-slit > tfoot > tr{
    	background-color: #dfe6e9;
    }
</style>

	<div class="container-1100 mt-40 mb-40">
		<div class="row center mt-40 mb-40">
			<h1>회원 조회</h1>
		</div>
		
		<div class="row center">
		<form action="list" method="get">
			<c:choose>
				<c:when test="${param.type == 'member_name'}">
					<select name="type" required>
						<option value="member_id">아이디</option>
						<option value="member_name" selected>이름</option>
						<option value="member_tel">전화번호</option>
						<option value="member_grade">등급</option>
					</select>
				</c:when>
				<c:when test="${param.type == 'member_tel'}">
					<select name="type" required>
						<option value="member_id">아이디</option>
						<option value="member_name">이름</option>
						<option value="member_tel" selected>전화번호</option>
						<option value="member_grade">등급</option>
					</select>
				</c:when>
				<c:when test="${param.type == 'member_grade'}">
					<select name="type" required>
						<option value="member_id">아이디</option>
						<option value="member_name">이름</option>
						<option value="member_tel">전화번호</option>
						<option value="member_grade" selected>등급</option>
					</select>
				</c:when>
				<c:otherwise>
					<select name="type" required>
						<option value="member_id" selected>아이디</option>
						<option value="member_name">이름</option>
						<option value="member_tel">전화번호</option>
						<option value="member_grade">등급</option>
					</select>
				</c:otherwise>
			</c:choose>
			
			<input type="search" name="keyword" required value="${param.keyword}">
			
			<button type="submit">검색</button>
		</form>
		</div>
		
		<div class="row">
		<table class="table table-slit table-hover table-border"> <!-- border="1" width="1000"> -->
			<thead>
				<tr>
					<th>아이디</th>
					<th>이름</th>
					<th>이메일</th>
					<th>전화번호</th>
					<th>생년월일</th>
					<th>성별</th>
					<th>등급</th>
					<th>메뉴</th>
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach var="memberDto" items="${list}">
				<tr>
					<td>${memberDto.memberId}</td>
					<td>${memberDto.memberName}</td>
					<td>${memberDto.memberEmail}</td>
					<td>${memberDto.memberTel}</td>
					<td>${memberDto.memberBirth}</td>
					<td>${memberDto.memberGender}</td>
					<td>${memberDto.memberGrade}</td>
					<td>
						<a class="btn btn-neutral" style="padding:4px;" href="detail?memberId=${memberDto.memberId}">상세</a>
						<a class="btn btn-neutral" style="padding:4px;" href="change?memberId=${memberDto.memberId}">수정</a>
						<a class="btn btn-neutral" style="padding:4px;" href="cut?memberId=${memberDto.memberId}">삭제</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="8" align="right">
						총 ${list.size()}개의 결과
					</td>
				</tr>
			</tfoot>
		</table>
		</div>
		
		<div class="row center">
			<!-- 페이징 처리 -->
			<ul class="pagination">
				<li>
					<c:choose>
						<c:when test="${not vo.isFirst()}">
							<a href="list?p=${vo.firstBlock()}&${vo.parameter()}">
								<i class="fa-solid fa-angles-left"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a href="#"><i class="fa-solid fa-angles-left"></i></a>
						</c:otherwise>
					</c:choose>
				</li>
				
				<!-- 이전을 누르면 이전 구간의 마지막 페이지로 안내 -->
				<li>
					<c:choose>
						<c:when test="${vo.hasPrev()}">
							<a href="list?p=${vo.prevBlock()}&${vo.parameter()}">
								<i class="fa-solid fa-chevron-left"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a href="#"><i class="fa-solid fa-chevron-left"></i></a>
						</c:otherwise>
					</c:choose>
				</li>
				 
				<c:forEach var="i" begin="${vo.startBlock()}" end="${vo.endBlock()}" step="1">
					<li <c:if test="${i==param.p}">class="on"</c:if>><a href="list?p=${i}&${vo.parameter()}">${i}</a></li>
				</c:forEach>
				
				<!-- 다음을 누르면 다음 구간의 첫 페이지로 안내 -->
				<li>
					<c:choose>
						<c:when test="${vo.hasNext()}">
							<a href="list?p=${vo.nextBlock()}&${vo.parameter()}">
								<i class="fa-solid fa-chevron-right"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a href="#"><i class="fa-solid fa-chevron-right"></i></a>
						</c:otherwise>
					</c:choose>
				</li>
				
				<li>
					<c:choose>
						<c:when test="${not vo.isLast()}">
							<a href="list?p=${vo.lastBlock()}&${vo.parameter()}">
								<i class="fa-solid fa-angles-right"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a href="#"><i class="fa-solid fa-angles-right"></i></a>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
        </div>
		
	</div>
