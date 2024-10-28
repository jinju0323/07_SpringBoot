package kr.jinju.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.mappers.DepartmentMapper;
import kr.jinju.database.mappers.ProfessorMapper;
import kr.jinju.database.mappers.StudentMapper;
import kr.jinju.database.models.Department;
import kr.jinju.database.models.Professor;
import kr.jinju.database.models.Student;
import kr.jinju.database.services.DepartmentService;

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
    public Department addItem(Department input) throws ServiceNoResultException, Exception {
        int rows = departmentMapper.insert(input);

        if (rows == 0) {
            throw new ServiceNoResultException("저장된 데이터가 없습니다.");
        }
        return departmentMapper.selectItem(input);
    }

    @Override
    public Department editItem(Department input) throws ServiceNoResultException, Exception {
        int rows = departmentMapper.update(input);

        if (rows == 0) {
            throw new ServiceNoResultException("수정된 데이터가 없습니다.");
        }
        return departmentMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Department input) throws ServiceNoResultException, Exception {
        // 회원를 삭제하기 위해 먼저 회원에 소속된 학생 데이터를 삭제
        Student student = new Student();
        student.setDeptNo(input.getDeptNo());
        studentMapper.deleteByDeptno(student);

        // 회원를 삭제하기 위해 먼저 회원에 소속된 교수 데이터를 삭제
        Professor professor = new Professor();
        professor.setDeptNo(input.getDeptNo());
        professorMapper.deleteByDeptno(professor);
        
        // 회원 삭제
        int rows = departmentMapper.delete(input);

        if (rows == 0) {
            throw new ServiceNoResultException("삭제된 데이터가 없습니다.");
        }
        return rows;
    }

    @Override
    public Department getItem(Department input) throws ServiceNoResultException, Exception {
        Department output = departmentMapper.selectItem(input);

        if (output == null) {
            throw new ServiceNoResultException("조회된 데이터가 없습니다.");
        }
        return output;
    }

    @Override
    public List<Department> getList(Department input) throws ServiceNoResultException, Exception {
        return departmentMapper.selectList(input);
    }
    
    @Override
    public int getCount(Department input) throws Exception {
        return departmentMapper.selectCount(input);
    }
}
