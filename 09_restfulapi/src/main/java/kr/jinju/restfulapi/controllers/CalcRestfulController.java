package kr.jinju.restfulapi.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import kr.jinju.restfulapi.helpers.RestHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CalcRestfulController {

    @Autowired
    private RestHelper restHelper;

    @GetMapping("/my_calc")
    public Map<String, Object> plus(
        HttpServletResponse response,
        @RequestParam(value = "x", defaultValue = "0") int x, 
        @RequestParam(value = "y", defaultValue = "0") int y) {

        /** 1) 파라미터 유효성 검사 */
        if (x < 0) {
            // case 1) 에러 내용을 직접 문자열로 전달
            return restHelper.badRequest("x는 0보다 커야합니다.");
        }

        if (y < 0) {
            // case 2) 에러 내용을 예외 객체로 전달하는 경우
            // catch 블록에서 사용하는 경우를 가정
            NumberFormatException ex = new NumberFormatException("y는 0보다 커야합니다.");
            return restHelper.badRequest(ex);
        }

        /** 2) 처리해야할 로직 수행 (DB연동 등을 가정) */
        int result = 0;

        try {
            result = x + y;
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        /** 3) 응답 결과를 구성 */
        Map<String, Object> output = new LinkedHashMap<String, Object>();

        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }
    
    @PostMapping("/my_calc")
    public Map<String, Object> minus(
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        /** 1) 파라미터 유효성 검사 */
        if (x < 0) {
            // case 1) 에러 내용을 직접 문자열로 전달
            return restHelper.badRequest("x는 0보다 커야합니다.");
        }

        if (y < 0) {
            // case 2) 에러 내용을 예외 객체로 전달하는 경우
            // catch 블록에서 사용하는 경우를 가정
            NumberFormatException ex = new NumberFormatException("y는 0보다 커야합니다.");
            return restHelper.badRequest(ex);
        }

        /** 2) 처리해야할 로직 수행 (DB연동 등을 가정) */
        int result = 0;

        try {
            result = x - y;
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        /** 3) 응답 결과를 구성 */
        Map<String, Object> output = new LinkedHashMap<String, Object>();

        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }

    @PutMapping("/my_calc")
    public Map<String, Object> times(
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        /** 1) 파라미터 유효성 검사 */
        if (x < 0) {
            // case 1) 에러 내용을 직접 문자열로 전달
            return restHelper.badRequest("x는 0보다 커야합니다.");
        }

        if (y < 0) {
            // case 2) 에러 내용을 예외 객체로 전달하는 경우
            // catch 블록에서 사용하는 경우를 가정
            NumberFormatException ex = new NumberFormatException("y는 0보다 커야합니다.");
            return restHelper.badRequest(ex);
        }

        /** 2) 처리해야할 로직 수행 (DB연동 등을 가정) */
        int result = 0;

        try {
            result = x * y;
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        /** 3) 응답 결과를 구성 */
        Map<String, Object> output = new LinkedHashMap<String, Object>();

        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }

    @DeleteMapping("/my_calc")
    public Map<String, Object> devied(
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        /** 1) 파라미터 유효성 검사 */
        if (x < 0) {
            // case 1) 에러 내용을 직접 문자열로 전달
            return restHelper.badRequest("x는 0보다 커야합니다.");
        }

        if (y < 0) {
            // case 2) 에러 내용을 예외 객체로 전달하는 경우
            // catch 블록에서 사용하는 경우를 가정
            NumberFormatException ex = new NumberFormatException("y는 0보다 커야합니다.");
            return restHelper.badRequest(ex);
        }

        /** 2) 처리해야할 로직 수행 (DB연동 등을 가정) */
        int result = 0;

        try {
            result = x / y;
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        /** 3) 응답 결과를 구성 */
        Map<String, Object> output = new LinkedHashMap<String, Object>();

        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }
}
