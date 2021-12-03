package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;  // WebFlux 를 지원해주는 Spring5의 reactive 를 import 해야함
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component  //일반적인 Bean으로 등록하기 위해 @Component 어노테이션 사용
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    // 기본 생성자 작성
    public CustomFilter() {
        super(Config.class);  // super 클래스 생성자 호출
    }

    // Configuration 정보가 있다면 클래스 매개변수를 CustomFilter.Config로 지정하고 이너클래로 Config를 작성해준다.
    public static class Config {
        // Configuration 정보를 입력
    }

    @Override
    public GatewayFilter apply(Config config) {  // apply 메소드 구현

        // Custom Pre Filter
        return (exchange, chain) -> {  // 람다식의 매개변수로 exchange와 chain을 받는다
            // exchange 로 부터 서버객체의 Request 와 Response 를 얻는다
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request id -> {}", request.getId());  // request.getId() 가 {} 안으로 들어감

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {  // 비동기방식 서버에서 단일값 전달시 Mono 사용. WebFlux 기능
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());
            }));

        };
    }

}
