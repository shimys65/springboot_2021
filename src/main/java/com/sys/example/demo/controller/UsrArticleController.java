package com.sys.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.service.ArticleService;
import com.sys.example.demo.vo.Article;

@Controller
public class UsrArticleController {
	//인스턴스 변수
	@Autowired
	private ArticleService articleService;

	//액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = articleService.writeArticle(title, body);
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
	// 리스트 articles에 저장된 모든 article을 browser에 보여줌	
		return articleService.getArticles();
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id + "번 게시물이 없음";
		}
		return article;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	// browser에 입력되는 명령어 : /usr/article/doDelete?id=1 ==> id 1번 삭제
	public String doDelete(int id) {//String인 이유 : return 값이 문자
		Article article = articleService.getArticle(id);// id가 1이므로 1번 article을 찾자
		
		if(article == null) {
			return id + "번 게시물이 없음";
		}		
		articleService.deleteArticle(id);
	
		return id + "번 게시물을 삭제함";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	// browser에 입력되는 명령어 : /usr/article/doModify?id=1&title=ㅋㅋㅋ&body=ㅠㅠㅠ
	public String doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id + "번 게시물이 없음";
		}		
		articleService.modifyArticle(id, title, body);
	
		return id + "번 게시물이 수정됨";
	}

	//액션 메서드 끝
}
