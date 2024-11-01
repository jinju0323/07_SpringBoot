package kr.jinju.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinju.crud.helpers.Pagination;
import kr.jinju.crud.helpers.WebHelper;
import kr.jinju.crud.models.Department;
import kr.jinju.crud.models.Professor;
import kr.jinju.crud.models.Student;
import kr.jinju.crud.services.DepartmentService;
import kr.jinju.crud.services.ProfessorService;
import kr.jinju.crud.services.StudentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ProfessorController {
    
    /** 교수 관리 서비스 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    /** 학과 관리 서비스 객체 주입 */
    @Autowired
    private DepartmentService departmentService;

    /** 학생 관리 서비스 객체 주입 */
    @Autowired
    private StudentService studentService;

    /** WebHelper 주입 */
    @Autowired
    private WebHelper webHelper;

    /**
     * 교수 목록 화면
     * @param model 모델
     * @return 교수 목록 화면을 구현한 View 경로
     */
    @GetMapping({"/professor","/department/list"})
    public String list(Model model,
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
        Professor input = new Professor();
        input.setName(keyword);
        input.setUserId(keyword);

        List<Professor> output = null;

        try {
            //전체 게시글 수 조회
            totalCount = professorService.getCount(input);
            // 페이지 번호 계산 --> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 LIMIT절에서 사용될 값을 Beans의 sataic 변수에 저장
            Professor.setOffset(pagination.getOffset());
            Professor.setListCount(pagination.getListCount());

            output = professorService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("professors", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

        return "/professor/list";
    }
    
    /**
     * 교수 상세 화면
     * @param model 모델
     * @param profNo 교수 번호
     * @return 교수 상세 화면을 구현한 View 경로
     */
    @GetMapping("/professor/view/{profNo}")
    public String view(Model model,
        @PathVariable("profNo") int profNo) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Professor input = new Professor();
        input.setProfNo(profNo);

        Student input2 = new Student();
        input2.setProfNo(profNo);
        

        // 조회 결과를 저장할 객체 선언
        Professor output = null;
        List<Student> output2 = null;
        try {
            output = professorService.getItem(input);
            output2 = studentService.getList(input2);

        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // View에 데이터 전달
        model.addAttribute("professors", output);
        model.addAttribute("students", output2);
        return "/professor/view";
    }
    
    /**
     * 교수 등록 화면
     * @return 교수 등록 화면을 구현한 View 경로
     */
    @GetMapping("/professor/add")
    public String add() {
        return "/professor/add";
    }

    /**
     * 교수 수정 페이지
     * @param model     - Model 객체
     * @param deptNo    - 교수 번호
     * @return View 페이지의 경로
     */
    @GetMapping("/professor/edit/{profNo}")
    public String edit(Model model,
        @PathVariable("profNo") int profNo) {

        // 파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색조건으로 사용하기 위함
        Professor input = new Professor();
        input.setProfNo(profNo);

        // 수정할 데이터의 현재 값을 조회한다.
        Professor output = null;
        List<Department> output2 = null;

        try {
            output = professorService.getItem(input);
            output2 = departmentService.getList(null);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        // View에 데이터 전달
        model.addAttribute("professor", output);
        model.addAttribute("departments", output2);

        return "/professor/edit";
    }
    

    /**
     * 교수 수정 처리
     */
    @ResponseBody
    @PostMapping("/professor/edit_ok/{profNo}")
    public void edit_ok(
        @PathVariable("profNo") int profNo,
        @RequestParam("name") String name,
        @RequestParam("userId") String userId,
        @RequestParam("position") String position,
        @RequestParam("sal") int sal,
        @RequestParam("hireDate") String hireDate,
        @RequestParam(value = "comm", defaultValue = "") Integer comm,
        @RequestParam("deptNo") int deptNo) {
        
        // 수정에 사용될 값을 Beans에 담는다.
        Professor professor = new Professor();
        professor.setProfNo(profNo);
        professor.setName(name);
        professor.setUserId(userId);
        professor.setPosition(position);
        professor.setSal(sal);
        professor.setHireDate(hireDate);
        professor.setComm(comm);
        professor.setDeptNo(deptNo);

        // 데이터를 수정한다.
        try {
            professorService.editItem(professor);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/professor/detail/" + professor.getProfNo(), "수정되었습니다.");
    }
}
