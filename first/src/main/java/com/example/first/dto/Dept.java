package com.example.first.dto;

import lombok.Getter;
import lombok.Setter;

//lombok 활용하여 getter/setter 생성 (어노테이션 이용)
@Setter
@Getter
public class Dept {
    private String deptno; //부서번호
    private String dept; // 부서이름
    private String loc; //지역

}
