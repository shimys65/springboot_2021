package com.sys.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.service.MemberService;
import com.sys.example.demo.vo.Member;

@Controller  //이것을 입력 후 ctr+shift+o하면 import 진행됨
public class UsrMemberController {
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname,
			String cellphoneNo, String email) {
		
		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
//먼저 리턴된 id가 -1이면 이미 사용중(browser에서 입력한 loginId가 일치하는 경우)
// 1 이상이 리턴되면 없는 loginId로 들어왔고 그에 맞는 id를 리턴
		if(id == -1) {
			return "해당 로그인 아이디는 이미 사용 중...";
		}
		
// 내가 Id하나 줄테니 그걸가지고 member 하나 찾아와---
		Member member = memberService.getMemberById(id);
		
		return member;
		
	}
	
}



