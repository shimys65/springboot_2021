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
	@Autowired
	private ArticleService articleService;

	//액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
// http://localhost:8011/usr/article/doAdd?title=제목4&body=내용4
	public ResultData<Article> doAdd(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title을 입력하세요");
		}
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body를 입력하세요");
		}
// writeArticleRd 내용 : writeArticleRd의 resultCode로 S-1, mag로 4번 게시물 생성, data1으로 4
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body);
		int id = writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		
//		return article;// browser에 {"id":4,"title":"제목4","body":"내용4"} 출력		
		return ResultData.newData(writeArticleRd, article);
	}
	
// 리스트 articles에 저장된 모든 article을 browser에 보여줌	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {		
		List<Article> articles =  articleService.getArticles();
		
		return ResultData.from("S-1", "게시물 리스트 입니다.", articles);
	}
	
// http://localhost:8011/usr/article/getArticle?id=1
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", (String) Ut.f("%d번 게시물이 없음", id));
		}
		return ResultData.from("S-1", (String) Ut.f("%d번 게시물", id), article);
	}	
	
// http://localhost:8011/usr/article/doDelete?id=1 ==> id 1번 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody	
	public ResultData<Integer> doDelete(int id) {//String인 이유 : return 값이 문자
		Article article = articleService.getArticle(id);// id가 1이므로 1번 article을 찾자
		
		if(article == null) {
			return ResultData.from("F-1", (String) Ut.f("%d번 게시물이 없음", id));
		}		
		articleService.deleteArticle(id);
	
		return ResultData.from("F-1", (String) Ut.f("%d번 게시물을 삭제함", id), id);
	}
		
// http://localhost:8011/usr/article/doModify?id=1&title=ㅋㅋㅋ&body=ㅠㅠㅠ
	@RequestMapping("/usr/article/doModify")
	@ResponseBody	
	public ResultData<Integer> doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", (String) Ut.f("%d번 게시물이 없음", id));
		}		
		articleService.modifyArticle(id, title, body);
	
		return ResultData.from("F-1", (String) Ut.f("%d번 게시물을 수정함", id), id);
	}
	//액션 메서드 끝
}
