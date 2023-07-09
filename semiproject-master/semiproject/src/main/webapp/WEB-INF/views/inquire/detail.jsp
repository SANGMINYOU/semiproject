<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/header.jsp"> 
	<jsp:param value="1:1 문의글 상세 페이지" name="title"/>
</jsp:include>

<!-- 댓글 수정을 누를 때 댓글 수정창 기능 Jquery -->
<script>
	$(function(){
		//1. edit-btn을 누르면 view를 숨기고 editor를 보여준다
		$(".edit-btn").click(function(){
			$(this).parents(".view").hide();
			$(this).parents(".view").next(".editor").show();
		});
		//2. cancel-btn을 누르면 editor를 숨기고 view를 보여준다
		$(".cancel-btn").click(function(){
			$(this).parents(".editor").hide();
			$(this).parents(".editor").prev(".view").show();
		});
		//3. 처음에는 view만 보여준다
		$(".editor").hide();
});
</script>

<style>
#td-top{
	vertical-align : top;
	margin-top: 20px;
}

#th-color{
	background-color: #efefef;
}

#border tr, th{
	border: 1px solid black;
}
</style>


<div class = "container-1200">
	<div class = "row center">
	<h1>${inquireDto.inquireId}님의 문의글</h1>
	</div>
	
	<div class = "row center">
		<table class="table" id="border" >
			<tbody>
				<tr>
					<th id="th-color">문의글 번호</th>
					<td>${inquireDto.inquireNo}</td>
					<th id="th-color">문의글 작성자</th>
					<td align="center" width="35%">${inquireDto.inquireId}</td>
				</tr>
				<tr>
					<th height="60" id="th-color">문의글 제목</th>
					<td colspan="3" >${inquireDto.inquireTitle}</td>
				</tr>
				<tr>
					<th height="500" id="th-color">문의글 내용</th>
					<td colspan="3" align="left" id="td-top">${inquireDto.inquireContent}</td>
				</tr>
				<tr>
					<th id="th-color">문의글 작성일</th>
					<td>${inquireDto.inquireWritetime}</td>
					<th id="th-color">문의글 수정일</th>
					<td>${inquireDto.inquireUpdatetime}</td>
				</tr>
				<tr>
					<th id="th-color">문의글 답변 상태</th>
					<td>
						<c:choose>
							<c:when test = "${inquireDto.inquireHasReply}">
								답변 완료
							</c:when>
							<c:otherwise>
								대기중
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th id="th-color">문의글 첨부파일</th>
					<td>
						<c:forEach var = "inquireAttachmentList" items = "${inquireAttachmentList}">
							<img width = "50px" height = "50px" src = "${pageContext.request.contextPath}/attachment/download/inquireImg?attachmentNo=${inquireAttachmentList.attachmentNo}">
						</c:forEach>
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<hr><!-- 여기부턴 댓글 목록 -->
		<c:set var="admin" value="${mg == '관리자'}"></c:set>
		
		<div class = "row left">
			<h1>문의 답변</h1>
		</div>
		<div class="container-1200 row">
			<table class="table table-border">
				<c:forEach var="inquireReplyList" items="${inquireReplyList}">
					<tr class="view">
						<td class="left w-100">
							(${inquireReplyList.memberName})
							<br>
							<pre>${inquireReplyList.inquireReplyContent}</pre>
						</td>
						<!-- 댓글 수정 -->
						<c:if test="${admin}">
						<td>
							<a class="edit-btn">수정</a>
						</td>
						<!-- 댓글 삭제 -->
						<td>
							<a href="${pageContext.request.contextPath}/inquireReply/delete?inquireReplyNo=${inquireReplyList.inquireReplyNo}&inquireOriginNo=${inquireDto.inquireNo}">삭제</a>
						</td>
						</c:if>
					</tr>
					
					<!-- 댓글 수정창 -->
					<tr class = "editor"> <%-- class 이름을 editor로 변경 --%>
						<th colspan = "2">
							<form action = "inquireReply/edit" method = "post"><!-- 컨트롤러에 댓글수정 -->
								<input type = "hidden" name = "inquireReplyNo" value = "${inquireReplyList.inquireReplyNo}"><!-- 1:1문의 원본글 번호 -->
								<input type = "hidden" name = "inquireOriginNo" value = "${inquireDto.inquireNo}"><!-- 댓글 번호 -->
								<textarea name = "inquireReplyContent" rows = "5" cols = "50" required></textarea>
								<button type = "submit">변경</button>
								<a class = "cancel-btn">취소</a>
							</form>
						</th>
					</tr>
					
				</c:forEach>
			</table>
		</div>
		
		
		<!-- 댓글 쓰기  -->
		<c:if test="${admin}">
		<div class="row left">
			<h1>문의 답변 쓰기</h1>
		</div>
		<form action="inquireReply/write" method="post">
		<input name="inquireOriginNo" type ="hidden" value="${inquireDto.inquireNo}">
		
		
		<div class="row left">
			<textarea class="input w-100 fix-size" name="inquireReplyContent"></textarea>
		</div>
		
		<button type="submit">등록</button>
		</form>
		</c:if>
	</div>

</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>