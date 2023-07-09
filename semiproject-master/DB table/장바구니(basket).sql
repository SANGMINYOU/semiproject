-- 장바구니 테이블
-- 장바구니 번호(basket_no) : primary key
-- 장바구니 아이디(basket_id) : 외래키, 회원(member) 테이블의 회원 아이디(member_id) 참조, 회원 탈퇴시 작성자를 삭제
-- 장바구니 상품번호(basket_product_no) : 상품(product)테이블의 상품번호(product_no) 참조, 상품 삭제시 함께 삭제
-- 장바구니 상품수량(basket_count_number) : 기본 1개, 1보다 큰 숫자로 조건 설정
-- 장바구니 상품추가일(basket_adddate) : sysdate 기본값
-- 장바구니 상품옵션(basekt_product_option) : null값 허용
-- 장바구니 번호(basket_no) : (+추가) primary key


-- BASKET 테이블 생성
create table basket (
basket_no number primary key,
basket_id varchar2(20) references member(member_id) on delete cascade,
basket_product_no number references product(product_no) on delete cascade,
basket_count_number number default 1 check(basket_count_number >= 1),
basket_adddate date default sysdate,
basket_product_option varchar2(300)
);

-- 테이블 삭제
drop table basket;

-- 시퀀스 생성
create sequence basket_seq;

