package kr.jinju.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.models.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class StudentMapperTest {
    // 테스트 클래스에는 객체 주입을 사용해야 함
    @Autowired
    private StudentMapper studentMapper;

    @Test
    @DisplayName("학생 추가 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void methodnameinsert() {
        Student input = new Student();
        input.setName("테스트추가");
        input.setUserId("test1234");
        input.setGrade(4);
        input.setIdNum("7907021369824");
        input.setBirthDate("20010323");
        input.setTel("02)1234-1234");
        input.setHeight(170);
        input.setWeight(50);
        input.setDeptNo(101);
        input.setProfNo(9903);

        // 자바 메서드임으로 .찍으면 알아서 나옴
        int ouput = studentMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
        // 생성된 PK값
        log.debug("new studno: " + input.getStudNo());
    }

    @Test
    @DisplayName("학생 수정 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void methodnameupdate() {
        Student input = new Student();
        input.setName("테스트수정");
        input.setUserId("test5678");
        input.setGrade(2);
        input.setIdNum("8511241639826");
        input.setBirthDate("20050525");
        input.setTel("02)7777-7777");
        input.setHeight(150);
        input.setWeight(40);
        input.setDeptNo(201);
        input.setProfNo(9907);

        int ouput = studentMapper.update(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
    }

    @Test
    @DisplayName("학생 삭제 테스트")
    void deleteStudent() {
        Student input = new Student();
        input.setStudNo(500000);
        
        int output = studentMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 학생 목록 조회 테스트")
    void selectOneStudent() {
        Student input = new Student();
        input.setStudNo(10101);
        
        Student output = studentMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("학생 목록 조회 테스트")
    void selectListStudent() {
        List<Student> output = studentMapper.selectList(null);
        
        for (Student item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
