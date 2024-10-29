package kr.jinju.restfulapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class SimpleRestful {
    
    @GetMapping("/simple_department")
    public Map<String, Object> simpleDepartment() {
        Map<String, Object> department = new LinkedHashMap<String, Object>();

        department.put("deptno", 101);
        department.put("dname", "컴퓨터공학과");
        department.put("loc", "1호관 101호");

        return department;
    }
    
}
