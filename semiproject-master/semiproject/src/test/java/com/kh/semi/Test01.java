package com.kh.semi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class Test01 {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@org.junit.jupiter.api.Test
	public void test() {
		String sql = "insert into test1(no, name) values(1,'가나다')";

		jdbcTemplate.update(sql);

		jdbcTemplate.update(sql);////////
		//지원 테스트

	}

}





