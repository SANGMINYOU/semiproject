-- 1:1 문의 게시판(inquire)
-- inquire_no(문의글 번호) : 숫자, 기본키, 시퀀스로 부여
-- inquire_id(문의글 작성자) : 문자(20 byte), 외래키, 회원(member) 테이블의 회원 아이디(member_id) 참조, 회원 탈퇴시 작성자를 null로
-- inquire_title(문의글 제목) : 문자(300byte), 한글 100자 기준, 필수 입력
-- inquire_content(문의글 내용) : 문자(4000 byte), 필수 입력
-- inquire_writedate(문의글 작성일) : 날짜, sysdate를 기본값으로
-- inquire_updatedate(문의글 수정일) : 날짜
-- inquire_hasReply(문의글 답변 상태) : 문자(1 byte), 관리자의 댓글이 달렸는지 여부, 관리자가 작성한 댓글이 있으면 'Y'
-- inquire_inactive(문의글 비활성화) : 문자(1 byte), 문의글 삭제시 'Y'

-- 테이블 생성
create table inquire(
inquire_no number primary key,
inquire_id varchar2(20) references member(member_id) on delete set null,
inquire_title varchar2(300) not null,
inquire_content varchar2(4000) not null,
inquire_writetime date default sysdate,
inquire_updatetime date,
inquire_hasReply char(1) check(inquire_hasReply = 'Y'),
inquire_inactive char(1) check(inquire_delete = 'Y')
);

-- 테이블 삭제
drop table inquire;

-- 시퀀스 생성
create sequence inquire_seq;

-- 시퀀스 삭제
drop sequence inquire_seq;

-- 시퀀스 번호 발급
select inquire_seq.nextval from dual;

-- 문의글 등록
insert into inquire(inquire_no, inquire_id, inquire_title, inquire_content) values(inquire_seq.nextval, 'hello111', '바꿔주세요', '바꿔줘');
insert into inquire(inquire_no, inquire_id, inquire_title, inquire_content) values(?, ?, ?, ?);

-- 문의글 조회(최신순)
select * from inquire order by inquire_no desc;

-- 문의글 상세
select * from inquire where inquire_no = ?;
select * from inquire where inquire_no = 1;

-- 문의글 수정
update inquire set inquire_title = ?, inquire_content = ? where inquire_no = ?;
update inquire set inquire_title = '환불해줘Yo', inquire_content = '제발' where inquire_no = 1;

-- 문의글 삭제(DELETE)
delete inquire where inquire_no = ?;
delete inquire where inquire_no = 311;

-- 문의글 삭제(UPDATE)
update inquire set inquire_inactive = 'Y' where inquire_no = ?;
update inquire set inquire_inactive = 'Y' where inquire_no = 304;

commit;

