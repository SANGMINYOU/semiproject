-- 첨부파일 테이블
-- 첨부파일 번호(attachment_no) : 숫자, 기본키, 시퀀스 부여
-- 첨부파일명(attachment_name) : 문자(256byte)
-- 첨부파일 유형(attachment_type) : 문자(30byte)
-- 첨부파일 크기(attachment_size) : 숫자
-- 첨부파일 업로드 시각(attachment_date) : 날짜

-- ATTACHMENT 테이블 생성
create table attachment(
attachment_no number primary key,
attachment_name varchar2(256) not null,
attachment_type varchar2(30) not null,
attachment_size number not null check(attachment_size >= 0),
attachment_date date default sysdate not null
);

-- 테이블 삭제
drop table attachment;

-- 시퀀스 생성
create sequence attachment_seq; 

-- 시퀀스 삭제
drop sequence attachment_seq;


