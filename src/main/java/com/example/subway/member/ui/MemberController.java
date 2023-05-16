package com.example.subway.member.ui;

import com.example.subway.filter.PreAuthorize;
import com.example.subway.member.application.MemberService;
import com.example.subway.member.application.dto.MemberRequest;
import com.example.subway.member.application.dto.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest request) {
        MemberResponse memberResponse = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberResponse.getId())).build();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> findMemberById(@PathVariable long id) {
        MemberResponse member = memberService.findMemberById(id);
        return ResponseEntity.ok().body(member);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable long id, @RequestBody MemberRequest request) {
        memberService.updateMember(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMemberOfMine(@PreAuthorize MemberResponse member) {
        return ResponseEntity.ok().body(member);
    }
}
