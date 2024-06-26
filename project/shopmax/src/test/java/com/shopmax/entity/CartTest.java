package com.shopmax.entity;

import com.shopmax.dto.MemberFormDto;
import com.shopmax.repository.CartRepository;
import com.shopmax.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional //트랜잭션 처리: 중간에 에러 발생 시 rollback을 시켜준다.
@TestPropertySource(locations="classpath:application-test.properties")
public class CartTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartRepository cartRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        //사용자가 입력한 회원가입 정보
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test10@gmail.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        //DTO -> Entity 객체로 변환 (JPA는 엔티티 객체로 CRUD를 진행)
        Member member = Member.createMember(memberFormDto, passwordEncoder);

        return member;
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 맵핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember(); //회원가입한 정보가 있는 엔티티 객체
        memberRepository.save(member); //insert

        Cart cart = new Cart(); //insert할 Cart 엔티티 객체

        //FK에 값 넣는 방법
        //- Cart 테이블의 member_id 컬럼은 FK이므로 Cart 객체의 Member 속성에 member 엔티티를 저장한다.
        cart.setMember(member);
        cartRepository.save(cart);

        //cart 테이블 레코드를 select 해오기
        //select * from cart where cart_id = ?
        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        //[중요]JPA에서는 join을 따로 하지 않아도 참조하고 있는 테이블의 결과까지 가지고 온다.
        System.out.println(savedCart);
        System.out.println(savedCart.getMember().getId());
        System.out.println(savedCart.getMember().getAddress());

        //Cart에 저장한 member id, member에 저장한 id가 같은지 확인 / Assertions.assertEquals: (2개 객체의) 값이 같은지 비교
        Assertions.assertEquals(savedCart.getMember().getId(), member.getId());

//        em.flush(); //강제 커밋(commit)

//        //영속성 컨텍스트에서 member, cart 엔티티를 분리
//        // - 영속성 컨텍스트에 엔티티가 없을 경우, JPA는 데이터베이스에서 데이터를 select 한다.
//        // - 이 때 실제 데이터베이스에서 장바구니 엔티티를 가지고 올 때 회원 엔티티도 같이 가지고 오는지 확인하기 위해 영속성 컨텍스트를 분리해준다.
//        // - 필수로 적용 필요한 항목은 아님
//        em.clear();
    }
}


