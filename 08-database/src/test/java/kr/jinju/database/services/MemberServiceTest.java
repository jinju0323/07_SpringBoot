package kr.jinju.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.models.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 추가 테스트")
    void insertMember() throws Exception {
        Member member = new Member();
        member.setName("테스트회원");
        member.setEmail("test@naver.com");
        member.setUserPw("123456");
        member.setGender("M");
        member.setBirthDate("20010101");
        member.setTel("02)1234-1234");
        member.setPostcode("1234");
        member.setAddr1("테스트주소1");
        member.setAddr2("테스트주소2");
        member.setProfileImg("테스트이미지");
        member.setIsOut("N");
        member.setRegDate("20241020");
        member.setEditDate("20241020");

        Member result = null;

        try {
            result = memberService.addItem(member);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            log.debug("result: " + result.toString());
        }
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() throws Exception {
        Member member = new Member();
        member.setId(3);
        member.setName("테스트123");
        member.setEmail("test123@naver.com");
        member.setUserPw("78910");
        member.setGender("M");
        member.setBirthDate("20030303");
        member.setTel("02)5555-1234");
        member.setPostcode("1234");
        member.setAddr1("테스트주소1-1");
        member.setAddr2("테스트주소2-2");
        member.setProfileImg("테스트이미지2");
        member.setIsOut("N");
        member.setRegDate("20241020");
        member.setEditDate("20241020");

        Member result = null;

        try {
            result = memberService.editItem(member);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            log.debug("result: " + result.toString());
        }
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMember() throws Exception {
        Member member = new Member();
        member.setId(4);

        try {
            memberService.deleteItem(member);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("하나의 회원 조회 테스트")
    void selectOneMember() throws Exception {
        Member member = new Member();
        member.setId(3);

        Member result = null;

        try {
            result = memberService.getItem(member);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            log.debug("result: " + result.toString());
        }
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void selectListMember() throws Exception {
        List<Member> result = null;

        Member member = new Member();
        member.setName("회원");
   
        try {
            result = memberService.getList(member);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            for (Member item : result) {
                log.debug("result: " + item.toString());
            }
        }
    }
}
