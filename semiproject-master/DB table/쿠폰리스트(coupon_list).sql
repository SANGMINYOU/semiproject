 -- 쿠폰리스트 테이블
-- 쿠폰발행 현황 정보(관리자 쿠폰 발급)

-- 구성요소
-- 쿠폰 번호(coupon_list_no) 기본키, 시퀀스 부여
-- 쿠폰 이름(coupon_name)  한글10자이내, 필수 입력
-- 쿠폰 할인율(coupon_discount) 숫자, 필수 입력
-- 쿠폰 정보(coupon_info) 쿠폰 발급정보 한글 20자 이내, 필수 입력

create table coupon_list (
coupon_list_no number primary key,
coupon_name varchar2(30) not null,
coupon_discount number not null,
coupon_info varchar2(60) not null
);

create sequence coupon_list_no_seq;

insert into coupon_list(coupon_list_no, coupon_name, coupon_discount, coupon_info) 
values (coupon_list_no_seq.nextval, '신규가입쿠폰', 3000, '주문금액 3만원이상 이용가능');

commit;

select * from coupon_list;

drop table coupon_list;
