package com.example.test.config;

import com.example.test.web.InfoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouteConfig {
	@Bean
	public RouterFunction<ServerResponse> route(InfoHandler infoHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/orgs")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), infoHandler::listOrgs)
				.andRoute(RequestPredicates.POST("/orgs")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), infoHandler::createAndListOrgs)
				.andRoute(RequestPredicates.GET("/spaces")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), infoHandler::listSpaces)
				.andRoute(RequestPredicates.POST("/spaces")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), infoHandler::createAndListSpaces);
	}
}
