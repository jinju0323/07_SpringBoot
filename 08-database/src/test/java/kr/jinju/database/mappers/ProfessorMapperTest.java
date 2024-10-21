package kr.jinju.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.models.Professor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProfessorMapperTest {
    // 테스트 클래스에는 객체 주입을 사용해야 함
    @Autowired
    private ProfessorMapper professorMapper;

    @Test
    @DisplayName("교수 추가 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void insertProfessor() {
        Professor input = new Professor();
        input.setName("테스트이름");
        input.setUserId("abcd1234");
        input.setPosition("교수");
        input.setSal(500); // int타입은 ""따옴표 뺀다
        input.setHireDate("20240101");
        input.setComm(20);
        input.setDeptNo(101);

        // 자바 메서드임으로 .찍으면 알아서 나옴
        int ouput = professorMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
        // 생성된 PK값
        log.debug("new profno: " + input.getProfNo());
    }

    @Test
    @DisplayName("교수 수정 테스트")
    void updateProfessor() {
        Professor input = new Professor();
        input.setName("테스트이름");
        input.setUserId("abcd1234");
        input.setPosition("교수");
        input.setSal(500); // int타입은 ""따옴표 뺀다
        input.setHireDate("20240101");
        input.setComm(20);
        input.setDeptNo(101);

        int ouput = professorMapper.update(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
    }

    @Test
    @DisplayName("교수 삭제 테스트")
    void deleteProfessor() {
        Professor input = new Professor();
        input.setDeptNo(203); // 이게 왜 deptno?
        
        int output = professorMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 교수 목록 조회 테스트")
    void selectOneProfessor() {
        Professor input = new Professor();
        input.setProfNo(9908);
        
        Professor output = professorMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("교수 목록 조회 테스트")
    void selectListProfessor() {
        List<Professor> output = professorMapper.selectList(null);
        
        for (Professor item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
