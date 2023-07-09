package com.kh.semi.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class InquireListSearchVO {

	// 필드
	private String type;	// 검색 분류
	private String keyword; // 검색어
	
	// 검색 조회인지 반환
	public boolean isSearch() {
		// 분류와 검색어가 모두 존재한다면(둘 다 null이 아니라면)
		return type != null && keyword != null;
	};
	
	   // 1. 현재 페이지에 표시될 문의글의 시작 rownum과 끝 rownum 관련
	   // 필드
	   private int pageNow = 1; // 현재 페이지 (기본값을 1로 한다)
	   private int countInquireNow = 10; // 현재 페이지에 표시될 문의글의 갯수
	   
	   //현재페이지에 목록갯수(countInquireNow=10씩)
	   @ToString.Include
	   public int rownumStart() {
		   return rownumEnd()-countInquireNow+1;
	   };
	   
	   //-현재 페이지에 표시될 상품의 마지막 rownum
	   @ToString.Include
	   public int rownumEnd() {
		   return pageNow*countInquireNow;
	   }
	   
	   //등록된 문의글의 총 수 - 조회한 총 문의글 갯수(count(*))를 값으로 가질 에정 
	   private int countTotalInquireNow;
	   //현재 페이지에 표시할 페이지 블럭의 수
	   private int countBlockNow=5;
	   
	   //전체 페이지에 대한 천 페이지 블럭 번호
	   @ToString.Include
	   public int blockFirst() {
		   return 1; //첫페이지 블럭번호는 1
	   }
	   
	   //문의글이 존재하는 페이지의 마지막 번호
	   @ToString.Include
	   public int countBlock() {
		   return (countTotalInquireNow +(countInquireNow-1))/countInquireNow;
	   }
	   
	   //전체 페이지에 마지막 페이지 블럭번호
	   @ToString.Include
	   public int blockLast() {
		   return countBlock();
	   }
	   
	   //현재 페이지에서 페이지 블럭의 시작 번호
	   @ToString.Include
	   public int blockStart() {
		   return (pageNow-1)/countBlockNow*countBlockNow+1;
	   }
	   
	   //현재 페이지에서 페이지 블럭의 끝 번호
	   @ToString.Include
	   public int blockEnd() {
		   int value = blockStart() + countBlockNow-1;
		   return Math.min(value, blockLast());
	   }
	   //이전 블럭의 번호
	   @ToString.Include
		public int blockPrev() {
			return blockStart() - 1;
		}
		
		//다음 블럭의 번호 
	   @ToString.Include
		public int blockNext() {
			return blockEnd() + 1;
		}
		
		//현재 블럭이 첫 번째 블럭인지 판정
	   @ToString.Include
		public boolean isFirst() {
			return pageNow == 1;
		}
		
		//현재 블럭이 마지막 블럭인지 판정
	   @ToString.Include
		public boolean isLast() {
			return blockEnd() == blockLast();
		}
		
		//이전 번호의 블럭이 있는지 판정
	   @ToString.Include
		public boolean hasPrev() {
			return blockStart() > 1;
		}
		
		//다음 번호의 블럭이 있는지 판정
	   @ToString.Include
		public boolean hasNext() {
			return blockEnd() < blockLast();
		}
		
		//검색중애도 페이징이 유자지될수있도록하는 메서드
	   @ToString.Include
		public String queryString() {
			if(isSearch()) {
				return "countInquireNow=" + countInquireNow + "&type=" + type + "&keyword=" + keyword;
			}
			else {
				return "countInquireNow=" + countInquireNow;
			}
		}
}
