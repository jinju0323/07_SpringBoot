package kr.jinju.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinju.database.exceptions.ServiceNoResultException;
import kr.jinju.database.helpers.WebHelper;
import kr.jinju.database.models.Professor;
import kr.jinju.database.services.ProfessorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ProfessorController {
    
    /** 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 교수 목록 화면
     * @param model 모델
     * @return 교수 목록 화면을 구현한 View 경로
     */
    @GetMapping("/professor")
    public String index(Model model) {

        List<Professor> professors = null;

        try {
            professors = professorService.getList(null);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
            return null;
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        // attributeName은 호출할 것과 이름 같게 하는게 안헷갈림
        model.addAttribute("professors", professors);
        return "/professor/index";
    }
    
    /**
     * 교수 상세 화면
     * @param model 모델
     * @param profNo 교수 번호
     * @return 교수 상세 화면을 구현한 View 경로
     */
    @GetMapping("/professor/detail/{profNo}")
    public String detail(Model model,
        @PathVariable("profNo") int profNo) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Professor params = new Professor();
        params.setProfNo(profNo);

        // 조회 결과를 저장할 객체 선언
        Professor professor = null;
        try {
            professor = professorService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // View에 데이터 전달
        model.addAttribute("professor", professor);
        return "/professor/detail";
    }
    
    /**
     * 학과 등록 화면
     * @return 학과 등록 화면을 구현한 View 경로
     */
    @GetMapping("/professor/add")
    public String add() {
        return "/professor/add";
    }

    /**
     * 학과 등록 처리
     * Action 페이지들은 View를 사용하지 않고 다른 페이지로 이돌해야 하므로
     * 메서드 상단에 @ResponseBody를 적용하여 View없이 직접 응답을 구현한다.
     * 
     * @param dname 학과 이름
     * @param loc 학과 위치
     */
    @ResponseBody     // <- View를 사용하지 않음(action 페이지에 꼭 적용)
    @PostMapping("/professor/add_ok")
    public void addOk(
        @RequestParam("name") String name,
        @RequestParam("userId") String userId,
        @RequestParam("position") String position,
        @RequestParam("sal") int sal,
        @RequestParam("hireDate") String hireDate,
        @RequestParam(value = "comm", required = false) Integer comm,
        @RequestParam("deptNo") int deptNo) {
        
        // 정상적인 경로로 접근한 경우 이전 페이지 주소는
        // 1) http://localhost/professor
        // 2) http://localhost/professor/detail/학과번호
        // 두 가지 경우가 있다.
        String referer = request.getHeader("referer");

        if (referer == null || !referer.contains("/professor")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        // 저장할 값들은 Beans에 담는다.
        Professor professor = new Professor();
        professor.setName(name);
        professor.setUserId(userId);
        professor.setPosition(position);
        professor.setSal(sal);
        professor.setHireDate(hireDate);
        // NULL일때 값 X
        if (comm == null) {
            professor.setComm(null);
        }
        professor.setComm(comm);
        professor.setDeptNo(deptNo);
        
        try {
            professorService.addItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // INSERT, UPDATE, DELETE 처리를 수행하는 경우에는 리다이렉트로 이동
        // INSERT 결과를 확인할 수 있는 상세 페이지로 이동해야 한다.
        // 상세 페이지에 조회 대상의 PK값을 전달해야 한다.
        webHelper.redirect("/professor/detail/" + professor.getProfNo(), "등록되었습니다.");
    }

    /**
     * 학과 삭제 처리
     * @param profNo 학과 번호
     */
    @ResponseBody
    @GetMapping("/professor/delete/{profNo}")
    public void delete(@PathVariable("profNo") int profNo) {

        // 이전 페이지 경로 검사 --> 정상적인 경로로 접근했는지 여부 확인
        String referer = request.getHeader("referer");
        
        if (referer == null || !referer.contains("/professor")) {
            webHelper.badRequest("올바르지 않은 접근입니다.");
            return;
        }

        Professor professor = new Professor();
        professor.setProfNo(profNo);

        try {
            professorService.deleteItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        webHelper.redirect("/professor", "삭제되었습니다.");
    }
    

    /**
     * 학과 수정 페이지
     * @param model     - Model 객체
     * @param profNo    - 학과 번호
     * @return View 페이지의 경로
     */
    @GetMapping("/professor/edit/{profNo}")
    public String edit(Model model,
        @PathVariable("profNo") int profNo) {

        // 파라미터로 받은 PK값을 beans객체에 담는다.
        // -> 검색조건으로 사용하기 위함
        Professor params = new Professor();
        params.setProfNo(profNo);

        // 수정할 데이터의 현재 값을 조회한다.
        Professor professor = null;
        try {
            professor = professorService.getItem(params);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        // View에 데이터 전달
        model.addAttribute("professor", professor);

        return "/professor/edit";
    }
    

    /**
     * 학과 수정 처리
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
        @RequestParam(value = "comm", required = false) Integer comm,
        @RequestParam("deptNo") int deptNo) {
        
        // 수정에 사용될 값을 Beans에 담는다.
        Professor professor = new Professor();
        professor.setProfNo(profNo);
        professor.setName(name);
        professor.setUserId(userId);
        professor.setPosition(position);
        professor.setSal(sal);
        professor.setHireDate(hireDate);
        // NULL일때 값 X
        if (comm == null) {
            professor.setComm(null);
        }
        professor.setComm(comm);
        professor.setDeptNo(deptNo);

        // 데이터를 수정한다.
        try {
            professorService.editItem(professor);
        } catch (ServiceNoResultException e) {
            webHelper.serverError(e);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 수정 결과를 확인하기 위해서 상세 페이지로 이동
        webHelper.redirect("/professor/detail/" + professor.getProfNo(), "수정되었습니다.");
    }
}
