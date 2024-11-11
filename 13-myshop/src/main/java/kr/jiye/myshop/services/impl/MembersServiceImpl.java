package kr.jiye.myshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jiye.myshop.exceptions.ServiceNoResultException;
import kr.jiye.myshop.mappers.MembersMapper;
import kr.jiye.myshop.models.Member;
import kr.jiye.myshop.services.MembersService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MembersServiceImpl implements MembersService {

    @Autowired
    private MembersMapper membersMapper;

    @Override 
    public Member addItem(Member input) throws ServiceNoResultException, Exception {
        int rows = membersMapper.insert(input);

        if (rows == 0) {
            throw new ServiceNoResultException("저장된 데이터가 없습니다.");
        }

        return membersMapper.selectItem(input);
    }

    @Override
    public Member editItem(Member input) throws ServiceNoResultException, Exception {
        int rows = membersMapper.update(input);


        if (rows == 0) {
            throw new ServiceNoResultException("수정된 데이터가 없습니다.");     
        }

        return membersMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Member input) throws ServiceNoResultException, Exception {
    
        int rows = membersMapper.delete(input);

        // 삭제된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new ServiceNoResultException("삭제된 데이터가 없습니다.");
        }

        return rows;
    }

    @Override
    public Member getItem(Member input) throws ServiceNoResultException, Exception {
        Member output = membersMapper.selectItem(input);

        if (output == null) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new ServiceNoResultException("조회된 데이터가 없습니다.");
        }

        return output;
    }

    @Override
    public List<Member> getList(Member input) throws ServiceNoResultException, Exception {
        return membersMapper.selectList(input);
    }

    // 추가내용
    @Override
    public void isUniqueUserId(String userId) throws Exception {
        Member input = new Member();
        input.setUserId(userId);

        int output = 0;

        try {
            output = membersMapper.selectCount(input);

            if (output > 0) {
                throw new Exception("사용할 수 없는 아이디 입니다.");
            }
        } catch (Exception e) {
            log.error("이메일 중복검사에 실패했습니다.", e);
            throw e;
        }
    }

    @Override
    public void isUniqueEmail(String email) throws Exception {
        Member input = new Member();
        input.setEmail(email);

        int output = 0;

        try {
            output = membersMapper.selectCount(input);

            if (output > 0) {
                throw new Exception("사용할 수 없는 이메일 입니다.");
            }
        } catch (Exception e) {
            log.error("이메일 중복검사에 실패했습니다.", e);
            throw e;
        }
        
    }

    @Override
    public Member findId(Member input) throws Exception {
        Member output = null;

        try {
            output = membersMapper.findId(input);

            if (output == null) {
                throw new Exception("Member 조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("아이디 검색에 실패했습니다", e);
            throw e;
        }

        return output;
    }
    
}
