package kr.jinju.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.jinju.crud.helpers.WebHelper;
import kr.jinju.crud.models.Member;
import kr.jinju.crud.services.MemberService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MemberController {
    
    /** 객체 주입 */
    @Autowired
    private MemberService memberService;

    @Autowired
    private WebHelper webHelper;

    /**
     * 회원 목록 화면
     * @param model 모델
     * @return 회원 목록 화면을 구현한 View 경로
     */
    @GetMapping("/member")
    public String index(Model model) {

        List<Member> members = null;

        try {
            members = memberService.getList(null);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        // attributeName은 호출할 것과 이름 같게 하는게 안헷갈림
        model.addAttribute("members", members);
        return "/member/index";
    }
    
}
