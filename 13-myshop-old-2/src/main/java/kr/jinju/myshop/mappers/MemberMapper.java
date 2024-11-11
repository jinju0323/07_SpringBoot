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

    @Insert("INSERT INTO members (user_id, user_pw, name, email, phone, birthday, gender, postcode, addr1, addr2, is_out, is_admin, login_date, reg_date, edit_date) VALUES (#{userId}, MD5(#{userPw}), #{name}, #{email}, #{phone}, #{birthday}, #{gender}, #{postcode}, #{addr1}, #{addr2}, 'N', 'N', null, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Member input);

    @Update("UPDATE members SET user_id=#{userId}, user_pw=MD5(#{userPw}), name=#{name}, email=#{email}, phone=#{phone}, birthday=#{birthday}, gender=#{gender}, postcode=#{postcode}, addr1=#{addr1}, addr2=#{addr2},edit_date=now() WHERE id=#{id}")
    int update(Member input);

    @Delete("DELETE FROM members WHERE id=#{id}")
    int delete(Member input);

    @Select("SELECT id, user_id, user_pw, name, email, phone, birthday, gender, postcode, addr1, addr2, is_out, is_admin, login_date, reg_date, edit_date FROM members WHERE id=#{id}")
    @Results(id = "memberMap", value = {
        @Result(property = "id" ,column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "userPw", column = "user_pw"),
        @Result(property = "name", column = "name"),
        @Result(property = "email", column = "email"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "birthday", column = "birthday"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "postcode",column = "postcode"),
        @Result(property = "addr1", column = "addr1"),
        @Result(property = "addr2", column = "addr2"),
        @Result(property = "isOut", column = "is_out"),
        @Result(property = "isAdmin", column = "is_admin"),
        @Result(property = "loginDate", column = "login_date"),
        @Result(property = "regDate", column = "reg_date"),
        @Result(property = "editDate", column = "edit_date")})
    Member selectItem(Member input);

    @Select("SELECT id, user_id, user_pw, name, email, phone, birthday, gender, postcode, addr1, addr2, is_out, is_admin, login_date, reg_date, edit_date FROM members")
    @ResultMap("memberMap")
    List<Member> selectList(Member input);

    @Select("<script>" + //
            "SELECT COUNT(*) FROM members\n" + //
            "<where>\n" + //
            "<if test='userId != null'>user_id = #{userId}</if>\n" + //
            "<if test='email != null'>email = #{email}</if>\n" + //
            "</where>\n" + //
            "</script>")
    public int selectCount(Member input);
}

