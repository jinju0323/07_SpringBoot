package kr.jinju.databasemysite.mappers;

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

import kr.jinju.databasemysite.models.BbsComment;

@Mapper
public interface BbsCommentMapper {
    /**
     * 학과 정보를 입력한다.
     * PK값은 파라미터로 전달된 INPUT 객체에 참조로 처리된다.
     * @param input - 입력할 학과 정보에 대한 모델 객체
     * @return 입력된 데이터 수
     */

    // @Insert 하고 import. ()안에 ""문자열로 감쌈. 마지막에 ;세미콜론 빼도된다.
    @Insert("INSERT INTO bbs_comment (writer, password, email, content, hit, " + 
            "reg_date, edit_date, members_id, bbs_documents_id) " + 
            "VALUES (#{writer}, #{password}, #{email}, #{content}, #{hit}, " + 
            "#{regDate}, #{editDate}, #{membersId}, #{bbsDocumentsId});")
    public int insert(BbsComment input);
    
    // 키 프로퍼티는 빈즈 멤버변수 이름이다.
    // INSERT문에서 필요한 PK에 대한 옵션 정의
    // useGeneratedKeys: AUTO_INCREMENT가 적용된 테이블인 경우 사용
    // keyProperty: 파라미터로 전달되는 MODEL 객체에서 PK에 대응되는 멤버변수
    // keyColumn: 테이블의 Primary Key 컬럼명
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    

    /**
     * UPDATE문을 수행하는 메서드 정의
     * @param input - 수정할 데이터에 대한 모델 객체
     * @return 수정한 데이터 수
     */
    @Update("UPDATE bbs_comment SET " + 
            "writer=#{writer}, password=#{password}, email=#{email}, content=#{content}, hit=#{hit}, " + 
            "reg_date=#{regDate}, edit_date=#{editDate}, members_id=#{membersId}, bbs_documents_id=#{bbsDocumentsId} " + 
            "WHERE id=#{id};")
    public int update(BbsComment input);


    /**
     * DELETE문을 수행하는 메서드 정의
     * @param input - 삭제할 데이터에 대한 모델 객체
     * @return 삭제한 데이터 수
     */
    @Delete("DELETE FROM bbs_comment WHERE id=#{id};")
    public int delete(BbsComment input);


    /**
     * 단일행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT id, writer, password, email, content, " + 
            "hit, reg_date, edit_date, members_id, bbs_documents_id " + 
            "FROM bbs_comment WHERE id=#{id};")
    // 조회 결과와 리턴할 MODEL 객체를 연결하기 위한 규칙 정의
    // property : MODEL 객체의 멤버변수 이름
    // column : SELECT문에 명시된 필드 이름(AS 옵션을 사용한 경우 별칭으로 명시)
    // -> import org.apache.ibatis.annotations.Result;
    // -> import org.apache.ibatis.annotations.Results;

    // DB 빈즈객체 담아서 저장. PK담아야함
    // 기존 xml에서 resultMap에 해당하는 값 넣어줘야함
    @Results(id = "bbs_commentMap", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "writer", column = "writer"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "content", column = "content"),
        @Result(property = "regDate", column = "reg_date"),
        @Result(property = "editDate", column = "edit_date"),
        @Result(property = "membersId", column = "members_id"),
        @Result(property = "bbsDocumentsId", column = "bbs_documents_id")
    })
    public BbsComment selectItem(BbsComment input);


    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    // 학과 순으로 정렬. 구문이 길면 -> 띄어쓰기" + "로 추가한다.
    @Select("SELECT id, writer, password, email, content, hit, reg_date, edit_date, members_id, bbs_documents_id " +
            "ORDER BY id DESC;")
    // 조회 결과와 MODEL의 맵핑이 이전 규칙과 동일한 경우 id 값으로 이전 규칙을 재사용
    @ResultMap("bbs_commentMap")
    public List<BbsComment> selectList(BbsComment input);
}
