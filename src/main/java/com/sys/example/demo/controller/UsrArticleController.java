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
		makeTestData();
	}

	//서비스 메서드 시작
	private void makeTestData() {
		for(int i=1; i<=10; i++) {			
			String title = "제목 " + i;
			String body = "내용 " + i;
			
			writeArticle(title, body);
		}
	}	
	
	private Article writeArticle(String title, String body) {
		int id = articlesLastId +1 ;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articlesLastId = id;
		
		return article;		
	}

	// browser에서 입력한 id 번호의 article 찾기
	private Article getArticle(int id) {// Article 이므로 반드시 return이 있어야 함
		for(Article article : articles) {
			if(article.getId() == id) {
				return article; // 찾은 article을 doDelete()에 반환
			}
		}
		return null;
	}	

// void인 이유: Article로 하면 반드시 return이 있어야 하므로 browser에서 입력한 id 번호로 article 찾아 제거
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);		
	}

	//서비스 메서드 끝

	//액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
	// 리스트 articles에 저장된 모든 article을 browser에 보여줌	
		return articles;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	// browser에 입력되는 명령어 : /usr/article/doDelete?id=1 ==> id 1번 삭제
	public String doDelete(int id) {//String인 이유 : return 값이 문자
		Article article = getArticle(id);// id가 1이므로 1번 article을 찾자
		
		if(article == null) {
			return id + "번 게시물이 없음";
		}		
		deleteArticle(id);
	
		return id + "번 게시물을 삭제함";
	}
	
	//액션 메서드 끝
}
