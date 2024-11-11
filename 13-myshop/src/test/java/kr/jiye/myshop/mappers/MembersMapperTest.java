package kr.jiye.myshop.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jiye.myshop.models.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class MembersMapperTest {
    @Autowired
    private MembersMapper membersMapper;

    @Test
    @DisplayName("회원 추가 테스트")
    void insertMembers() {
        Member input = new Member();
        input.setUserId("jiye1220");
        input.setUserPw("1234");
        input.setUserName("신지예");
        input.setEmail("cg2522@naver.com");
        input.setPhone("010-1234-5678");
        input.setBirthday("2002-12-20");
        input.setGender("F");
        input.setPostcode("12345");
        input.setAddr1("서울시 강남구");
        input.setAddr2("역삼동");

        int output = membersMapper.insert(input);

        log.debug("output: " + output);
        log.debug("new id: " + input.getId());
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMembers() {
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

        int output = membersMapper.update(input);

        log.debug("output: " + output);
    }
    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMembers() {
        Member input = new Member();
        input.setId(1);

        int output = membersMapper.delete(input);
        log.debug("output: " + output);
    }

    // 조회할 데이터가 있어야 테스트 통과함
    @Test
    @DisplayName("하나의 회원 조회 테스트")
    void selectOneMembers() {
        Member input = new Member();
        input.setId(5);

        Member output = membersMapper.selectItem(input);
        log.debug("output: " + output.toString());
        
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void selectListMembers() {
        List<Member> output = membersMapper.selectList(null);

        for (Member item : output) {
            log.debug("output: " + item.toString());
        }
    }    

    // 조회할 데이터가 있어야 테스트 통과함
    // mysql 확인 해볼것 !!
    @Test
    @DisplayName("아이디찾기 테스트")
    void findIdMembers() {
        Member input = new Member();
        input.setUserName("신지예");
        input.setEmail("11112@naver.com");

        Member output = membersMapper.findId(input);
        log.debug("output: " + output.toString());
    }
}

