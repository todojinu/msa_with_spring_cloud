package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component  //일반적인 Bean 으로 등록하기 위해 @Component 어노테이션 사용
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    // 기본 생성자 작성
    public GlobalFilter() { super(Config.class); }

    // 필요시 Configuration 정보 작성하고 클래스의 매개변수로 전달
    @Data
    public static class Config {
        private String BaseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {  // implement 메소드

        // Global Pre Filter
        return (exchange, chain) -> {  // 람다식의 매개변수로 exchange, chain 을 받음
            // exchange 로 부터 Request, Response 객체를 get
            // Spring5 부터 지원하는 WebFlux 의 기능을 통해 비동기로 통신한다.
                // -> HttpServletRequest, HttpServletResponse 대신 reactive의 ServerHttpRequest, ServerHttpResponse 사용
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global Filter Start: request id -> {}", request.getId());
            }

            // Global Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
                }
            }));
        };
    }
}
