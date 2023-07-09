-- 리뷰 테이블
-- 리뷰 번호(review_no) : 숫자, 기본키, 시퀀스로 부여
-- 리뷰 작성자 아이디(review_id) : 문자(20 byte), 외래키, 회원 테이블(member)의 회원 아이디(member_id) 참조, 회원 탈퇴시 작성자가 null이 되도록
-- 리뷰 결제 번호(review_payment_no) : 숫자, 외래키, 결제 테이블(payment)의 결제 번호(payment_no) 참조, 결제 취소시 리뷰 삭제
-- 리뷰 제목(review_title) : 문자(300 byte), 필수 입력
-- 리뷰 내용(review_content) : 문자(4000 byte), 필수 입력
-- 리뷰 작성시간(review_writetime) : 날짜, 기본값은 현재 시간으로
-- 리뷰 수정시간(review_updatetime) : 날짜
-- 리뷰 별점(review_good) : 숫자, 필수 입력
-- 리뷰 좋아요(review_like) : 숫자, 기본값은 0으로, 0 이상의 숫자만 입력 가능 
-- 리뷰 비활성화(review_inactive) : 문자(1byte), 리뷰 블라인드 시 'Y' 

-- *** 리뷰 작성 여부는 결제 테이블에 있음

-- 테이블 생성
create table review (
review_no number primary key,
review_id references member(member_id) on delete set null,
review_payment_no references payment(payment_no) on delete cascade,
review_title varchar2(300) not null,
review_content varchar2(4000) not null,
review_writetime date default sysdate not null,
review_updatetime date,
review_good number check(review_good between 1 and 5) not null,
review_like number default 0 check(review_like >= 0),
review_inactive char(1) check(review_inactive = 'Y')
);

-- 테이블 삭제
drop table review;

-- 시퀀스 생성
create sequence review_seq;

-- 시퀀스 삭제
drop sequence review_seq;

-- 결제 번호에 엮여있는 주문자 아이디 조회
select P.payment_no, p.payment_product_no, O.order_id from orders O inner join payment P on o.order_no = P.payment_order_no;

select * from (select  p.product_no, p.product_name, op.* from (select py.*, o.order_id, o.order_date from payment py
inner join orders o on py.payment_order_no=o.order_no where o.order_id='hello1234') op 
INNER JOIN product p on op.payment_product_no = p.product_no) OPP 
INNER JOIN product_attachment PA on opp.product_no=pa.product_origin_no;

-- 리뷰 등록을 위해 다음 시퀀스 번호 반환
select review_seq.nextval from dual;

-- 리뷰 작성 구문
insert into review(review_no, review_payment_no, review_id, review_title, review_content, review_good) values(?, ?, ?, ?, ?, ?);
insert into review(review_no, review_payment_no, review_id, review_title, review_content, review_good) values(review_seq.nextVal, 134, 'hello1234', '테스트용 제목', '테스트용 내용', 3);

-- 리뷰 작성시 필요한 정보 조회 구문 (회원용 상품 디테일 페이지와 같이 사용)
select p.product_no, p.product_name, p.product_price, p.product_good, p.product_inactive, pa.product_attachment_no from product p inner join product_attachment pa on p.product_no = pa.product_origin_no where p.product_no = 1315;
select p.product_no, p.product_name, p.product_price, p.product_good, p.product_inactive, pa.product_attachment_no from product p inner join product_attachment pa on p.product_no = pa.product_origin_no where p.product_no = ?;

-- 리뷰 작성 전 별점의 총점과 리뷰의 갯수를 반환하기 위한 준비
select * from product p inner join payment pm on p.product_no = pm.payment_product_no;
select * from review r inner join (select * from product pd inner join payment pm on pd.product_no = pm.payment_product_no) ppm on r.review_payment_no = ppm.payment_no where ppm.product_no = 1315;

-- 리뷰 작성 전 별점의 총점
select sum(review_good) from review r inner join (select * from product pd inner join payment pm on pd.product_no = pm.payment_product_no) ppm on r.review_payment_no = ppm.payment_no where ppm.product_no = 1315;
select sum(review_good) from review r inner join (select * from product pd inner join payment pm on pd.product_no = pm.payment_product_no) ppm on r.review_payment_no = ppm.payment_no where ppm.product_no = ?;

-- 리뷰 작성 전 리뷰의 총 갯수
select count(*) from review r inner join (select * from product pd inner join payment pm on pd.product_no = pm.payment_product_no) ppm on r.review_payment_no = ppm.payment_no where ppm.product_no = 1315;
select count(*) from review r inner join (select * from product pd inner join payment pm on pd.product_no = pm.payment_product_no) ppm on r.review_payment_no = ppm.payment_no where ppm.product_no = ?;

-- 리뷰 등록 후 상품 별점 수정
update product set product_good = 1 where product_no = 1315;
update product set product_good = ? where product_no = ?;


--1) 리뷰와 결제 inner join = pr
select * from payment p inner join review r on p.payment_no = r.review_payment_no where r.review_id = 'hello111';

--2) pr과 리뷰 첨부파일 inner join >>> 첨부파일 이미지를 띄우기 위함
select * from review_attachment ra inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on ra.review_attachment_origin_no = pr.review_no;

-- 내가 작성한 특정 상품 결제 번호에 대한 리뷰 전체 - 내가 작성한 리뷰창에 나올 정보 : 주문번호, 상품명, 수량, 옵션, 상품 이미지, 제목, 내용, 작성일
-- 1) 리뷰와 리뷰 첨부파일 inner join = rra
select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no;
-- 2) 결제와 상품 inner join = ppa
select * from product p inner join payment pa on p.product_no = pa.payment_product_no;  
-- 3) rra와 ppa inner join

select * from (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra inner join (select * from product p inner join payment pa on p.product_no = pa.payment_product_no) ppa on rra.review_payment_no = ppa.payment_no;

-- (최종) 내가 쓴 리뷰 : 리뷰 번호(수정, 삭제를 위해), 주문 번호, 상품명, 수량, 옵션, 별점, 리뷰 첨부파일 이미지, 제목, 내용, 작성일, 리뷰 작성 여부
select rra.review_no, ppa.payment_order_no, ppa.product_name, ppa.payment_count, ppa.payment_option, rra.review_good, rra.review_attachment_no, rra.review_title, rra.review_content, rra.review_writetime, ppa.payment_review from (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra inner join (select * from product p inner join payment pa on p.product_no = pa.payment_product_no) ppa on rra.review_payment_no = ppa.payment_no where rra.review_id = 'hello1234' order by ppa.payment_order_no desc, ppa.payment_no asc;
select rra.review_no, ppa.payment_order_no, ppa.product_name, ppa.payment_count, ppa.payment_option, rra.review_good, rra.review_attachment_no, rra.review_title, rra.review_content, rra.review_writetime, ppa.payment_review from (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra inner join (select * from product p inner join payment pa on p.product_no = pa.payment_product_no) ppa on rra.review_payment_no = ppa.payment_no where rra.review_id = ? order by ppa.payment_order_no desc, ppa.payment_no asc;

-- (최종) 상품에 작성된 리뷰 전체 : 작성자, 작성일, 옵션, 별점, 제목, 내용, 리뷰 첨부파일 이미지
select rra.review_id, rra.review_writetime, pa.payment_option, rra.review_good, rra.review_title, rra.review_content, rra.review_attachment_no from payment pa inner join (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra on pa.payment_no = rra.review_payment_no where pa.payment_product_no = 1329 order by rra.review_writetime desc;
select rra.review_id, rra.review_writetime, pa.payment_option, rra.review_good, rra.review_title, rra.review_content, rra.review_attachment_no from payment pa inner join (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra on pa.payment_no = rra.review_payment_no where pa.payment_product_no = ? order by rra.review_writetime desc;

-- 마이페이지에서 오직 HttpSession에서 반환한 아이디로 조회할 정보에 리뷰 작성 여부가 들어있어야함 (확인 완료)
select * from (select  p.product_no, p.product_name, op.* from (select py.*, o.order_id, o.order_date from payment py inner join orders o on py.payment_order_no=o.order_no where o.order_id= 'hello1234' order by o.order_no desc) op INNER JOIN product p on op.payment_product_no = p.product_no) OPP INNER JOIN product_attachment PA on opp.product_no=pa.product_origin_no;

--



--------------------------------------

select p.payment_order_no, p.payment_count, p.payment_price, p.payment_option from payment p inner join (select * from review r inner join review_attachment ra on r.review_no = ra.review_attachment_origin_no) rra on p.payment_no = rra.review_payment_no;


select distinct * from payment p inner join review r on p.payment_no = r.review_payment_no where review_id = 'hello111' order by p.payment_order_no desc;
select distinct p.payment_order_no, p.payment_no from payment p inner join review r on p.payment_no = r.review_payment_no where review_id = 'hello111' order by p.payment_order_no desc;
-- (최종)
select distinct pr.payment_order_no, p.product_name , pr.review_title, pr.review_content, pr.review_writetime from product p inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on p.product_no = pr.payment_product_no where pr.review_id = 'hello111' and pr.payment_no = 172;
select distinct pr.payment_order_no, p.product_name, pr.review_title, pr.review_content, pr.review_writetime from product p inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on p.product_no = pr.payment_product_no where pr.review_id = ? and pr.payment_no = ?;
commit;
-- (최최종) 섬네일을 가장 첫 번째 등록된 사진으로 하기



-- 내가 작성한 특정 상품에 대한 리뷰 첨부파일 - 이미지
select ra.review_attachment_no from review_attachment ra inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on ra.review_attachment_origin_no = pr.review_no where pr.review_id = 'hello111' and pr.payment_no = 172;
select ra.review_attachment_no from review_attachment ra inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on ra.review_attachment_origin_no = pr.review_no where pr.review_id = ? and pr.payment_no = ?;


-- 내가 작성한 리뷰창에 나올 정보 : 주문번호, 상품명, 제목, 내용, 작성일, 이미지
select * from review_attachment ra inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on ra.review_attachment_origin_no = pr.review_no;
select * from review_attachment ra inner join (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr on ra.review_attachment_origin_no = pr.review_no where review_id = 'hello111';


select * from (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr where pr.review_id = 'hello1234';

select * from (select * from (select * from payment p inner join review r on p.payment_no = r.review_payment_no) pr where pr.review_id = 'hello1234') prg group by prg.payment_no;


