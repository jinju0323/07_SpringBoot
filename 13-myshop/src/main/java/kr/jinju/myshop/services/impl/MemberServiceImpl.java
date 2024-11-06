package kr.jinju.myshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.myshop.mappers.MemberMapper;
import kr.jinju.myshop.models.Member;
import kr.jinju.myshop.services.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    /**
     *  SQL문을 구현하고 있는 Mapper 객체 주입
     */
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public Member addItem(Member input) throws Exception {
        int rows = 0;
        try {
            rows = memberMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        return memberMapper.selectItem(input);
    }

    @Override
    public Member editItem(Member input) throws Exception {
        int rows = 0;


        try {
            rows = memberMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }
        
        return memberMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Member input) throws Exception {
        
        // 회원 삭제
        int rows = 0;

        try {
            rows = memberMapper.delete(input);

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
    public Member getItem(Member input) throws Exception {
        Member output = null;

        try {
            output = memberMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("회원 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Member> getList(Member input) throws Exception {
        List<Member> output = null;
        try {
            output = memberMapper.selectList(input);
        } catch (Exception e) {
            log.error("회원 목록 조회에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
    
    @Override
    public int getCount(Member input) throws Exception {
        int output = 0;

        try {
            output = memberMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}
