package kr.jinju.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.models.Professor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProfessorServiceTest {
    @Autowired
    private ProfessorService professorService;

    @Test
    @DisplayName("교수 추가 테스트")
    void insertProfessor() throws Exception {
        Professor professor = new Professor();
        professor.setName("테스트이름");
        professor.setUserId("abcd1234");
        professor.setPosition("교수");
        professor.setSal(500); // int타입은 ""따옴표 뺀다
        professor.setHireDate("20240101");
        professor.setComm(20);
        professor.setDeptNo(101);

        Professor result = null;

        try {
            result = professorService.addItem(professor);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            log.debug("result: " + result.toString());
            log.debug("new profno: " + professor.getProfNo());
        }
    }

    @Test
    @DisplayName("교수 수정 테스트")
    void updateProfessor() throws Exception {
        Professor professor = new Professor();
        professor.setProfNo(9912);
        professor.setName("테스트이름");
        professor.setUserId("abcd1234");
        professor.setPosition("교수");
        professor.setSal(500); // int타입은 ""따옴표 뺀다
        professor.setHireDate("20240101");
        professor.setComm(20);
        professor.setDeptNo(101);

        Professor result = null;

        try {
            result = professorService.editItem(professor);
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
    @DisplayName("교수 삭제 테스트")
    void deleteProfessor() throws Exception {
        Professor professor = new Professor();
        professor.setProfNo(9913);

        try {
            professorService.deleteItem(professor);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("단일행 교수 조회 테스트")
    void selectOneProfessor() throws Exception {
        Professor professor = new Professor();
        professor.setDeptNo(102);

        Professor result = null;

        try {
            result = professorService.getItem(professor);
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
    @DisplayName("다중행 교수 조회 테스트")
    void selectListProfessor() throws Exception {
        List<Professor> result = null;

        Professor professor = new Professor();
        professor.setName("성현");
   
        try {
            result = professorService.getList(professor);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            for (Professor item : result) {
                log.debug("result: " + item.toString());
            }
        }
    }
}
