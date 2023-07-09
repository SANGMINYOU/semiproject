-- 상품 테이블
-- 상품 번호(product_no) : 숫자, 기본키, 시퀀스로 부여
-- 상위 카테고리 번호(category_high_no) : 숫자, 외래키, 상위 카테고리 테이블(category_high)의 상위 카테고리 번호(category_high_no)를 참조, 
-- 하위 카테고리 번호(category_low_no) : 숫자, 외래키, 하위 카테고리 테이블(category_low)의 하위 카테고리 번호(category_low_no)를 참조, 
-- 상품 이름(product_name) : 문자(30 byte), 필수 입력
-- 상품 가격(product_price) : 숫자, 필수 입력
-- 상품 정보(product_information) : 문자(500 byte), 필수 입력
-- 상품 재고(product_inventory) : 숫자, 기본값을 0으로, 0 이상의 숫자만 입력 가능, 필수 입력
-- 상품 별점(product_good) : 숫자, 기본값은 0으로
-- 상품 등록일(product_registtime) : 날짜, 기본값은 sysdate로
-- 상품 수정일(product_updatetime) : 날짜, 상품 정보 수정시 갱신되는 항목
-- 상품 비활성화(product_inactive) : 문자(1 byte), 상품 비활성화시 'Y' 입력

-- 테이블 생성
create table product (
product_no number primary key,
category_high_no references category_high(category_high_no) on delete cascade,
category_low_no number references category_low(category_low_no) on delete cascade,
product_name varchar2(30) not null,
product_price number not null,
product_information varchar2(500) not null,
product_inventory number default 0 not null check(product_inventory >= 0),
product_good number default 0,
product_registtime date default sysdate,
product_updatetime date,
product_inactive char(1) check(product_inactive = 'Y')
); 

-- 테이블 삭제
drop table product;

-- 시퀀스 생성
create sequence product_seq;

-- 시퀀스 삭제
drop sequence product_seq;

-- 다음 시퀀스 번호 
select product_seq.nextval from dual;

-- 상품 등록
insert into product(product_no, category_high_no, category_low_no, product_name, product_price, product_information, product_inventory) values(product_seq.nextval, ?, ?, ?, ?, ?, ?);
insert into product(product_no, category_high_no, category_low_no, product_name, product_price, product_information, product_inventory) values(product_seq.nextval, 1, 2, '상품1', 10000, '상품1 테스트', 100);

-- 상위 카테고리에 연결된 하위 카테고리 조회
select category_low_no, category_low_name from category_low where category_high_no = ? order by category_low_no asc;
select category_low_no, category_low_name from category_low where category_high_no = 27 order by category_low_no asc;

-- 상품 전체 조회
select * from product order by product_no desc;

-- 상품 전체 조회 + rownum
select * from (select TMP.*, rownum rn from (select * from product order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select * from product order by product_no desc)TMP) where rn between 1 and 5;
select * from (select TMP.*, rownum rn from (select * from product order by product_no desc)TMP) where rn between 1 and 5;

-- 상품 검색 조회
select * from product where instr(#1, ?) > 0 order by product_no desc;
select * from product where instr(product_name, '테') > 0 order by product_no desc;

-- 상품 검색 조회 + rownum
select * from (select TMP.*, rownum rn from (select * from product where instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select * from product where instr(product_name, '테') > 0 order by product_no desc)TMP) where rn between 1 and 5;

-- 전체 조회시 상품 총 갯수 조회
select count(*) from product;

-- 검색 조회시 상품 총 갯수 조회 
select count(*) from product where instr(#1, ?) > 0;

-- 상위 카테고리와 하위 카테고리 번호로 전체 조회
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = ? and category_low_no = ? order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = 25 and category_low_no = 9 order by product_no desc)TMP) where rn between 1 and 10;

-- 상위 카테고리와 하위 카테고리 번호로 전체 조회 + rownum
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no, PA.category_high_sub from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = ? and P.category_low_no = ? order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no, PA.category_high_sub from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = 41 and P.category_low_no = 21 order by product_no desc)TMP) where rn between 1 and 5;

-- 상위 카테고리와 하위 카테고리 번호로 검색 조회
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = ? and category_low_no = ? and instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = 25 and category_low_no = 9 and instr(product_name, '닭') > 0 order by product_no desc)TMP) where rn between 1 and 10;

-- 상위 카테고리 번호만으로 조회
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = ? order by product_no desc)TMP) where rn between ? and ?;
select * from (select TMP.*, rownum rn from (select * from product where category_high_no = 25 order by product_no desc)TMP) where rn between 1 and 10;

-- 상위 카테고리 번호로 검색 조회
-- 상위 카테고리 전체 조회시 상품 총 갯수 조회
select count(*) from product where category_high_no = ?;
select count(*) from product where category_high_no = 25;

-- 하위 카테고리 전체 조회시 상품 총 갯수 조회
select count(*) from product where category_high_no = ?;
select count(*) from product where category_high_no = 25 and category_low_no = 8;

-- 상위 카테고리 검색 조회시 상품 총 갯수 조회
select count(*) from product where category_high_no = ? and instr(#1, ?) > 0;
select count(*) from product where category_high_no = 25 and instr(product_name, '2주') > 0;

-- 하위 카테고리 검색 조회시 상품 총 갯수 조회
select count(*) from product where category_high_no = ? and category_low_no = ? and instr(#1, ?) > 0;
select count(*) from product where category_high_no = 25 and category_low_no = 8 and instr(product_name, '뭐') > 0;

select * from product;
delete product where category_high_no = null;

-- 상품 상세 조회
select * from product where product_no = ?;
select * from product where product_no = 73;

-- 상품 수정
update product set category_high_no = ?, category_low_no = ?, product_name = ?, product_price = ?, product_information = ?, product_inventory = ? where product_no = ?;
update product set category_high_no = 24, category_low_no = 5, product_name = '테스트999', product_price = 100, product_information = 'ㅎㅇ', product_inventory = 99 where product_no = 80;

-- 상품 수정시간 갱신
update product set product_updatetime = sysdate where product_no = ?;
update product set product_updatetime = sysdate where product_no = 80;

-- 상품 삭제(비활성화)
update product set product_inactive = ? where product_no = ?;
update product set product_inactive = 'Y' where product_no = 80;


-- 상위 카테고리와 하위 카테고리 inner join한 테이블 == HL 테이블
select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21;
select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?;

-- 상품 테이블과 (상품 첨부파일 테이블 + 첨부파일 테이블)을 inner join한 테이블 == PA 테이블
select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc;

-- HL 테이블과 PA 테이블 inner join 한 테이블 == HLPA
select * from ()HL inner join ()PA on HL.product_no = PA.product_no;

-- (수정용)
-- 2) 카테고리 상품 전체 조회
-- - 하위 카테고리가 존재할 경우 : HL 테이블과 PA 테이블 inner join 한 테이블 == HLPA 에서 특정 상위 카테고리와 하위 카테고리에 대한 조회 (category_high_no, category_low_no)
select * from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;
select * from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;

-- * categoryy_high_sub, product_no, product_name, product_price, product_good, product_inactive, attachment_no만 조회 결과로 얻음
select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;
select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;

-- * 최종본 (category_high_no, category_low_no, rn 시작, rn 끝)
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc)TMP) where rn between 1 and 2; 
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc)TMP) where rn between ? and ?; 

-- - 하위 카테고리가 존재하지 않을 경우 : HL 테이블과 PA 테이블 inner join 한 테이블 == HLPA 에서 특정 상위 카테고리에 대한 조회 (category_high_no)
select * from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;
select * from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc;

-- * 최종본 (category_high_no, rn 시작, rn 끝)
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc)TMP) where rn between 1 and 10;
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no order by product_no desc)TMP) where rn between ? and ?;

-- 3) 카테고리 상품 검색 조회
-- - 하위 카테고리가 존재할 경우
-- * 최종본 (category_high_no, category_low_no, #1, 검색어, rn 시작, rn 끝)
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no where instr(PA.product_name, '5일') > 0 order by product_no desc)TMP) where rn between 1 and 2; 
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ? and L.category_low_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no where instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?; 

-- - 하위 카테고리가 존재하지 않을 경우
-- * 최종본 (category_high_no, #1, 검색어, rn 시작, rn 끝)
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = 41 and L.category_low_no = 21)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no where instr(PA.product_name, '닭') > 0 order by product_no desc)TMP) where rn between 1 and 2; 
select * from (select TMP.*, rownum rn from (select HL.category_high_sub, PA.product_no, PA.product_name, PA.product_price, PA.product_good, PA.product_inactive, PA.attachment_no from (select * from category_high H inner join category_low L on H.category_high_no = L.category_high_no where H.category_high_no = ?)HL inner join (select * from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)A on P.product_no = A.product_origin_no order by product_no desc)PA on HL.category_low_no = PA.category_low_no where instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?; 


select * from (select TMP.*, rownum rn from ()TMP) where rn between ? and ?;


-- (백업용)
-- 2) 카테고리 상품 전체 조회
-- - 하위 카테고리가 존재할 경우
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = ? and P.category_low_no = ? order by product_no desc)TMP) where rn between ? and ?

-- - 하위 카테고리가 존재하지 않을 경우
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = ? order by product_no desc)TMP) where rn between ? and ?

-- 3) 카테고리 상품 검색 조회
-- - 하위 카테고리가 존재할 경우
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = ? and P.category_low_no = ? and instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?;

-- - 하위 카테고리가 존재하지 않을 경우
select * from (select TMP.*, rownum rn from (select P.product_no, P.product_name, P.product_price, P.product_good, P.product_inactive, PA.attachment_no from product P inner join (select * from product_attachment inner join attachment on product_attachment_no = attachment_no)PA on P.product_no = PA.product_origin_no where P.category_high_no = ? and instr(#1, ?) > 0 order by product_no desc)TMP) where rn between ? and ?;

-- 정기 배송 상품을 제외한 모든 상품의 상품 번호와 상품명 조회
select product_no, product_name from product where category_high_no = 42 or category_high_no = 43;
select product_no, product_name from product where category_high_no = ? or category_high_no = ?;
