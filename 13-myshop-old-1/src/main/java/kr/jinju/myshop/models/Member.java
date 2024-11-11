package kr.jinju.myshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Members 테이블의 구조를 정의하는 클래스
 */
@Data
public class Member {
    private int id;
    private String name;
    private String email;
    private String userId;
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

    /**
     * 한 페이지에 표시될 목록 수
     * MySQL의 Limit절에서 사용할 값이므로 Beans에 추가한다.
     * 
     * 1) static 변수로 선언하여 모든 객체가 공유한다.
     * 2) static 변수를 객체를 생성하지 않고도 사용할 수 있따.
     * 3) static 변수에 Lombok을 적용하려면
     *      @Getter, @Setter를 개별적으로 적용한다.
     */
    @Getter
    @Setter
    private static int listCount = 0;


    /**
     * MySQL의 Limit절에서 사용될 offset값
     * MySQL의 Limit절에서 사용할 값이므로 Beans에 추가한다.
     * 
     * offset위치부터 listCount 만큼의 데이터를 가져온다.
     */
    @Getter
    @Setter
    private static int offset = 0;
}
