package com.original.entity;

import com.original.constant.Grade;
import com.original.dto.MemberRegFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public static Member createMember(MemberRegFormDto memberRegFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberRegFormDto.getPassword());

        Member member = new Member();
        member.setName(memberRegFormDto.getName());
        member.setEmail(memberRegFormDto.getEmail());
        member.setPassword(password);
        member.setPhone(memberRegFormDto.getPhone());

        member.setGrade(Grade.USER);

        return member;
    }
}
