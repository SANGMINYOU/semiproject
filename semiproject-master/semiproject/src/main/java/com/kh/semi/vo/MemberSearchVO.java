package com.kh.semi.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class MemberSearchVO {
	//검색분류와 검색어
	private String type, keyword;
	@ToString.Include
	public boolean isSearch() {
		return type != null && keyword != null;
	}
	
	private int p = 1;//현재 페이지 번호(없을 경우 1)
	private int size = 10;//페이지에 표시할 개수
	
	@ToString.Include
	public int endRow() {
		return p*size;
	}
	@ToString.Include
	public int startRow() {
		return endRow()-(size-1);
	}
	
	//현재 페이지에 표시할 시작 번호
	@ToString.Include
	public int startBlock() {
		return (p - 1) / blockSize * blockSize + 1;
	}
	//현재 페이지에 표시할 끝 번호
	@ToString.Include
	public int endBlock() {
		int value = startBlock() + blockSize - 1;
		return Math.min(value, lastBlock());
	}
	
	private int count;//총 개수
	private int blockSize = 10;//화면에 표시할 개수
	
	//마지막 페이지 번호
	@ToString.Include
	public int pageCount() {
		return (count + size - 1) / size;
	}
	//첫 페이지 번호
	@ToString.Include 
	public int firstBlock() {
		return 1;
	}
	//마지막 페이지 번호
	@ToString.Include
	public int lastBlock() {
		return pageCount();
	}
	
	//이전 블럭 번호
	@ToString.Include
	public int prevBlock() {
		return startBlock() - 1;
	}
	
	//다음 블럭
	@ToString.Include
	public int nextBlock() {
		return endBlock() + 1;
	}
	
	//현재 블럭이 첫번째 블럭인지
	@ToString.Include
	public boolean isFirst() {
		return p == 1;
	}
	
	//현재 블럭이 마지막 블럭인지
	@ToString.Include
	public boolean isLast() {
		return endBlock() == lastBlock();
	}
	
	//이전 블럭 구간이 존재하는지
	@ToString.Include
	public boolean hasPrev() {
		return startBlock() > 1;
	}
	
	//다음 블럭 구간이 존재하는지
	@ToString.Include
	public boolean hasNext() {
		return endBlock() < lastBlock();
	}
	
	@ToString.Include
	public String parameter() {
		if(isSearch()) {
			return "size="+size+"&type="+type+"&keyword="+keyword;
		}
		else {
			return "size="+size;
		}
	}
}
