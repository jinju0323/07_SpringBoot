package kr.jinju.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.database.mappers.ProfessorMapper;
import kr.jinju.database.mappers.StudentMapper;
import kr.jinju.database.models.Professor;
import kr.jinju.database.models.Student;
import kr.jinju.database.services.ProfessorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public Professor addItem(Professor input) throws Exception {
        int rows = 0;
        
        try {
            rows = professorMapper.insert(input);
            
            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        
        return professorMapper.selectItem(input);
    }

    @Override
    public Professor editItem(Professor input) throws Exception {
        int rows = 0;

        try {
            rows = professorMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return professorMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Professor input) throws Exception {
        int rows = 0;

        // 교수를 삭제하기 위해 먼저 교수에 소속된 학생 데이터를 삭제
        Student student = new Student();
        student.setProfNo(input.getProfNo());
        
        try {
            studentMapper.updateByProfno(student);
            rows = professorMapper.delete(input);

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
    public Professor getItem(Professor input) throws Exception {
        Professor output = null;

        try {
            output = professorMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("교수 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Professor> getList(Professor input) throws Exception {
        List<Professor> output = null;
        try {
            output = professorMapper.selectList(input);
        } catch (Exception e) {
            log.error("교수 목록 조회에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
    
    @Override
    public int getCount(Professor input) throws Exception {
        int output = 0;

        try {
            output = professorMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }
        
        return output;
    }
}
