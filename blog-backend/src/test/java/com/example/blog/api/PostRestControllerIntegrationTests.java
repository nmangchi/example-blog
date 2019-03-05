package com.example.blog.api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.startsWith;

import java.util.Random;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.blog.Application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IfProfileValue(name="test-groups", value="integration")
@Slf4j
public class PostRestControllerIntegrationTests {

	@LocalServerPort
	private int port;
	
	@Before
	public void before() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	@Repeat(10)
	public void post_save() {
		String[] titles = {"맥심", "모카골드 마일드", "프라빈 커피"};

		Random rand = new Random();
		int num = rand.nextInt(1000) + 1;
		int idx = rand.nextInt(titles.length);
		
		String title = titles[idx] + " " + num;
		String contents = titles[idx] + " 포스트 내용 " + num;
		
		given()
			.contentType(ContentType.JSON)
			.body("{ \"title\" : \"" + title + "\", \"contents\" : \"" + contents + "\"}")
		.when()
			.post("/api/posts")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("title", equalTo(title),
					"contents", equalTo(contents));
	}
	
	@Test
	public void post_list() {
		when()
			.get("/api/posts")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("title", hasItems("테스트 타이틀"),
					"contents", hasItems("포스트 내용"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void post_list_with_page() {
		given()
			.param("page", "2")
			.param("size", "5")
		.when()
			.get("/api/posts")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("content.title", hasItems(startsWith("모카골드")),
					"content.contents", hasItems(containsString("포스트 내용")));
	}
	
	@Test
	public void post_list_all_response() {
		String posts = when().get("/api/posts").asString();
		log.debug("### posts : {}", posts);
	}
	
	@Test
	public void post_list_response() {	
		String posts = given()
					.param("page", "0")
					.param("size", "5")
//					.body("{ \"title\" : \"맥심\", \"contents\" : \"프라빈\" }")
					.body("{ \"title\" : \"맥심\" }")
					.contentType(ContentType.JSON)
				.when().get("/api/posts").asString();
		log.debug("### posts with param : {}", posts);
	}
}
