package com.example.subway;

import com.example.subway.member.domain.Member;
import com.example.subway.member.domain.MemberRepository;
import com.example.subway.member.domain.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
    private final MemberRepository memberRepository;

    @Autowired
    public DataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void loadData() {
        memberRepository.save(new Member("admin@email.com", "password", 20, List.of(RoleType.ROLE_ADMIN.name())));
        memberRepository.save(new Member("member@email.com", "password", 20, List.of(RoleType.ROLE_MEMBER.name())));
    }
}
