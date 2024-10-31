package kr.jinju.crud.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.crud.mappers.DepartmentMapper;
import kr.jinju.crud.mappers.ProfessorMapper;
import kr.jinju.crud.mappers.StudentMapper;
import kr.jinju.crud.models.Department;
import kr.jinju.crud.models.Professor;
import kr.jinju.crud.models.Student;
import kr.jinju.crud.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    /**
     *  SQL문을 구현하고 있는 Mapper 객체 주입
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Department addItem(Department input) throws Exception {
        int rows = 0;
        try {
            rows = departmentMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        return departmentMapper.selectItem(input);
    }

    @Override
    public Department editItem(Department input) throws Exception {
        int rows = 0;


        try {
            rows = departmentMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }
        
        return departmentMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Department input) throws Exception {
        // 회원를 삭제하기 위해 먼저 회원에 소속된 학생 데이터를 삭제
        Student student = new Student();
        student.setDeptNo(input.getDeptNo());
        studentMapper.deleteByDeptno(student);

        // 회원를 삭제하기 위해 먼저 회원에 소속된 교수 데이터를 삭제
        Professor professor = new Professor();
        professor.setDeptNo(input.getDeptNo());
        professorMapper.deleteByDeptno(professor);
        
        // 회원 삭제
        int rows = 0;

        try {
            rows = departmentMapper.delete(input);

            if (rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        
        return rows;
    }

    @Override
    public Department getItem(Department input) throws Exception {
        Department output = null;

        try {
            output = departmentMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("학과 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Department> getList(Department input) throws Exception {
        List<Department> output = null;
        try {
            output = departmentMapper.selectList(input);
        } catch (Exception e) {
            log.error("학과 목록 조회에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
    
    @Override
    public int getCount(Department input) throws Exception {
        int output = 0;

        try {
            output = departmentMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}
