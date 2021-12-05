package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() { super(Config.class);}

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {

        // 람다식을 통한 구현
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter: request id -> {}" , request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
                }
            }));
        };

        // OrderedGatewayFilter 을 통한 구현
        // GatewayFilter 는 interface 라 인스턴스를 직접 생성할 수 없고, OrderedGatewayFilter 를 통해 생성한다.
//        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {  // 첫번째 매개변수인 filter 함수를 함께 구현하기 위해 람다식 사용
//
//            // filter 의 내용 정의
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("Logging PRE Filter: request id -> {}" , request.getId());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if (config.isPostLogger()) {
//                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
//                }
//            }));
//
//        // 두번째 매개변수로 Filter 의 우선순위를 적용
//        }, Ordered.LOWEST_PRECEDENCE);  // HIGHEST_PRECEDENCE 를 적용하면 Global Filter 보다 앞서 적용된다.
//
//       return filter;

    }

}
