package com.sys.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.vo.Article;

@Controller
public class UsrArticleController {
	//인스턴스 변수
	private List<Article> articles;
	private int articlesLastId;
	
	//인스턴스변수 끝
	
	//생성자
	public UsrArticleController() {
		articles = new ArrayList<>();
		articlesLastId = 0;
	}
	

	//서비스 메서드 시작
	
	
	
	//서비스 메서드 끝

	//액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articlesLastId + 1;		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articlesLastId = id;
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		
		return articles;
	}
	
	
	//액션 메서드 끝
}
