package com.example.first.service;

import com.example.first.dao.DeptDao;
import com.example.first.dto.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //서비스 역할을 하는 클래스라는 의미(로 반드시 @Service 붙여야 함 / service의 자식 클래스에 추가)
public class DeptServiceImpl implements DeptService {
    @Autowired //DeptDao deptDao = new DeptDaoImpl; => 의존성 주입(Dependency Injection, DI라고도 함) => Autowired는 스프링에서 자동으로 객체 생성하는 역할
    DeptDao deptDao;

    @Override
    public List<Dept> selectList() {
        //service 부분은 원래 비즈니스 로직 작성
        return deptDao.selectList(); //List<Dept> 객체 리턴 (DB에 가져온 객체가 들어가있음)
    }

}
