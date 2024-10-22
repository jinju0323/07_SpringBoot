package kr.jinju.databasemysite.models;

import lombok.Data;

/**
 * BbsFile 테이블의 구조를 정의하는 클래스
 */
@Data
public class BbsFile {
    private int id;
    private String orginName;
    private String filePath;
    private String contentType;
    private String regDate;
    private String editDate;
    private String bbsDocumentsId;
}
