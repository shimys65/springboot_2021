package com.sys.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.example.demo.service.ArticleService;
import com.sys.example.demo.util.Ut;
import com.sys.example.demo.vo.Article;
import com.sys.example.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	//인스턴스 변수
	@Autowired
	private ArticleService articleService;

	//액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
// http://localhost:8011/usr/article/doAdd?title=제목4&body=내용4
	public ResultData doAdd(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title을 입력하세요");
		}
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body를 입력하세요");
		}
		
		ResultData writeArticleRd = articleService.writeArticle(title, body);
		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		
//		return article;// browser에 {"id":4,"title":"제목4","body":"내용4"} 출력
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}
	
// 리스트 articles에 저장된 모든 article을 browser에 보여줌	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData getArticles() {		
		List<Article> articles =  articleService.getArticles();
		
		return ResultData.from("S-1", "게시물 리스트 입니다.", articles);
	}
	
// http://localhost:8011/usr/article/getArticle?id=1
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", (String) Ut.f("%d번 게시물이 없음", id));
		}
		return ResultData.from("S-1", (String) Ut.f("%d번 게시물", id), article);
	}	
	
// http://localhost:8011/usr/article/doDelete?id=1 ==> id 1번 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody	
	public String doDelete(int id) {//String인 이유 : return 값이 문자
		Article article = articleService.getArticle(id);// id가 1이므로 1번 article을 찾자
		
		if(article == null) {
			return id + "번 게시물이 없음";
		}		
		articleService.deleteArticle(id);
	
		return id + "번 게시물을 삭제함";
	}
		
// http://localhost:8011/usr/article/doModify?id=1&title=ㅋㅋㅋ&body=ㅠㅠㅠ
	@RequestMapping("/usr/article/doModify")
	@ResponseBody	
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
