-- 하위 카테고리 테이블
-- 하위 카테고리 번호(category_low_no) : 번호, 기본키, 시퀀스 부여
-- 상위 카테고리 번호(category_high_no) : 번호, 외래키, 상위 카테고리 테이블(category_high)의 상위 카테고리 번호(category_high_no) 참조
-- 하위 카테고리명(category_low_name) : 문자(30byte)


-- CATEGORY_LOW 테이블 생성
create table category_low (
category_low_no number primary key,
category_high_no references category_high(category_high_no) on delete cascade,
category_low_name varchar2(30) not null
);

-- 시퀀스 생성
create sequence category_low_seq;

-- 하위 카테고리 다음 시퀀스 번호 생성
select category_high_seq.nextval from dual;

-- 하위 카테고리 생성
insert into category_low(category_high_no, category_low_no, category_low_name) values(?, ?, ?);

select b.*, p.product_name, p.product_price from basket b inner join product p on b.basket_product_no=p.product_no where basket_id=? and basket_product_no=?;

select b.*, p.product_name, p.product_price from basket b inner join product p on b.basket_product_no=p.product_no where basket_id='hello1234';


select b.*, p.product_name, p.product_price from basket b inner join product p on b.basket_product_no = p.product_no where basket_id='hello1234' order by basket_adddate desc;

select * from basket b inner join product p on b.basket_product_no = p.product_no;
