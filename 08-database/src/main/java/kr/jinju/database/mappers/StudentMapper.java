package kr.jinju.database.mappers;

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

import kr.jinju.database.models.Student;

@Mapper
public interface StudentMapper {
    /**
     * 학생 정보를 입력한다.
     * PK값은 파라미터로 전달된 INPUT 객체에 참조로 처리된다.
     * @param input - 입력할 학생 정보에 대한 모델 객체
     * @return 입력된 데이터 수
     */

    // @Insert 하고 import. ()안에 ""문자열로 감쌈. 마지막에 ;세미콜론 빼도된다.
    @Insert("INSERT INTO student (name, userid, grade, idnum, birthdate, tel, height, weight, deptno, profno) " + 
            "VALUES (#{name}, #{userId}, #{grade}, #{idNum}, #{birthDate}, #{tel}, #{height}, #{weight}, #{deptNo}, #{profNo});")
    public int insert(Student input);
    
    // 키 프로퍼티는 빈즈 멤버변수 이름이다.
    // INSERT문에서 필요한 PK에 대한 옵션 정의
    // useGeneratedKeys: AUTO_INCREMENT가 적용된 테이블인 경우 사용
    // keyProperty: 파라미터로 전달되는 MODEL 객체에서 PK에 대응되는 멤버변수
    // keyColumn: 테이블의 Primary Key 컬럼명
    @Options(useGeneratedKeys = true, keyProperty = "deptNo", keyColumn = "deptno")
    

    /**
     * UPDATE문을 수행하는 메서드 정의
     * @param input - 수정할 데이터에 대한 모델 객체
     * @return 수정한 데이터 수
     */
    @Update("UPDATE student SET name=#{name}, userid=#{userId}, grade=#{grade}, idnum=#{idNum}, birthdate=#{birthDate}, " + 
            "tel=#{tel}, height=#{height}, weight=#{weight}, deptno=#{deptNo}, profno=#{profNo} WHERE studno=#{studNo};")
    public int update(Student input);


    /**
     * DELETE문을 수행하는 메서드 정의
     * @param input - 삭제할 데이터에 대한 모델 객체
     * @return 삭제한 데이터 수
     */
    @Delete("DELETE FROM student WHERE studno=#{studNo};")
    public int delete(Student input);


    /**
     * 단일행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    @Select("SELECT studno, name, userid, grade, idnum, birthdate, tel, height, weight, deptno, profno FROM student WHERE studno=#{studNo};")
    // 조회 결과와 리턴할 MODEL 객체를 연결하기 위한 규칙 정의
    // property : MODEL 객체의 멤버변수 이름
    // column : SELECT문에 명시된 필드 이름(AS 옵션을 사용한 경우 별칭으로 명시)
    // -> import org.apache.ibatis.annotations.Result;
    // -> import org.apache.ibatis.annotations.Results;

    // DB 빈즈객체 담아서 저장. PK담아야함
    // 기존 xml에서 resultMap에 해당하는 값 넣어줘야함
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
        @Result(property = "deptNo", column = "deptNo"),
        @Result(property = "profNo", column = "profNo")
    })
    public Student selectItem(Student input);


    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회할 데이터에 대한 모델 객체
     * @return 조회한 데이터 수
     */
    // 학생 순으로 정렬. 구문이 길면 -> 띄어쓰기" + "로 추가한다.
    @Select("SELECT studno, name, userid, grade, idnum, birthdate, " + 
        "tel, height, weight, deptno, profno FROM student;")
    // 조회 결과와 MODEL의 맵핑이 이전 규칙과 동일한 경우 id 값으로 이전 규칙을 재사용
    @ResultMap("studentMap")
    public List<Student> selectList(Student input);
}
