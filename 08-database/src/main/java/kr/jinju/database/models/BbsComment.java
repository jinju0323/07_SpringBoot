package kr.jinju.database.models;

import lombok.Data;

/**
 * BbsComment 테이블의 구조를 정의하는 클래스
 */
@Data
public class BbsComment {
    private int id;
    private String writer;
    private String password;
    private String email;
    private String content;
    private String hit;
    private String regDate;
    private String editDate;
    private String membersId;
    private String bbsDocumentsId;
}
