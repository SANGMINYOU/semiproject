package com.kh.semi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.semi.entity.AttachmentDto;
import com.kh.semi.repository.AttachmentDao;

@RequestMapping("/attachment")
@RestController
public class AttachmentController {
	
	
	@Autowired
	AttachmentDao attachmentDao;
	
	//상품썸네일 이미지 다운로드 주소
	@GetMapping("/download/productTumbnail")
	public ResponseEntity<ByteArrayResource> productDowuload(
			@RequestParam int attachmentNo) throws IOException{
		//[1] 파일탐색(DB)
		AttachmentDto dto = attachmentDao.selectOne(attachmentNo);
		if(dto==null) {//파일이 없으면
			return ResponseEntity.notFound().build();//404 error 전송
		}
		//[2]파일 불러오기
		File directory = new File("D:\\upload\\KH10A\\productTumbnail");
		File target = new File(directory, String.valueOf(attachmentNo));
		directory.mkdir();//폴더 생성 명령
		byte[] data = FileUtils.readFileToByteArray(target);
		ByteArrayResource resource = new ByteArrayResource(data);
		
		//[3]응답 객체를 만들어 데이터 전송
		return ResponseEntity.ok()
//				.header("Content-Encoding","UTF-8")
				.header(HttpHeaders.CONTENT_ENCODING, 
						StandardCharsets.UTF_8.name())
//				.header("Content-Length",String.valueOf(dto.getAttachmentSize()))
				.contentLength(dto.getAttachmentSize())
//				.header("Content-Disposition", "attachment; filename="+dto.getAttachmentName())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().
						filename(
						dto.getAttachmentName(),
						StandardCharsets.UTF_8)
						.build().toString())
//				.header("Content-Type", dto.getAttachmentType())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	//상품 상세이미지 다운로드 주소
	@GetMapping("/download/productDetail")
	public ResponseEntity<ByteArrayResource> productDetailDowuload(
			@RequestParam int attachmentNo) throws IOException{
		//[1] 파일탐색(DB)
		AttachmentDto dto = attachmentDao.selectOne(attachmentNo);
		if(dto==null) {//파일이 없으면
			return ResponseEntity.notFound().build();//404 error 전송
		}
		//[2]파일 불러오기
		File directory = new File("D:upload\\kh10A\\productDeatail");
		File target = new File(directory, String.valueOf(attachmentNo));
		directory.mkdir();//폴더 생성 명령
		byte[] data = FileUtils.readFileToByteArray(target);
		ByteArrayResource resource = new ByteArrayResource(data);
		
		//[3]응답 객체를 만들어 데이터 전송
		return ResponseEntity.ok()
//				.header("Content-Encoding","UTF-8")
				.header(HttpHeaders.CONTENT_ENCODING, 
						StandardCharsets.UTF_8.name())
//				.header("Content-Length",String.valueOf(dto.getAttachmentSize()))
				.contentLength(dto.getAttachmentSize())
//				.header("Content-Disposition", "attachment; filename="+dto.getAttachmentName())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().
						filename(
						dto.getAttachmentName(),
						StandardCharsets.UTF_8)
						.build().toString())
//				.header("Content-Type", dto.getAttachmentType())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	
	// 문의글 첨부파일 이미지 다운로드 Mapping
	@GetMapping("/download/inquireImg")
	public ResponseEntity<ByteArrayResource> downloadInquireImg(@RequestParam int attachmentNo) throws IOException{
		
		// View에서 입력받은 첨부파일 번호로 단일 조회 실행
		AttachmentDto attachmentDto = attachmentDao.selectOne(attachmentNo);
		
		// 해당 첨부파일 번호의 파일이 있는지 판정
		if(attachmentDto == null) { // 해당 첨부파일 번호의 첨부파일이 존재하지 않는다면
			return ResponseEntity.notFound().build(); // 404 에러를 발생시키는 ResponseEntity 반환
		}
		
		// 문의글 이미지 첨부파일이 존재하는 상위 경로(parent) 설정(상위 경로에 대한 File 클래스의 인스턴스 추가)
		File directory = new File("D:upload\\KH10A\\inquireImg");
		
		// 상위 경로(parent)의 File 클래스 인스턴스와 하위 경로(child)의 문자열을 이용하여 파일 다운로드 경로 설정
		// - 첨부파일 업로드시 파일 이름을 첨부파일 번호(attchmentNo)로 설정했으므로 다운로드 시 하위 경로 이름은 해당 첨부파일 번호(attchmentNo)가 된다
		File target = new File(directory, String.valueOf(attachmentNo));
		
		// target을 byte 배열로 변환
		byte[] data = FileUtils.readFileToByteArray(target);
		
		// byte 배열로 ByteArrayResource의 인스턴스 생성
		ByteArrayResource resource = new ByteArrayResource(data);
		
		// ResponseEntity 반환
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name()) 
				.contentLength(attachmentDto.getAttachmentSize()) 
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						ContentDisposition.attachment().filename(attachmentDto.getAttachmentName(), 
						StandardCharsets.UTF_8)
						.build().toString())
				.body(resource);
	}
	
	//리뷰 첨부파일 이미지 다운로드 주소
	@GetMapping("/download/reviewImg")
	public ResponseEntity<ByteArrayResource> downloadReviewImg(@RequestParam int attachmentNo) throws IOException{
		//[1] 파일탐색(DB)
		//jsp에서 받은 번호 하나만 조회
		AttachmentDto dto = attachmentDao.selectOne(attachmentNo);
		if(dto==null) {//파일이 없으면
			return ResponseEntity.notFound().build();//404 error 전송
		}
		//[2]파일 불러오기
		//D드라이브에 있는 리뷰이미지 파일에 
		File directory = new File("D:upload\\KH10A\\reviewImg");
		File target = new File(directory, String.valueOf(attachmentNo));
		directory.mkdir();//폴더 생성 명령
		byte[] data = FileUtils.readFileToByteArray(target);
		ByteArrayResource resource = new ByteArrayResource(data);
		
		//[3]응답 객체를 만들어 데이터 전송
		return ResponseEntity.ok()
//						.header("Content-Encoding","UTF-8")
				.header(HttpHeaders.CONTENT_ENCODING, 
						StandardCharsets.UTF_8.name())
//						.header("Content-Length",String.valueOf(dto.getAttachmentSize()))
				.contentLength(dto.getAttachmentSize())
//						.header("Content-Disposition", "attachment; filename="+dto.getAttachmentName())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().
						filename(
						dto.getAttachmentName(),
						StandardCharsets.UTF_8)
						.build().toString())
//						.header("Content-Type", dto.getAttachmentType())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	
}
