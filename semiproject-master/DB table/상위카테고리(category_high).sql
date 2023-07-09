-- 상위 카테고리 테이블
-- 상위 카테고리 번호(category_high_no) : 번호, 기본키, 시퀀스로 부여
-- 상위 카테고리명 (category_high_name) : 문자(30 byte), 필수 입력
-- 구독 카테고리 여부 (category_high_sub) : 문자(1 byte), 해당 상위 카테고리가 구독 상품용 카테고리인 경우 'Y'

-- 테이블 생성
create table category_high (
category_high_no number primary key,
category_high_name varchar2(30) not null,
category_high_sub char(1)
);

-- 테이블 삭제
drop table category_high;

-- 시퀀스 생성
create sequence category_high_seq;

-- 시퀀스 삭제
drop sequence category_high_seq;

-- 상위 카테고리 다음 시퀀스 번호 생성
select category_high_seq.nextval from dual;

-- 상위 카테고리 생성
insert into category_high(category_high_no, category_high_name) values (?, ?);
