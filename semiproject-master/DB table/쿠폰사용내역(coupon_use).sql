-- 쿠폰사용내역 테이블
-- 사용된 쿠폰에 대한 정보

-- 구성요소
-- 기록 번호(coupon_use_no) 중복되면 안됨, 기본키, 시퀀스 부여
-- 주문 번호(coupon_use_order_no) 외래키, 주문(orders)테이블의 주문번호(order_no)참조
-- 발급 번호(coupon_origin_no) 외래키, 쿠폰리스트(coupon_list)테이블의 발급번호(coupon_issue)참조
-- 쿠폰 사용일(coupon_use_date) 쿠폰 이용 날짜


create table coupon_use(
coupon_use_no number primary key,
coupon_use_order_no references orders(order_no),
coupon_issue_no references coupon(coupon_issue),
coupon_use_date date default sysdate not null
);
                        
drop table coupon_use;

create sequence coupon_use_seq;

insert into coupon_use(coupon_use_no, coupon_use_order_no, coupon_issue_no, coupon_use_date)
values (coupon_use_seq.nextval, 100077, 2, sysdate);


commit;

select * from orders;
select * from coupon_use;
