package kr.jinju.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.models.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberMapperTest {
    // 테스트 클래스에는 객체 주입을 사용해야 함
    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("회원 추가 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void insertMember() {
        Member input = new Member();
        input.setName("테스트회원");
        input.setEmail("test@naver.com");
        input.setUserPw("123456");
        input.setGender("M");
        input.setBirthDate("20010101");
        input.setTel("02)1234-1234");
        input.setPostcode("1234");
        input.setAddr1("테스트주소1");
        input.setAddr2("테스트주소2");
        input.setProfileImg("테스트이미지");
        input.setIsOut("N");
        input.setRegDate("20241020");
        input.setEditDate("20241020");

        // 자바 메서드임으로 .찍으면 알아서 나옴
        int ouput = memberMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
        // 생성된 PK값
        log.debug("new id: " + input.getId());
    }

    @Test
    @DisplayName("회원 수정 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void updateMember() {
        Member input = new Member();
        input.setName("테스트123");
        input.setEmail("test123@naver.com");
        input.setUserPw("78910");
        input.setGender("M");
        input.setBirthDate("20030303");
        input.setTel("02)5555-1234");
        input.setPostcode("1234");
        input.setAddr1("테스트주소1-1");
        input.setAddr2("테스트주소2-2");
        input.setProfileImg("테스트이미지2");
        input.setIsOut("N");
        input.setRegDate("20241020");
        input.setEditDate("20241020");

        int ouput = memberMapper.update(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMember() {
        Member input = new Member();
        input.setId(2);
        
        int output = memberMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 회원 목록 조회 테스트")
    void selectItemMember() {
        Member input = new Member();
        input.setId(3);
        
        Member output = memberMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void selectListMember() {
        List<Member> output = memberMapper.selectList(null);
        
        for (Member item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
