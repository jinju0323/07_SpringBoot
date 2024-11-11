package kr.jinju.myshop.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.jinju.myshop.models.Member;

@Mapper
public interface MemberMapper {
    
    /**
     * 회원 정보를 입력한다.
     * PK값은 파라미터로 전달된 INPUT 객체에 참조로 처리된다.
     * @param input - 입력할 회원 정보에 대한 모델 객체
     * @return 입력된 데이터 수
     */
    @Insert("INSERT INTO members ("+
    "user_id, user_pw, user_name, email, phone, " +
    "birthday, gender, postcode, addr1, addr2, "+
    "photo, is_out, is_admin, login_date, reg_date, "+
    "edit_date) VALUES ( #{userId}, MD5(#{userPw}), #{userName}, "+
    "#{email}, #{phone}, #{birthday}, #{gender}, #{postcode}, "+
    "#{addr1}, #{addr2}, #{photo}, "+
    "'N', 'N', null, now(), now() )")
    // 키 프로퍼티는 빈즈 멤버변수 이름이다.
    // INSERT문에서 필요한 PK에 대한 옵션 정의
    // useGeneratedKeys: AUTO_INCREMENT가 적용된 테이블인 경우 사용
    // keyProperty: 파라미터로 전달되는 MODEL 객체에서 PK에 대응되는 멤버변수
    // keyColumn: 테이블의 Primary Key 컬럼명
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id" )
    public int insert(Member input);


    /**
     * UPDATE문을 수행하는 메서드 정의
     * @param input - 수정할 데이터에 대한 모델 객체
     * @return 수정한 데이터 수
     */
    @Update("UPDATE members SET "+
    "user_id = #{userId}, "+
    "user_pw = MD5(#{userPw}), "+
    "user_name = #{userName}, "+
    "email = #{email}, "+
    "phone = #{phone}, "+
    "birthday = #{birthday}, "+
    "gender = #{gender}, "+
    "postcode = #{postcode}, "+
    "addr1 = #{addr1}, "+
    "addr2 = #{addr2}, "+
    "photo = #{photo}, "+
    "edit_date = now() "+
    "WHERE id = #{id}")
    public int update(Member input);

    /**
     * DELETE문을 수행하는 메서드 정의
     * @param input - 삭제할 데이터에 대한 모델 객체
     * @return 삭제한 데이터 수
     */
    @Delete("DELETE FROM members WHERE id = #{id}")
    public int delete(Member input);

     /**
     * 단일행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT "+
            "id, user_id, user_pw, user_name, email, " +
            "phone, birthday, gender, postcode, addr1, "+
            "addr2, photo, is_out, is_admin, login_date, "+
            "reg_date, edit_date "+
            "FROM members "+
            "WHERE id = #{id}")
    // 조회 결과와 리턴할 MODEL 객체를 연결하기 위한 규칙 정의
    // property : MODEL 객체의 멤버변수 이름
    // column : SELECT문에 명시된 필드 이름(AS 옵션을 사용한 경우 별칭으로 명시)
    // -> import org.apache.ibatis.annotations.Result;
    // -> import org.apache.ibatis.annotations.Results;

    // DB 빈즈객체 담아서 저장. PK담아야함
    // 기존 xml에서 resultMap에 해당하는 값 넣어줘야함
    @Results(id="membersMap", value = {
        @Result(property="id", column="id"),
        @Result(property="userId", column="user_id"),
        @Result(property="userPw", column="user_pw"),
        @Result(property="userName", column="user_name"),
        @Result(property="email", column="email"),
        @Result(property="phone", column="phone"),
        @Result(property="birthday", column="birthday"),
        @Result(property="gender", column="gender"),
        @Result(property="postcode", column="postcode"),
        @Result(property="addr1", column="addr1"),
        @Result(property="addr2", column="addr2"),
        @Result(property="photo", column="photo"),
        @Result(property="isOut", column="is_out"),
        @Result(property="isAdmin", column="is_admin"),
        @Result(property="loginDate", column="login_date"),
        @Result(property="regDate", column="reg_date"),
        @Result(property="editDate", column="edit_date"),
    })
    public Member selectItem(Member input);

    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT id, user_id, user_pw, user_name, email,phone, birthday, gender, "+
    "postcode, addr1, addr2, photo, is_out, is_admin,login_date, reg_date, edit_date FROM members")
    @ResultMap("membersMap")
    public List<Member> selectList(Member input);

    /**
     * 검새 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회 조건을 담고 있는 객체
     * @return 조회 결과 수
     */
    @Select("<script>" + 
            "SELECT COUNT(*) FROM members" +
            "<where>" + 
            "<if test='userId != null'>user_id = #{userId}</if> " + 
            "<if test='email != null'>email = #{email}</if> " + 
            "</where>" + 
            "</script>")
    public int selectCount(Member input);
}
