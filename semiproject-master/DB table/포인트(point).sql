-- 포인트(point)
-- point_no(기록번호) : 기본키
-- point_id(회원아이디) : 회원테이블의 회원아이디 참조, 회원 삭제시 포인트 소멸
-- point_type(포인트종류) : 적립 또는 사용 중 하나
-- point_point(포인트 금액) : 숫자


-- 테이블 생성
create table point(
point_no number primary key,
point_id varchar2(20) references member(member_id) on delete cascade,
point_type varchar2(6) check(point_type in ('적립', '사용')) not null,
point_point number not null,
point_content varchar2(60)
);

-- 포인트 테이블 삭제
drop table point;

-- 포인트 테이블 시퀀스 생성
create sequence point_seq;

--포인트 테이블 시퀀스 삭제
drop sequence point_seq;

commit;