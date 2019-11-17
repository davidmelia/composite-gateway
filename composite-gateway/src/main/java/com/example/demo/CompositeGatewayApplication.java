package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AutoConfigureWireMock
public class CompositeGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompositeGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, @Value("${wiremock.server.port}")int wiremockPort) {
	    return builder.routes()
	        .route(p -> p
	            .path("/get")
	            .filters(f -> f.addRequestHeader("Hello", "World"))
	            .uri("http://httpbin.org:80"))
	        .route(p -> p
		            .path("/api/1/addresses")
		            .filters(f -> f.rewritePath("/api/1/addresses", "/address-lookup-service/api/1/addresses"))           
		            .uri("http://localhost:9090"))
	        .route(p -> p
		            .path("/api/1/customers/*")
		            .filters(f -> f.rewritePath("/api/1/customers/", "/customer-service/api/1/customers/"))           
		            .uri("http://localhost:9090"))	        
	        .build();
	}
}
