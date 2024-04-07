package com.example.springbasic.ch01;

import com.example.springbasic.service.MyService;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    //직접 bean 등록
    //- 메서드명이 bean 이름이 됨
    @Bean
    public MyBean myBean() {
        return new MyBean(); //spring container로 객체 return > 앞으로 해당 객체 생명주기는 스프링에서 관리하게 됨
    }

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
