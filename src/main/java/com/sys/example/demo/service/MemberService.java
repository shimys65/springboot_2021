package com.sys.example.demo.service;

import org.springframework.stereotype.Service;

import com.sys.example.demo.repository.MemberRepository;
import com.sys.example.demo.util.Ut;
import com.sys.example.demo.vo.Member;
import com.sys.example.demo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, 
									String cellphoneNo, String email) {
		//로그인 아이디 중복 검사
		Member oldMember = getMemberByLoginId(loginId);	
		
		if(oldMember != null) { //이미 browser에서 입력한 loginId가 사용중
			return ResultData.from("F-7", (String) Ut.f("해당 로그인아이디(%s)는 사용중.", loginId));
		}
		
		//이름+이메일 중복 검사
		oldMember = getMemberByNameAndEmail(name, email);
		
		if(oldMember != null) {
			return ResultData.from("F-8", (String) Ut.f("해당 이름(%s)과 이메일(%s)은 사용중.", name, email));
		}
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();
				
		return ResultData.from("S-1", "회원 가입 완료.", id);
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}
