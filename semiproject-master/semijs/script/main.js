$(function(){
    preventDefaultAnchor(); 
    // 메뉴 클릭 이벤트 
    const menubtn = $('.menubtn'); 
    const bottommenu = $('.bottommenu');
        menubtn.on('click', function(){
        if ($(this).hasClass('on')) {
        $(this).removeClass('on');
        bottommenu.slideUp(); 
        } 
        else {
        $(this).addClass('on');
        bottommenu.slideDown(); 
        }
    });

    // 슬라이드 이미지
    var visual= new Swiper('.swiper-container', {
        direction:'horizontal',
        loop:true,    
        
    autoplay:{
        delay:2700,
        },
    
    });

    // 플로팅 배너
    var floatPosition = parseInt($(".scrollbanner").css('top'))
    
    // scroll 인식
    $(window).scroll(function(){
        // 현재 스크롤 위치
        var currentTop =
        $(window).scrollTop();
        var bannerTop = currentTop + floatPosition + "px";

        // 이동 애니메이션
        $(".scrollbanner").stop().animate({
            "top":bannerTop
        }, 500);

    })

    // scroll top 이동 
    const scrolltop = $('.scrolltop'); 
    const scrollbottom = $('.scrolldown');

    scrolltop.click(() => {
        $( 'html, body' ).animate( { scrollTop : 0 }, 400 );
        return false;
    } );

    // scroll bottom 이동
    const scrollHeight = $(document).height(); 
    scrollbottom.click(() => {
        $('body,html').animate({
            scrollTop: scrollHeight
        }, 800); 
        return false;
    });


    // popup창 닫기 이벤트
    $(function(){

        $(".btnclose").click(function(){
            $(".popup").addClass("hidden");
        });
    });

});

    // 상품별 별 평점
    $(function(){
        $(".itemstar-score").score({
            starColor:"red",
            integerOnly:false,
            display:{
                showNumber:true,
                placeLimit:2,
                textColor:"red"
            }
        });
    });



    // href 에 # 넣으면 기본기능 잠금
    function preventDefaultAnchor() {
        $(document).on('click', 'a[href="#"]', function(e) {
            e.preventDefault();
        });
    }