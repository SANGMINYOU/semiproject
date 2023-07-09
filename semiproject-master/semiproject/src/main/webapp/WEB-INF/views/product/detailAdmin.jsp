<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="상품 관리" name="title"/>
</jsp:include>

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

<div class="container-600 mt-40 mb-40">
	<div class = "row center">
		<h1>${productDto.productNo}번 ${productDto.productName}</h1>
		<table class="table table-slit table-border mt-20">
			<tbody>
				<tr>
					<th width="30%">상품 번호</th>
					<td>${productDto.productNo}</td>
				</tr>
				<tr>
					<th>상위 카테고리 번호</th>
					<td>${productDto.categoryHighNo}</td>
				</tr>
				<tr>
					<th>하위 카테고리 번호</th>
					<td>${productDto.categoryLowNo}</td>
				</tr>
				<tr>
					<th>상품 이름</th>
					<td>${productDto.productName}</td>
				</tr>
				<tr>
					<th>상품 가격</th>
					<td>${productDto.productPrice}</td>
				</tr>
				<tr>
					<th>상품 정보</th>
					<td>${productDto.productInformation}</td>
				</tr>
				<tr>
					<th>상품 재고</th>
					<td>${productDto.productInventory}</td>
				</tr>
				<tr>
					<th>상품 별점</th>
					<td>${productDto.productGood}</td>
				</tr>
				<tr>
					<th>상품 등록일</th>
					<td>${productDto.productRegisttime}</td>
				</tr>
				<tr>
					<th>상품 수정일</th>
					<td>${productDto.productUpdatetime}</td>
				</tr>
				<tr>
					<th>상품 활성상태</th>
					<td>
						<c:choose>
							<c:when test = "${productDto.productInactive}">
								판매 중단
							</c:when>
							<c:otherwise>
								판매중
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>상품이미지</th>
					<td>
						<c:forEach var="productTumbnailList" items="${productTumbnailList}">
							<img width=50 height=50 src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${productTumbnailList.attachmentNo}">
						</c:forEach>
					</td>
				<tr>
					<th>상품상세이미지</th>
					<td>
						<c:forEach var="productDetailList" items="${productDetailList}">
							<img width=50 height=50 src="${pageContext.request.contextPath}/attachment/download/productDetail?attachmentNo=${productDetailList.attachmentNo}">
						</c:forEach>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="2">
						<a href = "${pageContext.request.contextPath}/admin/product/edit?productDetailNo=${productDto.productNo}">수정</a> &nbsp; &nbsp; &nbsp; | &nbsp; &nbsp; &nbsp; 
						<a href = "${pageContext.request.contextPath}/admin/product/inactive?productDetailNo=${productDto.productNo}">활성화</a> &nbsp; &nbsp; &nbsp; | &nbsp; &nbsp; &nbsp; 
						<a href = "${pageContext.request.contextPath}/admin/product/delete?productDetailNo=${productDto.productNo}">삭제</a>
					</th>
				</tr>
			</tfoot>
		</table>
		
		<div class="row right mt-10">
			<a href = "${pageContext.request.contextPath}/admin/product/list">목록</a>
		</div>
	</div>
</div>
