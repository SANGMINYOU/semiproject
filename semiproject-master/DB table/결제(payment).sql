-- 결제 테이블
-- 결제 번호(payment_no) : 하나의 주문번호로 묶여있는 여러 상품에 고유한 결제번호를 부여(시퀀스)
--                       (- 결제 번호를 이용하여 특정 주문 번호 안의 특정 상품의 결제를 부분 취소할 수 있다)
-- 결제 주문 번호(payment_order_no) : 숫자, 외래키, 주문 테이블(order)의 주문 번호(order_no) 참조, 주문 취소시 결제 주문 번호 삭제
-- 결제 상품 번호(payment_product_no) : 숫자, 외래키, 상품 테이블(product)의 상품 번호(product_no) 참조, 상품 삭제시 결제 주문 번호 삭제 
-- (** 상품 판매 불가시 해당 상품 삭제가 아닌 해당 상품을 담을 수 없도록 비활성화로 할 예정이므로 on delete 조건은 사실상 의미가 없다고 생각함)
-- 결제 상품 수량(payment_count) : 숫자, 기본값은 1로, 필수 입력
-- 결제 상품 가격(payment_price) : 숫자, 필수 입력
-- 결제 상품 옵션(payment_option) : 문자(300 byte)구독 상품일 경우 선택된 모든 옵션을 붙여서 하나의 문자열로 저장
-- 리뷰 작성 여부(payment_review) : 문자(1 byte), 리뷰 작성시 'Y' 입력

-- 테이블 생성
create table payment (
payment_no number primary key,
payment_order_no number references orders(order_no) on delete cascade,
payment_product_no number references product(product_no) on delete cascade,
payment_count number default 1 not null,
payment_price number not null,
payment_option varchar2(300),
payment_review char(1) check(payment_review in 'Y')
);

-- 리뷰 작성 여부 추가
alter table payment add payment_review char(1) check(payment_review in 'Y');

-- 테이블 삭제
drop table payment;

-- 시퀀스 생성
create sequence payment_seq;

-- 시퀀스 삭제
drop sequence payment_seq;
