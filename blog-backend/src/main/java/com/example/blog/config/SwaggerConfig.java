package com.example.blog.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.blog.api"))
				.paths(PathSelectors.ant("/api/**/*"))
				.build()
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Arrays.asList(securitySchemes()));
	}

	@Bean
	public SecurityConfiguration securityConfiguration() {
		return SecurityConfigurationBuilder
			.builder()
			.build();
	}
	
	private SecurityScheme securitySchemes() {
		List<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
//		scopes.add(new AuthorizationScope("read", "read all"));
//		scopes.add(new AuthorizationScope("write", "write all"));
		
		List<GrantType> grantTypes = new ArrayList<GrantType>();
		grantTypes.add(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
		
		return new OAuth("oauth2", scopes, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(securityReferences()).build();
	}

	private List<SecurityReference> securityReferences() {
		AuthorizationScope[] scopes = {
//			new AuthorizationScope("read", "read all")
//			, new AuthorizationScope("write", "write all")
//			, new AuthorizationScope("trust", "trust all")
		};
		return Collections.singletonList(new SecurityReference("oauth2", scopes));
	}
}
