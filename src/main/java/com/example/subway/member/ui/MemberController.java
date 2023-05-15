package com.example.subway.member.ui;

import com.example.subway.member.application.MemberService;
import com.example.subway.member.application.dto.MemberRequest;
import com.example.subway.member.application.dto.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest request) {
        MemberResponse memberResponse = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberResponse.getId())).build();
    }
}
