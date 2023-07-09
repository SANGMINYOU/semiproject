--<필수입력사항>
--member(회원) table
--아이디(member_id) : 아이디는 영문 소문자로 시작하며 영문 소문자, 숫자 조합 8~20자 이내
--비밀번호(member_pw) : 8~16자의 영문소, 대문자, 숫자, 특수문자(!@#$) 가능
--이름(member_name) : 한글 최소2자, 최대 7자
--이메일(member_email) @가 반드시 포함
--휴대폰(member_tel) : 010-0000-0000으로 설정

--주소-우편번호(member_post) : 숫자 5~6글자
--주소-기본주소(member_base_address) 
--주소-상세주소(member_detail_address)
--생년월일(member_birth) 
--성별(member_gender) : 남자, 여자
--등급(member_grade) : 일반, vip, 관리자, 일반으로 기본설정
--포인트(member_point) : 0으로 기본 설정
--가입일시(member_joindate) : 가입 시점의 시각, sysdate로 기본 설정
--로그인일시(member_logindate) : 로그인 시점의 시각
-- 회원 탈퇴 여부 (member_withdrawal) : 문자(1byte), 기본적으로 null이며 회원 탈퇴 요청시에 문자열 Y가 입력되도록 (회원 탈퇴를 회원 정보 삭제가 아닌 비활성화로 할 예정)
-- 회원 탈퇴 일시 (member_withdrawaldate) : 날짜, 회원 탈퇴 요청시에 sysdate 값이 입력되도록

-- 테이블 생성
create table member(
member_id varchar2(20) primary key check(regexp_like(member_id, '^[a-z][a-z0-9]{7,19}$') and regexp_like(member_id, '[a-z]') and regexp_like(member_id, '[0-9]')),
member_pw varchar2(16) not null check(regexp_like(member_pw, '^[a-zA-Z0-9!@#$]{8,16}$') and regexp_like(member_pw, '[a-z]') and regexp_like(member_pw, '[A-Z]') and regexp_like(member_pw, '[0-9]') and regexp_like(member_pw, '[!@#$]')),
member_name varchar2(21) not null check(regexp_like(member_name,'^[가-힣]{2,7}$')),
member_email varchar2(30) not null check(regexp_like(member_email, '@')),
member_tel char(13) not null check(regexp_like(member_tel, '^010-[0-9]{4}-[0-9]{4}$')),
member_post varchar2(6) check(regexp_like(member_post, '^[0-9]{5,6}$')),
member_base_address varchar2(150),
member_detail_address varchar2(150),
member_birth date,
member_gender varchar2(6) check(member_gender in ('남자', '여자')),
member_grade varchar2(9) default '일반' not null check(member_grade in ('일반', 'VIP', '관리자')),
member_point number default 0 check(member_point >= 0),
member_joindate date default sysdate not null,
member_logindate date,
member_withdrawal char(1) check(member_withdrawal = 'Y'),
member_withdrawaldate date
);

-- 테이블 삭제
drop table member;

-- 시험입력
insert into member(
member_id, member_pw, member_name, member_email, 
member_tel, member_post, member_base_address, member_detail_address, 
member_birth, member_gender, member_grade)
values(
'tester1', '1234', '관리자', 'admin@kh.com', 
'010-0000-0000', 39960, '경기도 성남시 판교역로', '166 카카오 판교아지트 A동 3층', 
'1980-01-01', '남자', '관리자'
);

-- 저장
commit;

-- 전체 테이블 삭제
SELECT 'DROP TABLE "' || TABLE_NAME || '" CASCADE CONSTRAINTS;' FROM user_tables;

DROP TABLE "TEST" CASCADE CONSTRAINTS;
DROP TABLE "TEST1" CASCADE CONSTRAINTS;
DROP TABLE "MEMBER" CASCADE CONSTRAINTS;
DROP TABLE "COUPON" CASCADE CONSTRAINTS;
DROP TABLE "COUPON_LIST" CASCADE CONSTRAINTS;
DROP TABLE "ATTACHMENT" CASCADE CONSTRAINTS;
DROP TABLE "CATEGORY_HIGH" CASCADE CONSTRAINTS;
DROP TABLE "CATEGORY_LOW" CASCADE CONSTRAINTS;
DROP TABLE "PRODUCT" CASCADE CONSTRAINTS;
DROP TABLE "PRODUCT_ATTACHMENT" CASCADE CONSTRAINTS;
DROP TABLE "ADDRESS" CASCADE CONSTRAINTS;
DROP TABLE "NOTICE" CASCADE CONSTRAINTS;
DROP TABLE "INQUIRE" CASCADE CONSTRAINTS;
DROP TABLE "INQUIRE_REPLY" CASCADE CONSTRAINTS;
DROP TABLE "INQUIRE_ATTACHMENT" CASCADE CONSTRAINTS;
DROP TABLE "ORDERS" CASCADE CONSTRAINTS;
DROP TABLE "BASKET" CASCADE CONSTRAINTS;
DROP TABLE "PAYMENT" CASCADE CONSTRAINTS;
DROP TABLE "REVIEW" CASCADE CONSTRAINTS;
DROP TABLE "REVIEW_ATTACHMENT" CASCADE CONSTRAINTS;
