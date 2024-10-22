package kr.jinju.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.models.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("학생 추가 테스트")
    void insertStudent() throws Exception {
        Student student = new Student();
        student.setName("테스트추가");
        student.setUserId("test1234");
        student.setGrade(4);
        student.setIdNum("7907021369824");
        student.setBirthDate("20010323");
        student.setTel("02)1234-1234");
        student.setHeight(170);
        student.setWeight(50);
        student.setDeptNo(101);
        student.setProfNo(9903);

        Student result = null;

        try {
            result = studentService.addItem(student);
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
    @DisplayName("학생 수정 테스트")
    void updateStudent() throws Exception {
        Student student = new Student();
        student.setStudNo(10101);
        student.setName("테스트수정");
        student.setUserId("test5678");
        student.setGrade(2);
        student.setIdNum("8511241639826");
        student.setBirthDate("20050525");
        student.setTel("02)7777-7777");
        student.setHeight(150);
        student.setWeight(40);
        student.setDeptNo(201);
        student.setProfNo(9907);

        Student result = null;

        try {
            result = studentService.editItem(student);
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
    @DisplayName("학생 삭제 테스트")
    void deleteStudent() throws Exception {
        Student student = new Student();
        student.setDeptNo(10101);

        try {
            studentService.deleteItem(student);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("단일행 학생 조회 테스트")
    void selectOneStudent() throws Exception {
        Student student = new Student();
        student.setDeptNo(10102);

        Student result = null;

        try {
            result = studentService.getItem(student);
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
    @DisplayName("다중행 학생 조회 테스트")
    void selectListStudent() throws Exception {
        List<Student> result = null;

        Student student = new Student();
        student.setName("성현");
   
        try {
            result = studentService.getList(student);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            for (Student item : result) {
                log.debug("result: " + item.toString());
            }
        }
    }
}
