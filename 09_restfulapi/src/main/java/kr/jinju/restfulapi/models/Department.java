package kr.jinju.restfulapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor      // <- 매개변수가 없는 생성자를 만들어줌 (for mybatis)
@AllArgsConstructor     // <- 모든 필드를 매개변수로 받는 생성자를 만들어줌
@Data
public class Department {
    private int deptNo;
    private String dname;
    private String loc;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
