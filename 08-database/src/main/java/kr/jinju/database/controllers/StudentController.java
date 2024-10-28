package kr.jinju.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinju.database.helpers.Pagination;
import kr.jinju.database.helpers.WebHelper;
import kr.jinju.database.models.Department;
import kr.jinju.database.models.Professor;
import kr.jinju.database.models.Student;
import kr.jinju.database.services.DepartmentService;
import kr.jinju.database.services.ProfessorService;
import kr.jinju.database.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class StudentController {
    
    /** 학생 관리 서비스 객체 주입 */
    @Autowired
    private StudentService studentService;

    /** 교수 관리 서비스 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    /** 학과 관리 서비스 객체 주입 */
    @Autowired
    private DepartmentService departmentService;

    /** WebHelper 주입 */
    @Autowired
    private WebHelper webHelper;

    /** HttpServletRequest 주입  */
    @Autowired
    private HttpServletRequest request;

    /**
     * 학생 목록 화면
     * @param model 모델
     * @return 학생 목록 화면을 구현한 View 경로
     */
    @GetMapping("/student")
    public String index(Model model,
            // 검색어 파라미터 (페이지가 처음 열릴 때는 값 없음. 필수(required)가 아님)
            @RequestParam(value = "keyword", required = false) String keyword,
            // 페이지 구현에서 사용할 페이지 번호
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {
        
        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5;  // 한 그룹당 표시할 페이지 번호 수
        
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
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("students", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

        return "/student/index";
    }
    
    /**
     * 학생 상세 화면
     * @param model 모델
     * @param studNo 학생 번호
     * @return 학생 상세 화면을 구현한 View 경로
     */
    @GetMapping("/student/detail/{studNo}")
    public String detail(Model model,
        @PathVariable("studNo") int studNo) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Student input = new Student();
        input.setStudNo(studNo);

        // 조회 결과를 저장할 객체 선언
        Student student = null;
        try {
            student = studentService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // View에 데이터 전달
        model.addAttribute("student", student);
        return "/student/detail";
    }
    
    /**
     * 학생 등록 화면
     * @return 학생 등록 화면을 구현한 View 경로
     */
    @GetMapping("/student/add")
    public String add(Model model) {
        // 모든 학과 목록을 조회하여 View에 전달한다.
        List<Department> output = null;
        // 모든 담당교수 목록을 조회하여 View에 전달한다.
        List<Professor> output2 = null;

        try {
            output = departmentService.getList(null);
            output2 = professorService.getList(null);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        model.addAttribute("departments", output);
        model.addAttribute("professors", output2);
        
        return "/student/add";
    }

    /**
     * 학생 등록 처리
     * Action 페이지들은 View를 사용하지 않고 다른 페이지로 이돌해야 하므로
     * 메서드 상단에 @ResponseBody를 적용하여 View없이 직접 응답을 구현한다.
     * 
     * @param dname 학생 이름
     * @param loc 학생 위치
     */
    @ResponseBody     // <- View를 사용하지 않음(action 페이지에 꼭 적용)
    @PostMapping("/student/add_ok")
    public void addOk(
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
        
        // 정상적인 경로로 접근한 경우 이전 페이지 주소는
        // 1) http://localhost/student
        // 2) http://localhost/student/detail/학생번호
        // 두 가지 경우가 있다.
        String referer = request.getHeader("referer");

        if (referer == null || !referer.contains("/student")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        // 저장할 값들은 Beans에 담는다.
        Student student = new Student();
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
        
        try {
            studentService.addItem(student);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // INSERT, UPDATE, DELETE 처리를 수행하는 경우에는 리다이렉트로 이동
        // INSERT 결과를 확인할 수 있는 상세 페이지로 이동해야 한다.
        // 상세 페이지에 조회 대상의 PK값을 전달해야 한다.
        webHelper.redirect("/student/detail/" + student.getStudNo(), "등록되었습니다.");
    }

    /**
     * 학생 삭제 처리
     * @param studNo 학생 번호
     */
    @ResponseBody
    @GetMapping("/student/delete/{studNo}")
    public void delete(@PathVariable("studNo") int studNo) {

        // 이전 페이지 경로 검사 --> 정상적인 경로로 접근했는지 여부 확인
        String referer = request.getHeader("referer");
        
        if (referer == null || !referer.contains("/student")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        Student student = new Student();
        student.setStudNo(studNo);

        try {
            studentService.deleteItem(student);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        webHelper.redirect("/student", "삭제되었습니다.");
    }
    

    /**
     * 학생 수정 페이지
     * @param model     - Model 객체
     * @param studNo    - 학생 번호
     * @return View 페이지의 경로
     */
    @GetMapping("/student/edit/{studNo}")
    public String edit(Model model,
        @PathVariable("studNo") int studNo) {

        // 파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색조건으로 사용하기 위함
        Student input = new Student();
        input.setStudNo(studNo);

        // 수정할 데이터와 모든 학과 목록과 담당교수 목록을 조회한다.
        Student output = null;
        List<Department> output2 = null;
        List<Professor> output3 = null;
        try {
            output = studentService.getItem(input);
            output2 = departmentService.getList(null);
            output3 = professorService.getList(null);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        // View에 데이터 전달
        model.addAttribute("student", output);
        model.addAttribute("departments", output2);
        model.addAttribute("professors", output3);

        return "/student/edit";
    }
    

    /**
     * 학생 수정 처리
     */
    @ResponseBody
    @PostMapping("/student/edit_ok/{studNo}")
    public void edit_ok(
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

        // 데이터를 수정한다.
        try {
            studentService.editItem(student);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/student/detail/" + student.getStudNo(), "수정되었습니다.");
    }
}
