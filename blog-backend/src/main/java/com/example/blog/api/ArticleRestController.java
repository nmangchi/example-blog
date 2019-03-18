package com.example.blog.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.AppConsts;
import com.example.blog.model.Article;
import com.example.blog.model.Search;
import com.example.blog.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

	@Autowired
	private ArticleService articleService;

	@PostMapping("")
	public ResponseEntity<Article> postArticles(@RequestBody Article article) {
		article = articleService.save(article);
		return ResponseEntity.ok(article);
	}
	
	@GetMapping("")
	public ResponseEntity<Page<Article>> getArticles(
			@RequestParam(required=false, defaultValue = AppConsts.Page.INIT) int page,
			@RequestParam(required=false, defaultValue = AppConsts.Page.ROW_SIZE) int size,
			Search search) {
		Sort sort = Sort.by(Direction.DESC, "wrote");
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Article> articles = articleService.findAll(search.to(Article.class), pageRequest);
		return ResponseEntity.ok(articles);
	}

	@PutMapping("/{seq}")
	public ResponseEntity<?> putArticles(@PathVariable Integer seq, @RequestBody Article article) {
		article.setSeq(seq);
		Optional<Article> newArticle = articleService.modify(article);
		if (newArticle.isPresent()) {
			return ResponseEntity.ok(newArticle);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> deleteArticles(@PathVariable Integer seq) {
		articleService.deleteBySeq(seq);
		return (ResponseEntity<?>) ResponseEntity.ok();
	}
	
	@GetMapping("/{seq}")
	public ResponseEntity<?> getArticleBySeq(@PathVariable Integer seq) {
		Optional<Article> article = articleService.findBySeq(seq);
		return ResponseEntity.ok(article);
	}
}
