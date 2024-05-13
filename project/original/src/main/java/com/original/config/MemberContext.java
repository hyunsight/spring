package com.original.config;

import com.original.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class MemberContext extends User {

    private final String name;
    private final String phone;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);

        this.name = member.getName();
        this.phone = member.getPhone();
    }
}
