package kr.jinju.crud.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinju.crud.mappers.MemberMapper;
import kr.jinju.crud.models.Member;
import kr.jinju.crud.services.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

    /**
     *  SQL문을 구현하고 있는 Mapper 객체 주입
     */
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member addItem(Member input) throws Exception {
        int rows = memberMapper.insert(input);

        if (rows == 0) {
            throw new Exception("저장된 데이터가 없습니다.");
        }
        return memberMapper.selectItem(input);
    }

    @Override
    public Member editItem(Member input) throws Exception {
        int rows = memberMapper.update(input);

        if (rows == 0) {
            throw new Exception("수정된 데이터가 없습니다.");
        }
        return memberMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Member input) throws Exception {
        
        // 회원 삭제
        int rows = memberMapper.delete(input);

        if (rows == 0) {
            throw new Exception("삭제된 데이터가 없습니다.");
        }
        return rows;
    }

    @Override
    public Member getItem(Member input) throws Exception {
        Member output = memberMapper.selectItem(input);

        if (output == null) {
            throw new Exception("조회된 데이터가 없습니다.");
        }
        return output;
    }

    @Override
    public List<Member> getList(Member input) throws Exception {
        return memberMapper.selectList(input);
    }
    
}
