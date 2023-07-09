package com.kh.semi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.semi.entity.Week2Dto;

@Repository
public class Week2DaoImpl implements Week2Dao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//시퀀스(조합번호) 발급
	@Override
	public int sequence() {
		String sql = "select week2_seq.nextval from dual";
		int sequence = jdbcTemplate.queryForObject(sql, int.class);
		return sequence;
	}

	//데이터 등록
	@Override
	public void insert(Week2Dto week2Dto) {
		String sql = "insert into week2(week2_combi, week2_product_no,"
				+ "week2_member_id, week2_option1, week2_option2, "
				+ "week2_option3, week2_option4, week2_option5, "
				+ "week2_option6, week2_option7, week2_option8) values("
				+ "week2_seq.nextval, ?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {week2Dto.getWeek2ProducNo(),
				week2Dto.getWeek2MemberId(),
				week2Dto.getWeek2Option1(), week2Dto.getWeek2Option2(),
				week2Dto.getWeek2Option3(), week2Dto.getWeek2Option4(),
				week2Dto.getWeek2Option5(), week2Dto.getWeek2Option6(),
				week2Dto.getWeek2Option7(), week2Dto.getWeek2Option8()};
		jdbcTemplate.update(sql, param);
	}

}
