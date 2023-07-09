--주문 테이블
--주문하는 회원의 정보

--구성요소
-- 주문번호(order_no) : yyyymmdd0000 형식, 시퀀스번호 1000부터 생성
-- 주문자 아이디(order_id) : 영어숫자 10자이내, 위의 테이블(멤버)지워지면 이 컬럼도 삭제됨(on delete cascade)
-- 수취인 이름(order_name) : 한글 10자이내
-- 수취인 우편번호(order_post) : 숫자5~6자
-- 수취인 주소(order_base_address) : 한글 50자 이내
-- 수취인 상세주소(order_detail_address) : 한글 50자 이내
-- 수취인 휴대폰번호(order_tel) : 숫자13자 고정(010-0000-0000)
-- 배송 요청사항(order_memo) : 주문시 필요한 메모, 빈칸 가능, (최대3000byte)
-- 주문날짜(order_date) : 주문된 시점의 데이터베이스 시각, 주문시에는 주문시각으로 설정
-- 주문 상태(order_status) : 다음 항목 중 (결제완료 주문취소) 필수선택
-- 주문상태 변경일(order_changedate) : 주문 상태가 변경된 시점을 각각 sysdate로 설정
-- 할인전 금액(order_price) : 숫자, 필수 입력
-- 총 결제 금액(order_payprice) : 숫자, 필수 입력
-- 결제방법(order_type) : 한글 10자 이내
-- 적립금 사용금액(order_use_point) : 숫자
-- 적립금액(order_point) 숫자

-- 테이블 생성
create table orders(
order_no number primary key,
order_id not null unique references member(member_id) on delete cascade,
order_name varchar2(30) not null,
order_post varchar2(6) not null,
order_base_address varchar2(150) not null,
order_detail_address varchar2(150) not null,
order_tel char(13) not null, 
order_memo varchar2(3000),
order_date date default sysdate,
order_status varchar2(16) default '결제완료' not null check(order_status in('결제완료', '주문취소', '취소완료')),
order_changedate date,
order_price number not null,
order_payprice number not null,
order_type varchar2(30),
order_use_point number,
order_point number
);

-- 테이블 삭제
drop table orders;

-- 시퀀스 생성
create sequence orders_seq;

-- 시퀀스 삭제
drop sequence orders_seq;
