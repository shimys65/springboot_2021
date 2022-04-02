package com.sys.example.demo.service;

import org.springframework.stereotype.Service;

import com.sys.example.demo.repository.MemberRepository;
import com.sys.example.demo.vo.Member;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		//로그인 아이디 중복 검사
		Member oldMember = getMemberByLoginId(loginId);	
		
		if(oldMember != null) { //이미 browser에서 입력한 loginId가 사용중
			return -1;
		}
		
		//이름+이메일 중복 검사
		oldMember = getMemberByNameAndEmail(name, email);
		
		if(oldMember != null) {
			return -2;
		}
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
				
		return memberRepository.getLastInsertId();
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
