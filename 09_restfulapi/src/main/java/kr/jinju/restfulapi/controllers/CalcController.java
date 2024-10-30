package kr.jinju.restfulapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalcController {
    /**
     * 계산 입력 폼을 출력하는 메서드
     * 웹 브라우저에게 HTML을 전송한다
     * 프론트엔드 역할
     * 
     * @return - 계산 입력 폼
     */
    @GetMapping("/calc")
    public String calcForm() {
        return "calc.html";
    }
}
