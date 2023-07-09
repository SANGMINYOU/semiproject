<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="전체 상품 목록" name="title"/>
</jsp:include>

<script src="https://cdn.jsdelivr.net/gh/hiphop5782/score@latest/score.js"></script>

<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_category.css">

<style> /* 스타일 */

	#img-inactive {
		opacity : 0.5;
	}
	
	#p-inf {
		color : #900020;
		font-size: 15px;
	}
	
	#product-good {
		color : red;
	}
	
</style>

<script type="text/javascript"> /* 자바 스크립트 */

	$(function(){
	    $(".star-score").score({
	    	starColor:"red", // 별 색상 (기본 : 금색)
            integerOnly:false, // 반올림
            
            display:{
                showNumber:false, // 숫자 표시
                placeLimit:1, // 소수점 자릿수
                textColor:"red", // 숫자 색상 (기본 : 금색)
            }
	    });
	});
	
</script>

<section class="listsection1">
    <div class="inner">
        <h2 class="listitemtitle">상품 검색 결과</h2>
        <div class="listitem1">
            <ul class="itemW">
                   	<c:forEach var = "productList" items = "${productList}">
                <li>
                	<div class="listimg1 center">
                		<div class = "row">
                			<a href= "detail?productNo=${productList.productNo}&categoryHighSub=${productList.categoryHighSub}">
								<c:choose>
									<c:when test = "${productList.productInactive}">
										<img src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${productList.productAttachmentNo}" id = "img-inactive">
									</c:when>
									<c:otherwise>
										<img src="${pageContext.request.contextPath}/attachment/download/productTumbnail?attachmentNo=${productList.productAttachmentNo}">
									</c:otherwise>
								</c:choose>
							</a>
                		</div>
                		<div class = "row">
                			<a href= "detail?productNo=${productList.productNo}&categoryHighSub=${productList.categoryHighSub}">
								<span id = "p-inf">${productList.productName}</span>
							</a>
                		</div>
                		<div class = "row">
                			${productList.productPrice}원
                		</div>
                		<div class = "row">
                			<div id = "product-good" class = "star-score" data-max="5" data-rate = "${productList.productGood}"></div>
                			<span id = "product-good">${productList.productGood}</span>
                		</div>
                		
					</div>
                </li>
					</c:forEach>
            </ul>
        </div> <!--//listitem1-->
    </div> <!--//listeitemtitle-->
</section> <!--listsection1-->

<div class="footer5">
    <div class="inner">
        <div class="formdiv1">	<%-- 검색창 --%>
            <h1 class="search1">검색</h1>
            <form action="">
                <fieldset>
                    <legend>
                    	<%-- 카테고리 번호를 입력받아 조회해야하므로 검색을 위해 form에서 값을 보낼 때 하이퍼링크에서 입력받았던 카테고리 번호도 보내야 한다 --%>
                        <input type = "hidden" name = "categoryHighNo" value = "${productListSearchCategoryVO.categoryHighNo}">
						<input type = "hidden" name = "categoryLowNo" value = "${productListSearchCategoryVO.categoryLowNo}">
						<input type = "hidden" name = "type" value = "product_name" >
                        <input type="text" class="textinput1" name = "keyword" placeholder="검색어를 입력하시오." value = "${productListSearchCategoryVO.keyword}">
                        <button type = "submit" class="btnSearch1">검색</button>
                    </legend>
                </fieldset>
            </form>
        </div> <!--//formdiv-->
    </div> <!--//inner-->
</div> <!--//footer5-->



<div align = "center">

<%-- 페이지 이동 --%>
<div class = "row">
	<ul class = "pagination">
		<%-- 첫 페이지인지 판정 --%>
		<c:choose>
			<c:when test = "${productListSearchAllVO.isFirst()}"> <%-- 첫 페이지라면 --%>
				<li>
					<a href = "">
						&laquo;
					</a>  <%-- 현재 주소 유지 --%>
				</li>
			</c:when>
			<c:otherwise> <%-- 그렇지 않다면(첫 페이지가 아니라면) --%>
				<li>
					<a href = "categoryAll?pageNow=${productListSearchAllVO.blockFirst()}&${productListSearchAllVO.queryString()}">
						&laquo;
					</a> <%-- 첫 페이지로 이동 --%>
				</li>
			</c:otherwise>
		</c:choose>
		
		<%-- 이전 페이지의 존재 여부 판정 --%>
		<c:choose>
			<c:when test = "${productListSearchAllVO.hasPrev()}"> <%-- 이전 페이지가 존재한다면 --%>
				<li>
					<a href = "categoryAll?pageNow=${productListSearchAllVO.blockPrev()}&${productListSearchAllVO.queryString()}">
						&lt;
					</a> <%-- 이전 페이지로 이동 --%>
				</li>
			</c:when>
			<c:otherwise> <%-- 그렇지 않다면(이전 페이지가 존재하지 않는다면) --%>
				<li>
					<a href = "#">
						&lt;
					</a> <%-- 현재 주소 유지 --%>
				</li>
			</c:otherwise>
		</c:choose>
		
		<%-- 페이지 블럭 표시 --%>
		<c:forEach var = "i" begin = "${productListSearchAllVO.blockStart()}" end = "${productListSearchAllVO.blockEnd()}" step = "1">
			<li <c:if test = "${i == param.pageNow}">class="on"</c:if>>
				<a href = "categoryAll?pageNow=${i}&${productListSearchAllVO.queryString()}">
					${i}
				</a>
			</li>
		</c:forEach>
		
		<%-- 다음 페이지의 존재 여부 판정 --%>
		<c:choose>
			<c:when test = "${productListSearchAllVO.hasNext()}"> <%-- 다음 페이지가 존재한다면 --%>
				<li>
					<a href = "categoryAll?pageNow=${productListSearchAllVO.blockNext()}&${productListSearchAllVO.queryString()}">
						&gt;
					</a> <%-- 다음 페이지로 이동 --%>
				</li>
			</c:when>
			<c:otherwise> <%-- 그렇지 않다면(다음 페이지가 존재하지 않는다면) --%>
				<li>
					<a href = "">
						&gt;
					</a> <%-- 현재 주소 유지 --%>
				</li>	
			</c:otherwise>
		</c:choose>
		
		<%-- 마지막 페이지인지 판정 --%>
		<c:choose>
			<c:when test = "${productListSearchAllVO.isLast()}"> <%-- 마지막 페이지라면 --%>
				<li>
					<a href = "#">
						&raquo;
					</a> <%-- 현재 주소 유지 --%>
				</li>
			</c:when>
			<c:otherwise>
				<li>
					<a href = "categoryAll?pageNow=${productListSearchAllVO.blockLast()}&${productListSearchAllVO.queryString()}">
						&raquo;
					</a>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>

</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>