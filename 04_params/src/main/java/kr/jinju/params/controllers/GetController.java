package kr.jinju.params.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class GetController {
    @GetMapping("/get/home")
    public String home() {
        return "get/home";
    }

    // GET 방식의 파라미터를 전송받기 위한 컨트롤러 메서드 (쿼리스트링 파라미터 : GET 방식)
    @GetMapping("/get/result")
    public String result(Model model,
            // GET 파라미터 받기 ==> ?answer=100 => 요청 파라미터. ?뒤에 받아오는 값이 쿼리스트링 값이다.
            // 기본값이 필요 없을 경우 `@RequestParam("answer") int myAnswer` 로 사용가능
            @RequestParam(value = "answer", defaultValue = "0") int myAnswer) {

        String result = null;

        if (myAnswer == 300) {
            result = "정답";
        } else {
            result = "오답";
        }

        // Model 객체에 데이터를 답아서 전달
        model.addAttribute("result", result);
        model.addAttribute("myAnswer", myAnswer);

        return "get/result";
    }
    
}
