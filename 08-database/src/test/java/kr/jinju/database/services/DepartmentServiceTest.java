package kr.jinju.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.models.Department;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    @Test
    @DisplayName("학과 추가 테스트")
    void insertDepartment() throws Exception {
        Department department = new Department();
        department.setDname("테스트학과2");
        department.setLoc("테스트강의실3");

        Department result = null;

        try {
            result = departmentService.addItem(department);
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
    @DisplayName("학과 수정 테스트")
    void updateDepartment() throws Exception {
        Department department = new Department();
        department.setDeptNo(317);
        department.setDname("학과수정2");
        department.setLoc("강의실수정3");

        Department result = null;

        try {
            result = departmentService.editItem(department);
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
    @DisplayName("학과 삭제 테스트")
    void deleteDepartment() throws Exception {
        Department department = new Department();
        department.setDeptNo(341);

        try {
            departmentService.deleteItem(department);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("하나의 학과 조회 테스트")
    void selectOneDepartment() throws Exception {
        Department department = new Department();
        department.setDeptNo(102);

        Department result = null;

        try {
            result = departmentService.getItem(department);
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
    @DisplayName("학과 목록 조회 테스트")
    void selectListDepartment() throws Exception {
        List<Department> result = null;

        Department department = new Department();
        department.setDname("학과");
   
        try {
            result = departmentService.getList(department);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (result != null) {
            for (Department item : result) {
                log.debug("result: " + item.toString());
            }
        }
    }
}
