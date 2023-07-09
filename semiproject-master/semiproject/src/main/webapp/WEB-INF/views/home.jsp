<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- header.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="메인페이지" name="title"/>
</jsp:include>
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/css/SANGMIN_main.css">


<section class="section1">
                   <div class="swiper-container">
                       <div class="swiper-wrapper">
                           <!-- //slide-->
                           <div class="visual1 swiper-slide"></div>
                           <div class="visual2 swiper-slide"></div>
                           <div class="visual3 swiper-slide"></div>
                           <div class="visual4 swiper-slide"></div>
                           <div class="visual5 swiper-slide"></div>
                           <div class="visual6 swiper-slide"></div>
                       </div> <!--//swiper-wrapper-->
                   </div> <!--//swiper-container-->
           </section> <!--//section1-->
           <section class="section2">
               <div class="inner">
                   <h2 class="hotitem">이달의 구독과 좋아요 상품!</h2>
                   <div class="item1">
                       <ul>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1325&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/main2-1.jpg" alt="정기2주-1" width="200px" height="200px"></a>
                               </div>
                               <p>샐러드 + 간편식 콤보</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1322&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/main2=2.jpg" alt="정기2주-2" width="200px" height="200px"></a>
                               </div>
                               <p>트리플 콤보!!!</p>
                           </li>
                           <li>        
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1317&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/main4-1.jpg" alt="정기4주-1" width="200px" height="200px"></a>
                               </div>
                               <p>한끼 샐러드</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1320&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/main4-2.jpg" alt="정기4주-2" width="200px" height="200px"></a>
                               </div>
                               <p>하루 두번 샐러드</p>
                           </li>
                       </ul>
                   </div> <!--//item1-->
               </div> <!--//inner-->
           </section> <!--//section2-->
           <section class="section3">
               <div class="inner">
                   <h2 class="hotitem">이달의 핫한 샐러드</h2>
                   <div class="item1">
                       <ul>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1308&categoryHighSub=true"><img src="${pageContext.request.contextPath}/image/mainsalad1.jpg" alt="샐러드1" width="200px" height="200px"></a>
                               </div>
                               <p>레드칠리 로스트</p>
                           </li>
                           <li>       
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1309&categoryHighSub=true"><img src="${pageContext.request.contextPath}/image/mainsalad2.jpg" alt="샐러드2" width="200px" height="200px"></a>
                               </div>
                               <p>멕시칸 타코</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1312&categoryHighSub=true"><img src="${pageContext.request.contextPath}/image/mainsalad3.jpg" alt="샐러드3" width="200px" height="200px"></a>
                               </div>
                               <p>깻잎 닭가슴살볼</p>
                           </li>
                           <li>                
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1313&categoryHighSub=true"><img src="${pageContext.request.contextPath}/image/mainsalad4.jpg" alt="샐러드4" width="200px" height="200px"></a>                              </div>
                               <p>파스타 파스타!</p>
                           </li>
                       </ul>
                   </div> <!--//item2-->
               </div> <!--//inner-->
           </section> <!--//section3-->
           <section class="section4">
               <div class="inner">
                   <ul class="banner">
                       <li>
                           <a href="${pageContext.request.contextPath}/event">
                               <img class="bannerimg" src="${pageContext.request.contextPath}/image/semi-banner1.png">
                           </a>
                       </li>
                       <li>
                           <a href="${pageContext.request.contextPath}/event">
                               <img class="bannerimg" src="${pageContext.request.contextPath}/image/semi-banner2.png">
                           </a>
                       </li>
                   </ul> <!--banner-->
               </div> <!--inner-->
           </section> <!--section4-->
           <section class="section5">
               <div class="inner">
                   <h2 class="hotitem">한번만 먹어 본 사람 없지?</h2>
                   <div class="item1">
                       <ul>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1333&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack1.jpg" alt="스낵1" width="200px" height="200px"></a>
                               </div>
                               <p>리얼 요거트</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1334&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack2.jpg" alt="스낵2" width="200px" height="200px"></a>
                               </div>
                               <p>아이스 홍시</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1331&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack3.jpg" alt="스낵3" width="200px" height="200px"></a>
                               </div>
                               <p>오리지널 닭가슴살 소시지</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1330&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack4.jpg" alt="스낵4" width="200px" height="200px"></a>
                               </div>
                               <p>갈릭 닭가슴살 소시지</p>
                           </li>
                       </ul>
                   </div> <!--//item3-->
               </div> <!--//inner-->
           </section> <!--//section5-->
           <section class="section6">
               <div class="inner">
                   <h2 class="hotitem">만두~ 만두만두만두</h2>
                   <div class="item1">
                       <ul>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1337&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack1-1.jpg" alt="만두1-1" width="200px" height="200px"></a>
                               </div>
                               <p>오리지널 닭가슴살 만두</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1336&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack1-2.jpg" alt="만두1-2" width="200px" height="200px"></a>
                               </div>
                               <p>반반 닭가슴살 만두</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1354&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack1-3.png" alt="만두1-3" width="200px" height="200px"></a>
                               </div>
                               <p>닭가슴살 만두 3종</p>
                           </li>
                           <li>
                               <div class="img1">
                                   <a href="${pageContext.request.contextPath}/product/detail?productNo=1353&categoryHighSub=false"><img src="${pageContext.request.contextPath}/image/snack1-4.png" alt="만두1-4" width="200px" height="200px"></a>
                               </div>
                               <p>어맛 씨스루 만두(닭가슴살)</p>
                           </li>
                       </ul>
                   </div> <!--//item4-->
               </div> <!--//inner-->
           </section> <!--//section6-->
           
<div class="scrollbanner">
        <ul class="scroll1">
            <li>
                <a href="${pageContext.request.contextPath}/basket/list"><img src="${pageContext.request.contextPath}/image/cart.jpg" alt="장바구니" width="50px" height="50px"></a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/notice/list"><img src="${pageContext.request.contextPath}/image/noice1.png" alt="공지사항" width="55px" height="55px" ></a>
            </li>
        </ul> <!--//scroll1-->
        <span class="scrolltop">
            <a href="#"><i class="fa-solid fa-circle-up" style="color:#569956;"></i></a>
        </span>
        <span class="scrolldown">
            <a href="#"><i class="fa-solid fa-circle-down" style="color:#569956;"></i></a>
        </span>
		</div><!--scrollbanner-->
		<div class="popup-wrap">
    	<div class="popup">
            <img src="${pageContext.request.contextPath}/image/popup.png">
       		<button type="submit" class="btnclose">닫기</button>
    	</div> <!--popup-->
	</div>  <!--popup-wrap-->
	
	
<%-- footer.jsp 불러오기 --%>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
 
