package com.kh.semi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Week2Dto {
	private int week2Combi, week2ProducNo;
	private String week2MemberId;
	private int week2Option1, week2Option2, week2Option3;
	private int week2Option4, week2Option5, week2Option6;
	private int week2Option7, week2Option8;
}
