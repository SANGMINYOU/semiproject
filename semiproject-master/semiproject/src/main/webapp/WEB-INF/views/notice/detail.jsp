<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="공지 게시판 글 수정 페이지" name="title"/>
</jsp:include>


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
	.footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
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
<div class = "container-1200">
	<div class = "row center">
	
		<table class="table" id="border" >
			<tbody>
				<tr>
					<th id="th-color">공지글 번호</th>
					<td>${noticeDto.noticeNo}</td>
					<th id="th-color">공지글 작성자</th>
					<td align="center" width="35%">${noticeDto.noticeId}</td>
				</tr>
				<tr>
					<th height="60" id="th-color">공지글 제목</th>
					<th colspan="3" >${noticeDto.noticeTitle}</th>
				</tr>
				<tr>
					<th height="300" id="th-color">공지글 내용</th>
					<td colspan="3" align="left" id="td-top">${noticeDto.noticeContent}</td>
				</tr>
				<tr>
					<th id="th-color">작성일</th>
					<td>${noticeDto.noticeWritedate}</td>
					<th id="th-color">수정일</th>
					<td>${noticeDto.noticeUpdatedate}</td>
				</tr>
				
			</tbody>
		</table>
		</div>
</div>

	
<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>