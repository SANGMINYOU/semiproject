-- 배송지 목록(ADDRESS) 테이블 생성
-- 상품이 배송될 주소에 대한 정보

-- 구성요소
-- 배송지 번호(address_no) 배송될 곳 각각의 주소 넘버링, 시퀀스 필요
-- 회원 아이디(address_id) 회원테이블의 member_id 참조
-- 배송지 우편번호(address_post) 숫자5~6자리   
-- 배송지 기본주소(address_base) 한글 10자 이내
-- 배송지 상세주소(address_detail) 한글 10자 이내
   
create table address (
address_no number primary key ,
address_id varchar2(20) references member(member_id) not null,
address_post varchar2(6) not null,
address_base varchar(150) not null,
address_detail varchar(150) not null
); 

create sequence address_seq;

drop table address;
