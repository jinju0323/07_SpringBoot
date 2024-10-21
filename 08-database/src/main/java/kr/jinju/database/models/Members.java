package kr.jinju.database.models;

import lombok.Data;

/**
 * Members 테이블의 구조를 정의하는 클래스
 */
@Data
public class Members {
    private int id;
    private String name;
    private String email;
    private String userPw;
    private String gender;
    private String birthDate;
    private String tel;
    private String postcode;
    private String addr1;
    private String addr2;
    private String profileImg;
    private String isOut;
    private String regDate;
    private String editDate;
}
