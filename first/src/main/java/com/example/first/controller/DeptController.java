package com.example.first.controller;

import com.example.first.dto.Dept;
import com.example.first.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController //해당 클래스를 컨트롤러롤 사용하겠다는 의미
public class DeptController {
    @Autowired
    DeptService deptService; //DeptService deptService = new DeptServiceImpl();와 동일한 의미

    //@PostMapping //post 방식으로 요청이 올 때 해당 main() 메소드를 실행, post는 보통 form 태그 이용할 때 많이 사용
    @GetMapping("/main") //get 방식으로 요청이 올 때 해당 main() 메소드를 실행, GetMapping()의 () 안에는 경로 지정
    //[참조] 주소를 입력해서 들어오는 방식은 모두 get 방식임
    public List<Dept> main() {
        List<Dept> list = deptService.selectList();
        return list; //리턴에 주는 값을 Response(객체) body에 담아서 view에 전달
    }
}
