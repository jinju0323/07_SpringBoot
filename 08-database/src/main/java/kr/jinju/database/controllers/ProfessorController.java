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



@Controller
public class ProfessorController {
    
    /** 객체 주입 */
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private WebHelper webHelper;

    /**
     * 학과 목록 화면
     * @param model 모델
     * @return 학과 목록 화면을 구현한 View 경로
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
    
}
