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

import kr.jinju.crud.models.Student;

@Mapper
public interface StudentMapper {
    /**
     * 학생 정보를 입력한다.
     * PK값은 파라미터로 전달된 INPUT 객체에 참조로 처리된다.
     * @param input - 입력할 학생 정보에 대한 모델 객체
     * @return 입력된 데이터 수
     */

    @Insert("INSERT INTO student (" + 
                "name, userid, grade, idnum, birthdate, " + 
                "tel, height, weight, deptno, profno" + 
            ") VALUES (" + 
                "#{name}, #{userId}, #{grade}, #{idNum}, #{birthDate}, " + 
                "#{tel}, #{height}, #{weight}, #{deptNo}, #{profNo}" + 
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "studNo", keyColumn = "studno")
    public int insert(Student input);

    /**
     * 학생 정보를 수정한다.
     * @param input - 수정할 데이터에 대한 모델 객체
     * @return 수정한 데이터 수
     */
    @Update("UPDATE student SET " + 
                "name=#{name}, " + 
                "userid=#{userId}, " + 
                "grade=#{grade}, " + 
                "idnum=#{idNum}, " + 
                "birthdate=#{birthDate}, " + 
                "tel=#{tel}, " + 
                "height=#{height}, " + 
                "weight=#{weight}, " + 
                "deptno=#{deptNo}, " + 
                "profno=#{profNo} " + 
            "WHERE studno=#{studNo}")
    public int update(Student input);


    /**
     * 학생 정보를 삭제한다. 
     * @param input - 삭제할 데이터에 대한 모델 객체
     * @return 삭제한 데이터 수
     */
    @Delete("DELETE FROM student WHERE studno=#{studNo}")
    public int delete(Student input);

    // 학과를 삭제하기 전에 학과에 소속된 학생 데이터를 삭제
    @Delete("DELETE FROM student WHERE deptno=#{deptNo}")
    int deleteByDeptno(Student input);

    // 교수를 삭제하기 전에 교수에게 소속된 학생들과의 연결을 해제
    // -> profno 컬럼이 null 허용으로 설정되야 함
    @Update("UPDATE student SET profno = Null WHERE profno = #{profNo}")
    int updateByProfno(Student input);


    /**
     * 단일행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT " + 
                "studno, s.name AS name, s.userid AS userid, grade, idnum, " + 
                "DATE_FORMAT(birthdate, '%Y-%m-%d') AS birthdate, " + 
                "tel, height, weight, dname, p.name AS pname, " + 
                "s.deptno AS deptno, s.profno AS profno " + 
            "FROM student s " + 
            "INNER JOIN department d ON s.deptno = d.deptno " +
            "LEFT OUTER JOIN professor p ON s.profno = p.profno " + 
            "WHERE studno=#{studNo}")

    @Results(id = "studentMap", value = {
        @Result(property = "studNo", column = "studno"),
        @Result(property = "name", column = "name"),
        @Result(property = "userId", column = "userid"),
        @Result(property = "grade", column = "grade"),
        @Result(property = "idNum", column = "idnum"),
        @Result(property = "birthDate", column = "birthdate"),
        @Result(property = "tel", column = "tel"),
        @Result(property = "height", column = "height"),
        @Result(property = "weight", column = "weight"),
        @Result(property = "deptNo", column = "deptno"),
        @Result(property = "profNo", column = "profno"),
        @Result(property = "dname", column = "dname"),
        @Result(property = "pname", column = "pname")
    })
    public Student selectItem(Student input);


    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    // 학생 순으로 정렬. 구문이 길면 -> 띄어쓰기" + "로 추가한다.
    @Select("<script>" +
            "SELECT " + 
                "studno, s.name AS name, s.userid AS userid, grade, idnum, " + 
                "DATE_FORMAT(birthdate, '%Y-%m-%d') AS birthdate, " + 
                "tel, height, weight, dname, p.name AS pname, " + 
                "s.deptno AS deptno, s.profno AS profno " + 
            "FROM student s " + 
            "INNER JOIN department d ON s.deptno = d.deptno " +
            "LEFT OUTER JOIN professor p ON s.profno = p.profno " +
            "<where>" +
            "<if test='deptNo != 0'>p.deptNo = #{deptNo}</if>" +
            "<if test='profNo != 0'>p.profNo = #{profNo}</if>" +
            "<if test='name != null'>s.name LIKE concat('%', #{name}, '%')</if>" +
            "<if test='userId != null'>OR s.userid LIKE concat('%', #{userId}, '%')</if>" +
            "</where>" +
            "ORDER BY studno DESC " +
            "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if> " +
            "</script>")
    // 조회 결과와 MODEL의 맵핑이 이전 규칙과 동일한 경우 id 값으로 이전 규칙을 재사용
    @ResultMap("studentMap")
    public List<Student> selectList(Student input);

    /**
     * 검색 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회 결과 수
     */
    @Select("<script>" +
            "SELECT COUNT(*) AS cnt " + 
            "FROM student s " +
            "INNER JOIN department d ON s.deptno = d.deptno " +
            "LEFT OUTER JOIN professor p ON s.profno = p.profno " +
            "<where>" + 
            "<if test='name != null'>s.name LIKE concat('%', #{name}, '%')</if>" +
            "<if test='userId != null'>OR s.userid LIKE concat('%', #{userId}, '%')</if>" +
            "</where>" + 
            "</script>")
    public int selectCount(Student input);
}
