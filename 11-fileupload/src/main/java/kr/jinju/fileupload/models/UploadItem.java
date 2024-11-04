package kr.jinju.fileupload.models;

import lombok.Data;

/**
 * 업로드 된 파일의 정보를 저장하기 위한 Javabeans
 * - 이 클래스의 객체가 업로드 된 파일의 수 만큼 생성되어 List 형태로 보관된다
 */
@Data
public class UploadItem {
    private String fieldName;       // <input type="file">의 name 속성
    private String originName;      // 원본 파일 이름
    private String contentType;     // 파일 형식
    private long fileSize;          // 파일 용량
    private String filePath;        // 서버상의 파일 경로
    private String fileUrl;         // 서버상의 파일 URL
}
