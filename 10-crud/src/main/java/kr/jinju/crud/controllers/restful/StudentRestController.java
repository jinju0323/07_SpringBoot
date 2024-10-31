package kr.jinju.crud.controllers.restful;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.jinju.crud.helpers.Pagination;
import kr.jinju.crud.helpers.RestHelper;
import kr.jinju.crud.models.Student;
import kr.jinju.crud.services.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class StudentRestController {
    
    /** 학생 관리 서비스 객체 주입 */
    @Autowired
    private StudentService studentService;

    /** RestHelper 주입 */
    @Autowired
    private RestHelper restHelper;

    /**
     * 학생 목록 화면
     * @param model 모델
     * @return 학생 목록 화면을 구현한 View 경로
     */
    @GetMapping("/api/student")
    public Map<String, Object> getlist(
            // 검색어 파라미터 (페이지가 처음 열릴 때는 값 없음. 필수(required)가 아님)
            @RequestParam(value = "keyword", required = false) String keyword,
            // 페이지 구현에서 사용할 페이지 번호
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {
        
        int totalCount = 0; // 전체 게시글 수
        int listCount = 5; // 한 페이지당 표시할 목록 수
        int pageCount = 3;  // 한 그룹당 표시할 페이지 번호 수
        
        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        // 조회 조건에 사용할 객체
        Student input = new Student();
        input.setName(keyword);
        input.setUserId(keyword);

        List<Student> output = null;

        try {
            //전체 게시글 수 조회
            totalCount = studentService.getCount(input);
            // 페이지 번호 계산 --> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 LIMIT절에서 사용될 값을 Beans의 sataic 변수에 저장
            Student.setOffset(pagination.getOffset());
            Student.setListCount(pagination.getListCount());

            output = studentService.getList(input);
        } catch (Exception e) {
            restHelper.serverError(e);
            return null;
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("students", output);
        data.put("keyword", keyword);
        data.put("pagination", pagination);

        return restHelper.sendJson(data);
    }
    
    /**
     * 학생 상세 화면
     * @param model 모델
     * @param studNo 학생 번호
     * @return 학생 상세 화면을 구현한 View 경로
     */
    @GetMapping("/api/student/{studNo}")
    public Map<String, Object> detail(
        @PathVariable("studNo") int studNo) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Student input = new Student();
        input.setStudNo(studNo);

        // 조회 결과를 저장할 객체 선언
        Student student = null;
        try {
            student = studentService.getItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", student);

        return restHelper.sendJson(data);
    }

    /**
     * 학생 등록 처리
     * Action 페이지들은 View를 사용하지 않고 다른 페이지로 이돌해야 하므로
     * 메서드 상단에 @ResponseBody를 적용하여 View없이 직접 응답을 구현한다.
     * 
     * @param dname 학생 이름
     * @param loc 학생 위치
     */
    @PostMapping("/api/student")
    public Map<String, Object> addOk(
        @RequestParam("name") String name,
        @RequestParam("userId") String userId,
        @RequestParam("grade") int grade,
        @RequestParam("idNum") String idNum,
        @RequestParam("birthDate") String birthDate,
        @RequestParam("tel") String tel,
        @RequestParam("height") int height,
        @RequestParam("weight") int weight,
        @RequestParam("deptNo") int deptNo,
        @RequestParam(value = "profNo", defaultValue = "") Integer profNo) {
        
        // 저장할 값들은 Beans에 담는다.
        Student input = new Student();
        input.setName(name);
        input.setUserId(userId);
        input.setGrade(grade);
        input.setIdNum(idNum);
        input.setBirthDate(birthDate);
        input.setTel(tel);
        input.setHeight(height);
        input.setWeight(weight);
        input.setDeptNo(deptNo);
        input.setProfNo(profNo);
        
        Student output = null;

        try {
            output = studentService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", output);

        return restHelper.sendJson(data);
    }

    /**
     * 학생 삭제 처리
     * @param studNo 학생 번호
     */
    @DeleteMapping("/api/student/{studNo}")
    public Map<String, Object> delete(@PathVariable("studNo") int studNo) {

        Student input = new Student();
        input.setStudNo(studNo);

        try {
            studentService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }
    
    /**
     * 학생 수정 처리
     */
    @PutMapping("/api/student/{studNo}")
    public Map<String, Object> edit_ok(
        @PathVariable("studNo") int studNo,
        @RequestParam("name") String name,
        @RequestParam("userId") String userId,
        @RequestParam("grade") int grade,
        @RequestParam("idNum") String idNum,
        @RequestParam("birthDate") String birthDate,
        @RequestParam("tel") String tel,
        @RequestParam("height") int height,
        @RequestParam("weight") int weight,
        @RequestParam("deptNo") int deptNo,
        @RequestParam(value = "profNo", defaultValue = "") Integer profNo) {
        
        // 수정에 사용될 값을 Beans에 담는다.
        Student student = new Student();
        student.setStudNo(studNo);
        student.setName(name);
        student.setUserId(userId);
        student.setGrade(grade);
        student.setIdNum(idNum);
        student.setBirthDate(birthDate);
        student.setTel(tel);
        student.setHeight(height);
        student.setWeight(weight);
        student.setDeptNo(deptNo);
        student.setProfNo(profNo);

        Student output = null;

        try {
            output = studentService.editItem(student);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", output);

        return restHelper.sendJson(data);
    }
}
