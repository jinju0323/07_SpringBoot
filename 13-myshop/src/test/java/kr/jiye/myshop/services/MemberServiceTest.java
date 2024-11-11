package kr.jiye.myshop.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jiye.myshop.exceptions.ServiceNoResultException;
import kr.jiye.myshop.models.Member;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MembersService memberService;
    @Test
    @DisplayName("학생 추가 테스트")
    void insertMember() throws Exception {
    Member input = new Member();
    input.setUserId("jiye");
    input.setUserPw("1234");
    input.setUserName("신지예");
    input.setEmail("cg2522@naver.com");
    input.setPhone("010-1234-5678");
    input.setBirthday("2002-12-20");
    input.setGender("F");
    input.setPostcode("12345");
    input.setAddr1("서울시 강남구");
    input.setAddr2("역삼동");

    Member output = null;

    try {
    output = memberService.addItem(input);
    } catch (ServiceNoResultException e) {
        log.error("SQL문 처리 결과 없음", e);
    } catch (Exception e) {
        log.error("Mapper 구현 에러", e);
        throw e;
    }

    if (output != null) {
        log.debug("output: " + output);
        log.debug("new studno: " + input.getId());
    }
    }

    @Test
    @DisplayName("학생 수정 테스트")
    void updateMember() throws Exception {
    Member input = new Member();
        input.setId(1);
        input.setUserId("jiye");
        input.setUserPw("1234");
        input.setUserName("신지예");
        input.setEmail("cg2522@naver.com");
        input.setPhone("010-1234-5678");
        input.setBirthday("2002-12-20");
        input.setGender("F");
        input.setPostcode("12345");
        input.setAddr1("경기도 과천시");
        input.setAddr2("문원동");

    Member output = null;

    try {
        output = memberService.editItem(input);
    } catch (ServiceNoResultException e) {
        log.error("SQL문 처리 결과 없음", e);
    } catch (Exception e) {
        log.error("Mapper 구현 에러", e);
        throw e;
    }
    if (output != null) {
        log.debug("output: " + output.toString());
    }
    }

        @Test
        @DisplayName("학생 삭제 테스트")
        void deleteMember() throws Exception {
        Member input = new Member();
        input.setId(1);

        try {
            memberService.deleteItem(input);
        } catch (ServiceNoResultException e) {
        log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
        log.error("Mapper 구현 에러", e);
        throw e;
        }
        }

        @Test
        @DisplayName("하나의 학생 조회 테스트")
        void selectOneMember() throws Exception {
        Member input = new Member();
        input.setId(3);
        Member output = null;
        try {
        output = memberService.getItem(input);
        } catch (ServiceNoResultException e) {
        log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
        log.error("Mapper 구현 에러", e);
        
        }
        if (output != null) {
        log.debug("output: " + output.toString());
        }
        }

        @Test
        @DisplayName("학생 목록 조회 테스트")
        void selectListMember() throws Exception {
        List<Member> output = null;
        Member input = new Member();
        input.setUserName("지예");
        try {
        output = memberService.getList(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
        log.error("Mapper 구현 에러", e);
        }
        if (output != null) {
        for (Member item : output) {
        log.debug("output: " + item.toString());
        }
        }
        
    }

    // 메서드에 throws 걸면 try-catch 자동완성 안됨.
    @Test
    @DisplayName("아이디찾기 테스트")
    void findIdMember() throws Exception {
        Member input = new Member();
        input.setUserName("신지예");
        input.setEmail("11112@naver.com");

        Member output = null;
        try {
            output = memberService.findId(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (output != null) {
            log.debug("result: " + output.toString());
        }
    }
}

