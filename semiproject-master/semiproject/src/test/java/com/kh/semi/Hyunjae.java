package com.kh.semi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kh.semi.entity.InquireReplyDto;
import com.kh.semi.repository.AttachmentDao;
import com.kh.semi.repository.InquireReplyDao;
import com.kh.semi.repository.ProductDao;

@SpringBootTest
public class Hyunjae {

	@Autowired
	JdbcTemplate jdbcTemplate;
	//
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	AttachmentDao attachmentDao;
	
	@Autowired
	InquireReplyDao inquireReplyDao;
	
	//건들지 마셈
	@Test
	public void test01() {
		
	inquireReplyDao.replyWrite(InquireReplyDto.builder()
			.inquireReplyId("hello111")
			.inquireOriginNo(301)
			.inquireReplyContent("아몰랑")
			.build());
	
	inquireReplyDao.selectList(1);
	//
	
		
	}
	
	
	
	
}
