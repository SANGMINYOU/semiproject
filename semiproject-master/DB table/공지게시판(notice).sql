--공지게시판(notice)
--notice_no(공지글 번호) : 숫자, 기본키 시퀀스로 부여
--notice_id(작성자) : 회원의 아이디 정보, 외래키로 설정, 탈퇴하면 작성자를 null로 변경
--notice_title(제목) :  한글 최대 100자 기준으로 설정, 필수 입력
--notice_content(내용) : 최대 4000byte, 필수 입력
--notice_writedate(작성시간) : 작성된 시점의 시각, sysdate로 기본설정
--notice_updatedate(수정시간) : 수정된 시점의 시각
--notice_read(조회수) : 0으로 기본 설정

-- notice 테이블 생성
create table notice(
notice_no number primary key,
notice_id varchar2(20) references member(member_id) on delete set null,
notice_title varchar2(300) not null,
notice_content varchar2(4000) not null,
notice_writedate date default sysdate,
notice_updatedate date,
notice_read number default 0
);

-- 테이블 삭제
drop table notice;

-- notice_seq 시퀀스 생성
create sequence notice_seq;

-- 시퀀스 삭제
drop sequence notice_seq;

--여기서부터 수정 22.10.24.

-- 시퀀스 번호 발급
select notice_seq.nextval from dual;

-- 공지 글 등록
insert into notice(notice_no, notice_id, notice_title, notice_content) values(notice_seq.nextval, 'hello1234', '공지테스트', '공지내용');

-- 공지 글 조회(최신순)
select * from notice order by notice_no desc;

-- 공지 글 상세
select * from notice where notice_no=1;

-- 공지 글 수정
update notice set notice_title =?, notice_content=? where notice_no=?;

-- 공지 글 삭제
delete notice where notice_no=?;

commit;

