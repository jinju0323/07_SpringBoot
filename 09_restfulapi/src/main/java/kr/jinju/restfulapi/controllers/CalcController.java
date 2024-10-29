package kr.jinju.restfulapi.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Slf4j
@RestController
public class CalcController {

    @GetMapping("/my_calc")
    public Map<String, Object> getcalc(
        HttpServletResponse response,
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("x", x);
        result.put("y", y);
        result.put("result", x + y);

        return result;
    }
    
    @PostMapping("/my_calc")
    public Map<String, Object> postcalc(
        HttpServletResponse response,
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("x", x);
        result.put("y", y);
        result.put("result", x - y);

        return result;
    }

    @PutMapping("/my_calc")
    public Map<String, Object> putcalc(
        HttpServletResponse response,
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("x", x);
        result.put("y", y);
        result.put("result", x * y);

        return result;
    }

    @DeleteMapping("/my_calc")
    public Map<String, Object> deletecalc(
        HttpServletResponse response,
        @RequestParam("x") int x, 
        @RequestParam("y") int y) {

        Map<String, Object> result = new LinkedHashMap<String, Object>();

        if (y != 0) {
            result.put("x", x);
            result.put("y", y);
            result.put("result", x / y);   
        } else {
            result.put("x", x);
            result.put("y", y);
            result.put("result", "0으로 나눌 수 없습니다.");
        }

        return result;
    }
}
