package kr.jiye.myshop.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

import kr.jiye.myshop.models.Member;

@Mapper
public interface MembersMapper {

    @Insert("INSERT INTO members (user_id, user_pw, user_name, email, phone, birthday, gender, postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date) VALUES (#{userId}, MD5(#{userPw}), #{userName}, #{email}, #{phone}, #{birthday}, #{gender}, #{postcode}, #{addr1}, #{addr2}, #{photo}, 'N', 'N', null, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Member input);

    @Update("UPDATE members SET user_id=#{userId}, user_pw=MD5(#{userPw}), user_name=#{userName}, email=#{email}, phone=#{phone}, birthday=#{birthday}, gender=#{gender}, postcode=#{postcode}, addr1=#{addr1}, addr2=#{addr2}, photo=#{photo},edit_date=now() WHERE id=#{id}")
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

    @Select("<script>" + //
            "SELECT COUNT(*) FROM members\n" + //
            "<where>\n" + //
            "<if test='userId != null'>user_id = #{userId}</if>\n" + //
            "<if test='email != null'>email = #{email}</if>\n" + //
            "</where>\n" + //
            "</script>")
    public int selectCount(Member input);

    @Select("SELECT user_id FROM members " + //
            "WHERE user_name = #{userName} AND email = #{email}")
    @ResultMap("memberMap")
    public Member findId(Member input);
}

