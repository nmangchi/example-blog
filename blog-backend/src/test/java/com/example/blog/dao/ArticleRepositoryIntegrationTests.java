package com.example.blog.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.blog.config.JpaConfig;
import com.example.blog.model.Article;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @Filter(
		type = FilterType.ASSIGNABLE_TYPE
		, classes = { AuditorAware.class, JpaConfig.class }))
@IfProfileValue(name="test-groups", value="integration")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ArticleRepositoryIntegrationTests {

	@Autowired
	private ArticleRepository articleRepository;
	
	public static final List<Article> dummyArticles = Arrays.asList(
		Article.of("원망하지 말라", "원망하기 시작하면 끝이 없다"),
		Article.of("자책하지 말라", "후회와 반성은 지독하게 하되 한번으로 족하다"),
		Article.of("상황을 인정하라", "과거는 소리없이 흘러갔을 뿐이다 현실을 냉정하게 인정할 줄 아는 것이 중요하다"),
		Article.of("궁상을 부리지 마라", "죽겠다는 소리는 입 밖에도 내지 마라"),
		Article.of("조급해 하지 마라", "조급해서 얻을 수 있는 것은 실수뿐이다"),
		Article.of("자신을 바로 알라", "내가 어쩌다가 여기까지 왔나를 냉정하게 생각해볼 필요가 있다"),
		Article.of("희망을 품어라", "희망은 생명을 살리는 기적을 낳는다"),
		Article.of("용기를 내라", "아무것도 없었던 매 처음 때를 생각하고 그때의 용기를 다시 내자"),
		Article.of("책을 읽어라", "책을 읽되 우선은 의용 관리를 위해 실패담보다는 성공사례를 많이 읽어라. 책 속에 길이 있다"),
		Article.of("성공한 모습을 상상하고 행동하라", "사람은 누구나 자기가 되고 싶은 모습이 있게 되고 또 자기도 모르는 사이에 그렇게 변해간다")
	);
	
	@Test
	@Transactional
	@Rollback(false)
	@WithMockUser(value="1", authorities= {"USER"})
	@Repeat(10)
	public void A_save_article() {
		Random rand = new Random();
		int idx = rand.nextInt(dummyArticles.size());
		
		String title = dummyArticles.get(idx).getTitle();
		String contents = dummyArticles.get(idx).getContents();
		
		Article article = articleRepository.save(new Article(title, contents));
		log.debug("### article : {}", article);
		
		Optional<Article> newArticle = articleRepository.findById(article.getSeq());
		log.debug("### newArticle : {}", newArticle);
		
		assertThat(newArticle, is(Optional.of(article)));
	}
	
	@Test
	public void B_articles() {
		Random rand = new Random();
		String keyword = dummyArticles.get(rand.nextInt(dummyArticles.size())).getTitle();
		
		int page = 0;
		int size = 5;
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "wrote"));
		log.debug("### keyword : {}, {}", keyword, pageRequest);
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withStringMatcher(StringMatcher.CONTAINING);
		Page<Article> articles = articleRepository.findAll(Example.of(new Article(keyword, keyword), exampleMatcher), pageRequest);
		log.debug("### articles : {}, {}", articles.getTotalElements(), articles.getContent());
	}
	
	@Test
	@Transactional
	@WithMockUser(value="2", authorities= {"USER"})
	public void C_modify_article() {
		List<Article> articles = articleRepository.findAll();
		
		Random rand = new Random();
		int idx = rand.nextInt(dummyArticles.size());
		String title2 = dummyArticles.get(idx).getTitle();
		String contents2 = dummyArticles.get(idx).getContents();
		
		Optional<Article> oldArticle = articleRepository.findById(articles.get(0).getSeq());
		Article article = oldArticle.get();
		log.debug("### oldArticle : {}", article);
		
		article.setTitle(title2);
		article.setContents(contents2);
		article = articleRepository.save(article);
		
		log.debug("### newArticle : {}", article);
	}
}
