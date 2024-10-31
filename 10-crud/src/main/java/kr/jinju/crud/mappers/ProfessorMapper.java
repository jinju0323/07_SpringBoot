package kr.jinju.crud.mappers;

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

import kr.jinju.crud.models.Professor;

@Mapper
public interface ProfessorMapper {
    /**
     * 학과 정보를 입력한다.
     * PK값은 파라미터로 전달된 INPUT 객체에 참조로 처리된다.
     * @param input - 입력할 학과 정보에 대한 모델 객체
     * @return 입력된 데이터 수
     */

    // @Insert 하고 import. ()안에 ""문자열로 감쌈. 마지막에 ;세미콜론 빼도된다.
    @Insert("INSERT INTO professor (" + 
                "name, userid, position, sal, hiredate, comm, deptno" + 
            ") VALUES (" + 
                "#{name}, #{userId}, #{position}, #{sal}, #{hireDate}, #{comm}, #{deptNo}" + 
                ")")    
    // 키 프로퍼티는 빈즈 멤버변수 이름이다.
    // INSERT문에서 필요한 PK에 대한 옵션 정의
    // useGeneratedKeys: AUTO_INCREMENT가 적용된 테이블인 경우 사용
    // keyProperty: 파라미터로 전달되는 MODEL 객체에서 PK에 대응되는 멤버변수
    // keyColumn: 테이블의 Primary Key 컬럼명
    @Options(useGeneratedKeys = true, keyProperty = "profNo", keyColumn = "profno")
    public int insert(Professor input);
    

    /**
     * UPDATE문을 수행하는 메서드 정의
     * @param input - 수정할 데이터에 대한 모델 객체
     * @return 수정한 데이터 수
     */
    @Update("UPDATE professor SET " + 
                "name=#{name}, " + 
                "userid=#{userId}, " + 
                "position=#{position}, " + 
                "sal=#{sal}, " + 
                "hiredate=#{hireDate}, " + 
                "comm=#{comm}, " + 
                "deptno=#{deptNo} " + 
            "WHERE profno=#{profNo}")
    public int update(Professor input);


    /**
     * DELETE문을 수행하는 메서드 정의
     * @param input - 삭제할 데이터에 대한 모델 객체
     * @return 삭제한 데이터 수
     */
    @Delete("DELETE FROM professor WHERE profno=#{profNo}")
    public int delete(Professor input);

    // 학과를 삭제하기 전에 학과에 소속된 교수 데이터를 삭제
    @Delete("DELETE FROM professor WHERE deptno=#{deptNo}")
    int deleteByDeptno(Professor input);


    /**
     * 단일행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT " + 
                "profno, name, userid, position, sal, " + 
                "DATE_FORMAT(hiredate, '%Y-%m-%d') AS hiredate, comm, " + 
                "p.deptno AS deptno, dname " + 
            "FROM professor p " +
            "INNER JOIN department d ON p.deptno = d.deptno " +  
            "WHERE profno=#{profNo}")
    // 조회 결과와 리턴할 MODEL 객체를 연결하기 위한 규칙 정의
    // property : MODEL 객체의 멤버변수 이름
    // column : SELECT문에 명시된 필드 이름(AS 옵션을 사용한 경우 별칭으로 명시)
    // -> import org.apache.ibatis.annotations.Result;
    // -> import org.apache.ibatis.annotations.Results;

    // DB 빈즈객체 담아서 저장. PK담아야함
    // 기존 xml에서 resultMap에 해당하는 값 넣어줘야함
    @Results(id = "professorMap", value = {
        @Result(property = "profNo", column = "profno"),
        @Result(property = "name", column = "name"),
        @Result(property = "userId", column = "userid"),
        @Result(property = "position", column = "position"),
        @Result(property = "sal", column = "sal"),
        @Result(property = "hireDate", column = "hiredate"),
        @Result(property = "comm", column = "comm"),
        @Result(property = "deptNo", column = "deptno"),
        @Result(property = "dname", column = "dname")
    })
    public Professor selectItem(Professor input);


    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    // 교수 순으로 정렬. 구문이 길면 -> 띄어쓰기" + "로 추가한다.
    @Select("<script>" +
            "SELECT " + 
                "profno, name, userid, position, sal, " + 
                "DATE_FORMAT(hiredate, '%Y-%m-%d') AS hiredate, comm, " + 
                "p.deptno AS deptno, dname " + 
            "FROM professor p " +
            "INNER JOIN department d ON p.deptno = d.deptno " +  
            "<where>" + 
            "<if test='name != null'>name LIKE concat('%', #{name}, '%')</if>" + 
            "<if test='userId != null'>OR userId LIKE concat('%', #{userId}, '%')</if>" + 
            "</where>" +
            "ORDER BY profno DESC " + 
            "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if>" + 
            "</script>")
    // 조회 결과와 MODEL의 맵핑이 이전 규칙과 동일한 경우 id 값으로 이전 규칙을 재사용
    @ResultMap("professorMap")
    public List<Professor> selectList(Professor input);

    /**
     * 검색 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회 결과 수
     */
    @Select("<script>" +
            "SELECT COUNT(*) AS cnt " + 
            "FROM professor p " +
            "INNER JOIN department d ON p.deptno = d.deptno " +  
            "<where>" + 
            "<if test='name != null'>name LIKE concat('%', #{name}, '%')</if> " + 
            "<if test='userId != null'>OR userId LIKE concat('%', #{userId}, '%')</if> " + 
            "</where>" + 
            "</script>")
    public int selectCount(Professor input);
}
