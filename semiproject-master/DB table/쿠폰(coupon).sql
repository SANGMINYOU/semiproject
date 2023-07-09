-- 쿠폰 테이블
-- 발행될 쿠폰에 대한 정보

-- 구성요소
-- 쿠폰 발급번호(coupon_issue) 기본키, 중복설정방지
-- 쿠폰 번호(coupon_no) 외래키, 쿠폰리스트(coupon_list)테이블의 coupon_list_no 참조 
-- 쿠폰 아이디(coupon_id) 외래키, 회원(member)테이블의 member_id 참조
-- 쿠폰 발행일(coupon_startdate) 쿠폰 발행 날짜, 지정 설정
-- 쿠폰 만료일(coupon_enddate) 쿠폰 만료 날짜, 지정 설정
-- 쿠폰 사용여부(coupon_valid) varchar(9), 사용, 미사용, 만료 중 하나, 기본값 미사용

create table coupon(
coupon_issue number primary key,
coupon_no references coupon_list(coupon_list_no) on delete  cascade,
coupon_id references member(member_id) on delete cascade,
coupon_startdate date default sysdate not null,
coupon_enddate date not null,
coupon_valid varchar2(9) default '미사용' check(coupon_valid in ('사용', '미사용', '만료'))
);
