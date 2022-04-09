package com.sys.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.example.demo.repository.ArticleRepository;
import com.sys.example.demo.util.Ut;
import com.sys.example.demo.vo.Article;
import com.sys.example.demo.vo.ResultData;

@Service
public class ArticleService {
//Error creating bean with name 'usrArticleController': Unsatisfied dependency expressed through field 
//'articleService'; nested exception is org.springframework.beans.factory.BeanCreationException: 
//Error creating bean with name 'articleService' defined in file 
//	[C:\work\... example\demo\service\ArticleService.class]: Instantiation of bean failed; 
//nested exception is org.springframework.beans.BeanInstantiationException: 
//Failed to instantiate [com.sys.example.demo.service.ArticleService]: Constructor threw exception; 
//nested exception is java.lang.NullPointerException: Cannot invoke "com.sys.example.demo.repository.ArticleRepository.writeArticle(String, String)" 
//	because "this.articleRepository" is null 그래서 makeTestData를 repository로 옮김	
	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public ResultData<Integer> writeArticle(int memberId, String title, String body) {		
		articleRepository.writeArticle(memberId, title, body);		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", (String) Ut.f("%d번 게시물 생성", id), "id", id);
	}

	public List<Article> getArticles() {
		
		return articleRepository.getArticles();
	}

	public Article getArticle(int id) {
		
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {		
		articleRepository.modifyArticle(id, title, body);
		
		Article article = getArticle(id);
		
		return ResultData.from("S-1", (String) Ut.f("%d번 게시물을 수정함", id), "article", article);
	}

	public ResultData actorCanModify(int actorId, Article article) {
		if(article == null) {
			return ResultData.from("F-1", "게시물이 없어요.");
		}
		
		if(article.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없어요.");
		}
		
		return ResultData.from("S-1", "게시물을 수정하세요.");
	}
	

}
