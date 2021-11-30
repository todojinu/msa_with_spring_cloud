package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //Spring Boot가 처음 BootStrap 위에서 작동을 할때 @Configuration 이 붙은 Bean 들을 모아서 메모리에 먼저 등록함
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                //자바코드로 routes 정보 작성시 Lamda 식의 사용이 가능하다.
                //Lamda식은 클래스나 인터페이스의 선언 없이 사용할 수 있는 익명클래스 라고 생각하면 된다.
                //즉석으로 인스턴스를 생성해 사용하고 바로 소멸됨

                //라우트 정보 추가(first-service)
                .route(r -> r.path("/first-service/**")  // /first-service/ 로 시작하는 요청정보가 들어오면
                            // filter 추가
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header")      // request 필터
                                           .addResponseHeader("first-response", "first-response-header"))  // respondse 필터
                            .uri("http://localhost:8081"))     // http://localhost:8081/first-service/... 로 포워딩
                //라우트 정보 추가(second-service)
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
