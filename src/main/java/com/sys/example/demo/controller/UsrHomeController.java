package com.sys.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.service.MemberService;

@Controller  //이것을 입력 후 ctr+shift+o하면 import 진행됨
public class UsrHomeController {
	
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String getString() {
		return "안뇽";
	}


}



