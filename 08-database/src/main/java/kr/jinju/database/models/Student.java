package kr.jinju.database.models;

import lombok.Data;
/**
 * Student 테이블의 구조를 정의하는 클래스
 */
@Data
public class Student {
    private int studNo;     // 학생번호
    private String name;    // 이름
    private String userId;  // 아이디
    private int grade;      // 학년
    private String idNum;   // 주민번호
    private String birthDate; // 생년월일
    private String tel;     // 전화번호
    private int height;     // 키
    private int weight;     // 몸무게
    private int deptNo;     // 학과번호
    private Integer profNo; // 담당교수 번호
}
