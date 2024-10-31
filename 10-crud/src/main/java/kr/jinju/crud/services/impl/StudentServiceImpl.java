package kr.jinju.crud.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.crud.mappers.StudentMapper;
import kr.jinju.crud.models.Student;
import kr.jinju.crud.services.StudentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    /**
     *  SQL문을 구현하고 있는 Mapper 객체 주입
     */
    @Autowired
    private StudentMapper StudentMapper;

    @Override
    public Student addItem(Student input) throws Exception {
        int rows = 0;
        
        try {
            rows = StudentMapper.insert(input);
            
            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        
        return StudentMapper.selectItem(input);
    }

    @Override
    public Student editItem(Student input) throws Exception {
        int rows = 0;

        try {
            rows = StudentMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return StudentMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Student input) throws Exception {
        int rows = 0;
        
        try {
            rows = StudentMapper.delete(input);

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
    public Student getItem(Student input) throws Exception {
        Student output = null;

        try {
            output = StudentMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("학생 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Student> getList(Student input) throws Exception {
        List<Student> output = null;
        try {
            output = StudentMapper.selectList(input);
        } catch (Exception e) {
            log.error("학생 목록 조회에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
    
    @Override
    public int getCount(Student input) throws Exception {
        int output = 0;

        try {
            output = StudentMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }
        
        return output;
    }
}
