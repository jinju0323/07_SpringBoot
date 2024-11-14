package kr.jinju.myshop.mappers;

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

import java.util.List;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO members (user_id, user_pw, user_name, email, phone, birthday, gender, postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date) VALUES (#{userId}, MD5(#{userPw}), #{userName}, #{email}, #{phone}, #{birthday}, #{gender}, #{postcode}, #{addr1}, #{addr2}, #{photo}, 'N', 'N', null, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Member input);

    @Update("<script>" +
            "UPDATE members " + 
            "SET " + 

            // 아이디는 수정하지 않는다.

            "user_name = #{userName}, " + 

            // 신규 비밀번호가 입력 된 경우만 UPDATE절에 추가함.
            "<if test='newUserPw != null and newUserPw != \"\"'>user_pw = MD5(#{newUserPw}),</if> " + 
            
            "email=#{email}, " + 
            "phone=#{phone}, " + 
            "birthday=#{birthday}, " + 
            "gender=#{gender}, " + 
            "postcode=#{postcode}, " + 
            "addr1=#{addr1}, " + 
            "addr2=#{addr2}, " + 
            //"photo=#{photo}, " + 
            "edit_date=now() " + 
            // 세션의 일련번호와 입력한 비밀번호가 일치할 경우만 수정
            "WHERE id=#{id} AND user_pw = MD5(#{userPw})" + 
            "</script>")
    int update(Member input);

    @Delete("DELETE FROM members WHERE id=#{id}")
    int delete(Member input);

    @Select("SELECT id, user_id, user_pw, user_name, email, phone, birthday, gender, postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date FROM members WHERE id=#{id}")
    @Results(id = "memberMap", value = {
        @Result(property = "id" ,column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "userPw", column = "user_pw"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "email", column = "email"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "birthday", column = "birthday"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "postcode",column = "postcode"),
        @Result(property = "addr1", column = "addr1"),
        @Result(property = "addr2", column = "addr2"),
        @Result(property = "photo", column = "photo"),
        @Result(property = "isOut", column = "is_out"),
        @Result(property = "isAdmin", column = "is_admin"),
        @Result(property = "loginDate", column = "login_date"),
        @Result(property = "regDate", column = "reg_date"),
        @Result(property = "editDate", column = "edit_date")})
    Member selectItem(Member input);

    @Select("SELECT id, user_id, user_pw, user_name, email, phone, birthday, gender, postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date FROM members")
    @ResultMap("memberMap")
    List<Member> selectList(Member input);

    @Select("<script>" + 
            "SELECT COUNT(*) FROM members " + 
            "<where>" + 
            "<if test='userId != null'>user_id = #{userId}</if>" + 
            "<if test='email != null'>email = #{email}</if>" + 
            "<if test='id != 0'>AND id != #{id}</if>" + 
            "</where>" + 
            "</script>")
    public int selectCount(Member input);

    /**
     * 아이디 찾기
     * @param input
     * @return
     */
    @Select("SELECT user_id FROM members " + 
            "WHERE user_name = #{userName} AND email = #{email} AND is_out = 'N'")
    @ResultMap("memberMap")
    public Member findId(Member input);

    /**
     * 비밀번호 재설정
     * @param input
     * @return
     */
    @Update("UPDATE members SET user_pw = MD5(#{userPw}) " + 
            "WHERE user_id = #{userId} AND email = #{email} AND is_out = 'N'")
    public int resetPw(Member input);

    /**
     * 로그인 시 아이디와 비밀번호 일치 확인
     * @param input
     * @return
     */
    @Select("SELECT " + 
            "id, user_id, user_pw, user_name, email, " + 
            "phone, birthday, gender, postcode, addr1, " + 
            "addr2, photo, is_out, is_admin, login_date, " + 
            "reg_date, edit_date " + 
            "FROM members " + 
            "WHERE user_id = #{userId} AND user_pw = MD5(#{userPw}) AND is_out = 'N'")
    @ResultMap("memberMap")
    public Member login(Member input);

    /**
     * 로그인 세션 현재시간 남기기
     * @param input
     * @return
     */
    @Update("UPDATE members SET login_date=NOW() " + 
            "WHERE id = #{id} AND is_out = 'N'")
    public int updateLoginDate(Member input);

    /**
     * 회원탈퇴 UPDATE : is_out='Y', edit_date=NOW()
     * @param input
     * @return
     */
    @Update("UPDATE members " + 
            "SET is_out='Y', edit_date=NOW() " + 
            "WHERE id = #{id} AND user_pw = MD5(#{userPw}) AND is_out = 'N'")
    public int out(Member input);

    /**
     * 회원탈퇴 전 회원이 업로드한 PHOTO 삭제
     * @return
     */
    @Select("SELECT photo FROM members " + 
            "WHERE is_out = 'Y' AND " + 
            "edit_date < DATE_ADD(NOW(), interval -1 minute) AND " +
            "photo IS NOT NULL")
    @ResultMap("memberMap")
    public List<Member> selectOutMemberPhoto();

    /**
     * 회원탈퇴 DELETE : 수정시간에서 1분 이후 회원 삭제
     * @return
     */
    @Delete("DELETE FROM members " + 
            "WHERE is_out = 'Y' AND " + 
            "edit_date < DATE_ADD(NOW(), interval -1 minute)")
    public int deleteOutMembers();
}

