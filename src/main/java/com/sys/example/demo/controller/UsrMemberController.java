package com.sys.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.service.MemberService;
import com.sys.example.demo.util.Ut;
import com.sys.example.demo.vo.Member;
import com.sys.example.demo.vo.ResultData;

@Controller  //이것을 입력 후 ctr+shift+o하면 import 진행됨
public class UsrMemberController {
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname,
			String cellphoneNo, String email) {
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}

		if (Ut.empty(name)) {
			return ResultData.from("F-3", "name(을)를 입력해주세요.");
		}

		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "nickname(을)를 입력해주세요.");
		}

		if (Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "cellphoneNo(을)를 입력해주세요.");
		}

		if (Ut.empty(email)) {
			return ResultData.from("F-6", "email(을)를 입력해주세요.");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);		

		if(joinRd.isFail()) {
			return (ResultData)joinRd;
		}		

		Member member = memberService.getMemberById(joinRd.getData1());		
//		return ResultData.from(joinRd.getResultCode(), joinRd.getMsg(), member);
		return ResultData.newData(joinRd, member);
		
	}
	
}



