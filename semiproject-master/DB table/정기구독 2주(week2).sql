-- 정기구독 2주 테이블(week2)
-- week2_combi(조합번호) : 숫자, 기본키, 시퀀스로 부여
-- week2_product_no : 외래키, 상품(product) 테이블의 상품번호(product_no) 참조
-- week2_member_id : 외래키, 회원(member) 테이블의 회원아이디(member_id) 참조
-- week2_option1~9 : 숫자


-- 테이블 생성
create table week2(
week2_combi number primary key,
week2_product_no references product(product_no) on delete cascade,
week2_member_id references member(member_id) on delete cascade,
week2_option1 number not null,
week2_option2 number not null,
week2_option3 number not null,
week2_option4 number not null,
week2_option5 number not null,
week2_option6 number not null,
week2_option7 number not null,
week2_option8 number not null
);

-- 테이블 삭제
drop table week2;

-- 시퀀스 삭제
drop sequence week2_seq;

-- 시퀀스 생성
create sequence week2_seq;
