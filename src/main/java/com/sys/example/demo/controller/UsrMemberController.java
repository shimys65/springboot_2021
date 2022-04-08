package com.sys.example.demo.controller;

import javax.servlet.http.HttpSession;

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
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpsession, String loginId, String loginPw) {
		boolean isLogined = false;
// 세션에 저장된 내용 확인		
		if(httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("F-5", "이미 로그인 되었음.");
		}
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}
// loginId와 loginPw를 입력받고, member 테이블에서 loginId를 가져옮	
		Member member = memberService.getMemberByLoginId(loginId);		

		if(member == null) { // loginId 유. 무를 확인
			return ResultData.from("F-3", "존재하지 않습니다.");
		}
		
		if(member.getLoginPw().equals(loginPw) == false) { // loginPw 일치를 확인
			return ResultData.from("F-4", "비밀번호 틀려요.");
		}
//입력 성공하면 name:loginedMemberId와 value:getId로 세션에 저장
		httpsession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1", (String) Ut.f("%s님 환영합니다.", member.getNickname()));		
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData<Member> doLogout(HttpSession httpsession) {
		boolean isLogined = false;
		
		if(httpsession.getAttribute("loginedMemberId") == null) {
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("S-1", "이미 로그아웃 되었음.");
		}
// 세션정보 삭제
		httpsession.removeAttribute("loginedMemberId");
		
		return ResultData.from("S-2", "로그아웃 되었음.");		
	}
	
}



