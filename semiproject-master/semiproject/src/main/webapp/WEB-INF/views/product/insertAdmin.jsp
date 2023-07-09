<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="/WEB-INF/views/template/adminHeader.jsp">
	<jsp:param value="상품 관리" name="title"/>
</jsp:include>

<!-- 자바 스크립트 코드 -->
<script type = "text/javascript">

        $(function(){
			$("#select-categoryHigh").on("change", function(){

				// 상위 카테고리 선택창의 값(value)을 변수로 설정
				var categoryHighNo = $("#select-categoryHigh").val();

				// 하위 카테고리에 해당하는 select 태그는 유지하며 내부의 선택 항목 삭제(초기화)
				$("#select-categoryLow").empty();

				$.ajax({
					// 상위 카테고리 번호를 매개변수로 전체 조회 수행
					url:"${pageContext.request.contextPath}/rest/product/categoryLow?categoryHighNo="+categoryHighNo,
                    method:"get",
                    success:function(resp){

						// 상위 카테고리에 연결된 하위 카테고리의 수만큼 option 태그 생성
						for(var i = 0 ; i < resp.length ; i ++) {
							// 하위 카테고리 option 태그 생성 template을 변수로 설정
							var option = $("#option-categoryLow").text();
							// 임시로 작성했던 하위 카테고리 번호(categoryLowNo)를 resp의 categoryLowNo으로 변환
							option = option.replace("{{categoryLowNo}}", resp[i].categoryLowNo);
							// 임시로 작성했던 하위 카테고리 이름(categoryLowName)를 resp의 categoryLowName으로 변환
							option = option.replace("{{categoryLowName}}", resp[i].categoryLowName);
							// 변환이 끝난 option을 변수 result로 지정
							var result = $(option);
							// result를 이용하여 option 태그 생성
							$("select[name=categoryLowNo]").append(result);
						}
                    }
				});
			});
		});

</script>

<!-- 하위 카테고리 option 태그 생성 template -->
<script type = "text/template" id = "option-categoryLow">
	<option value = "{{categoryLowNo}}">{{categoryLowName}}</option>
</script>
    
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
    
    
</style>

<div class = "container-800 mt-40 mb-40">
	<div class="row center mb-20">
		<h1>상품 등록</h1>
	</div>
	
	<div class = "row center mb-40">
		
		<div class="row">
			<h3>상위 카테고리 생성</h3>
		</div>
		<form action = "createCategoryHigh" method = "post">
			<table class="table table-border table-slit">
				<tbody>
					<tr class="row left">
						<th>상위 카테고리</th>
						<td>
							상위 카테고리명 : <input name = "categoryHighName">
	            			<input type = "checkbox" name = "categoryHighSub" value = "Y"> (정기배송인지)
							<button type = "submit">생성</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>	
	</div>
			
	<div class = "row center mb-40">		
		<div class = "row">
			<h3>하위 카테고리 생성</h3>
		</div>
		<form action = "createCategoryLow" method = "post">
			<table class="table table-border table-slit">
				<tbody>
					<tr class = "row left">
						<th>하위 카테고리</th>
						<td>
							상위 카테고리명 :
							<select name = "categoryHighNo">
								<option value = "">선택</option>
								<c:forEach var = "categoryHighList" items = "${categoryHighList}">
									<option value = "${categoryHighList.categoryHighNo}">${categoryHighList.categoryHighName}</option>
								</c:forEach>
							</select>
							<br>
							하위 카테고리명 : <input name = "categoryLowName">
							<button type = "submit">생성</button>					
						</td>
					</tr>	
				</tbody>
			</table>
		</form>	
	</div>
</div>
	
<div class = "container-800 mt-40 mb-30">
	<div class="row center">
		<h3>등록 상품 정보</h3>
	</div>
	<form action = "insert" method = "post" enctype = "multipart/form-data">
		<table class="table table-border table-slit">
			<tbody>
				<tr>
					<th>상위 카테고리 번호</th>
					<td>
						<select name = "categoryHighNo" id = "select-categoryHigh">
							<option value = "">선택</option>
							<c:forEach var = "categoryHighList" items = "${categoryHighList}">
								<option value = "${categoryHighList.categoryHighNo}">${categoryHighList.categoryHighName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			
				<tr>
					<th>하위 카테고리 번호</th>
					<td>
						<select name = "categoryLowNo" id = "select-categoryLow">
							<option>선택</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th>상품 이름</th>
					<td>
						<input type = "text" name = "productName">
					</td>
				</tr>
					
				<tr>
					<th>상품 가격</th>
					<td>
						<input type = "number" name = "productPrice">
					</td>
				</tr>
				
				<tr>
					<th>상품 정보</th>
					<td>
						<textarea class="input w-100 fix-size" rows="8" name = "productInformation"></textarea>
					</td>
				</tr>	
				
				<tr>
					<th>상품 재고</th>
					<td>
						<input type = "number" name = "productInventory">
					</td>
				</tr>
				
				<tr>
					<th>상품 이미지</th>
					<td>
						<input type = "file" name = "attachmentMainImg" accept = ".png, .jpg" > 
					</td>
				</tr>
				
				<tr>
					<th>상품 상세이미지</th>
					<td>
						<input type = "file" name = "attachmentSubImg" accept = ".png, .jpg" multiple > 
					</td>
				</tr>
			</tbody>
		</table>
		<div class = "row right">
			<button type = "submit">등록</button>
		</div>
	</form>
</div>

