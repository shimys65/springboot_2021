package com.sys.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sys.example.demo.vo.Article;

@Component
public class ArticleRepository {
	
	private List<Article> articles;
	private int articlesLastId;
	
	public ArticleRepository() {
		articles = new ArrayList<>();
		articlesLastId = 0;
	}	
	
	public void makeTestData() {
		for(int i=1; i<=10; i++) {			
			String title = "제목 " + i;
			String body = "내용 " + i;
			
			writeArticle(title, body);
		}
	}
	
	// makeTestData()와 doAdd에서 겹치는 부분을 writeArticle()로 통합.
	public Article writeArticle(String title, String body) {
		int id = articlesLastId +1 ;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articlesLastId = id;
		
		return article;		
	}

	// browser에서 입력한 id 번호의 article 찾기
	public Article getArticle(int id) {// Article 이므로 반드시 return이 있어야 함
		for(Article article : articles) {
			if(article.getId() == id) {
				return article; // 찾은 article을 doDelete()에 반환
			}
		}
		return null;
	}	

// void인 이유: Article로 하면 반드시 return이 있어야 하므로 browser에서 입력한 id 번호로 article 찾아 제거
	public void deleteArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);		
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
	}

	public List<Article> getArticles() {
		
		return articles;
	}

}
