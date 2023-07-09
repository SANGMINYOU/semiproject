--상품 상세설명 첨부파일
-- 상품 원본 번호(product_origin_no) : 숫자, 외래키, 상품 테이블의 상품번호 참조
-- 첨부파일 번호(product_attachment_no) : 숫자, 외래키, 첨부파일 테이블의 첨부파일 번호 참조

--테이블 생성
create table product_explain(
    product_explain_no references product(product_no) on delete cascade,
    explain_attachment_no number references attachment(attachment_no) on delete cascade
);

create view product_explain_view as
select p.explain_attachment_no, a.* from product_explain p inner join attachment a on p.explain_attachment_no=a.attachment_no;