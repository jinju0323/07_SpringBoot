package kr.jinju.database.models;

import lombok.Data;
/**
 * Professor 테이블의 구조를 정의하는 클래스
 */
@Data
public class Professor {
    private int profNo;         // 교수 번호 <- 테이블 필드이름과 다름에 주의
    private String name;        // 교수 이름
    private String userId;      // 교수 아이디
    private String position;    // 교수 직급
    private int sal;            // 교수 급여
    private String hireDate;    // 교수 입사일
    private Integer comm;       // 교수 보직수당
    private int deptNo;         // 학과 번호
}