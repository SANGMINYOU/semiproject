<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

	/* 확장 스타일 : 줄무늬 테이블*/
	.table > tbody > tr > th
	{
		background-color: #dfe6e9;
		width: 200px;
		background: #efefef;
	}
	
	/* 테이블 텍스트 가운데 */
	th, td {
    	vertical-align : middle;
    }
    
    .input{
    	font-size: 14px
    }
    
    .footer{
		position:fixed;
		bottom:0;
		left:0;
		width:100%;
	}
</style>

<div class="container-700 mt-40 mb-40">
	<div class="row">
		<form action = "edit" method = "post">
			<%-- 상품 정보 수정을 위한 상품 번호(값을 유지한 채로 다시 form에 전송하기 위해 hidden으로 설정) --%>
			<input type = "hidden" name = "productNo" value = "${productDto.productNo}">
			<div class = "row mb-20 center">
				<h1>상품 수정</h1>
			</div>
				<table class="table table-border table-slit">
					<tr>
						<th>상위 카테고리</th>
						<td>
							<input type = "number" name = "categoryHighNo" value = "${productDto.categoryHighNo}">
						</td>
					</tr>
					
					<tr>
						<th>하위 카테고리</th>
						<td>
							<input type = "number" name = "categoryLowNo" value = "${productDto.categoryLowNo}">
						</td>
					</tr>
					
					<tr>
						<th>상품 이름</th>
						<td>
							<input type = "text" name = "productName" value = "${productDto.productName}">
						</td>
					</tr>
					
					<tr>
						<th>상품 가격</th>
						<td>
							<input type = "text" name = "productPrice" value = "${productDto.productPrice}">
						</td>
					</tr>
					
					<tr>
						<th>상품 정보</th>
						<td>
							<textarea class="input w-100 fix-size" rows="8" name = "productInformation">${productDto.productInformation}</textarea>
						</td>
					</tr>
					
					<tr>
						<th>상품 재고</th>
						<td>
							<input type = "text" name = "productInventory" value = "${productDto.productInventory}">
						</td>
					</tr>
					
				</table>
				<div class="row right">
					<button type = "submit">수정</button>
				</div>
		</form>
	</div>
</div>

