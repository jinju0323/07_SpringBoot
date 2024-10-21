package kr.jinju.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.models.Department;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class DepartmentMapperTest {
    // 테스트 클래스에는 객체 주입을 사용해야 함
    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    @DisplayName("학과 추가 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void insertDepartment() {
        Department input = new Department();
        input.setDname("스프링학과");
        input.setLoc("강의실");

        // 자바 메서드임으로 .찍으면 알아서 나옴
        int ouput = departmentMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
        // 생성된 PK값
        log.debug("new deptno: " + input.getDeptNo());
    }

    @Test
    @DisplayName("학과 수정 테스트")
    // 메서드 이름 아무거나 지어도 괜찮다
    void updateDepartment() {
        Department input = new Department();
        input.setDname("스프링학과");
        input.setLoc("강의실");

        int ouput = departmentMapper.update(input);

        // 저장된 데이터의 수
        log.debug("output: " + ouput);
    }

    @Test
    @DisplayName("학과 삭제 테스트")
    void deleteDepartment() {
        Department input = new Department();
        input.setDeptNo(203);
        
        int output = departmentMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 학과 목록 조회 테스트")
    void selectItemDepartment() {
        Department input = new Department();
        input.setDeptNo(102);
        
        Department output = departmentMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("학과 목록 조회 테스트")
    void selectListDepartment() {
        List<Department> output = departmentMapper.selectList(null);
        
        for (Department item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
