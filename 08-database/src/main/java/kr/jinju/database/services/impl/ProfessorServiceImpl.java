package kr.jinju.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.mappers.ProfessorMapper;
import kr.jinju.database.mappers.StudentMapper;
import kr.jinju.database.models.Professor;
import kr.jinju.database.models.Student;
import kr.jinju.database.services.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    /**
     *  SQL문을 구현하고 있는 Mapper 객체 주입
     */
    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Professor addItem(Professor input) throws ServiceNoResultException, Exception {
        int rows = professorMapper.insert(input);

        if (rows == 0) {
            throw new ServiceNoResultException("저장된 데이터가 없습니다.");
        }
        return professorMapper.selectItem(input);
    }

    @Override
    public Professor editItem(Professor input) throws ServiceNoResultException, Exception {
        int rows = professorMapper.update(input);

        if (rows == 0) {
            throw new ServiceNoResultException("수정된 데이터가 없습니다.");
        }
        return professorMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Professor input) throws ServiceNoResultException, Exception {
        // 교수를 삭제하기 위해 먼저 교수에 소속된 학생 데이터를 삭제
        Student student = new Student();
        student.setProfNo(input.getProfNo());
        studentMapper.updateByProfno(student);
        
        // 교수 삭제
        int rows = professorMapper.delete(input);

        if (rows == 0) {
            throw new ServiceNoResultException("삭제된 데이터가 없습니다.");
        }
        return rows;
    }

    @Override
    public Professor getItem(Professor input) throws ServiceNoResultException, Exception {
        Professor output = professorMapper.selectItem(input);

        if (output == null) {
            throw new ServiceNoResultException("조회된 데이터가 없습니다.");
        }
        return output;
    }

    @Override
    public List<Professor> getList(Professor input) throws ServiceNoResultException, Exception {
        return professorMapper.selectList(input);
    }
    
}
