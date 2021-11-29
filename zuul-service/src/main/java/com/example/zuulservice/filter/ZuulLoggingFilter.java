package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component  // 일반적인 Bean으로 등록하기 위해 stereotype을 Component로 지정
public class ZuulLoggingFilter extends ZuulFilter {  // ZuulFilter역할을 하기 위해 ZuulFilter를 상속받음

    /*
      구현해야 하는 추상 메소드
      1. run() : 실제 동작을 구현
      2. filterType() : 사전 필터인지 사후 필터인지 결정. "pre" or "post"
      3. filterOrder() : 여러 필터가 있는 경우 순서를 적용. 1, 2, 3...
      4. shouldFilter() : 필터로 쓸 것인지 쓰지 않을 것이지 지정. true or false
     */

    // run(), filterType(), filterOrder(), shouldFilter() 4개 메소드를 구현해야한다

    @Override
    public Object run() throws ZuulException {
        // Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);  // Lombok 을 사용하고 있으므로 @Slf4j 로 대체

        log.info("**************** printing logs: ");

        //웹 프로젝트의 request 정보를 가지고 있는 최상위 객체인 RequestContext 객체로 부터 request 정보를 get
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("**************** " + request.getRequestURI());  //request의 URI 출력

        return null;
    }

    @Override
    public String filterType() {
        // "pre"는 사전필터 "post"는 사후필터

        return "pre";  // 사용자의 요청이 들어오면 run() 메소드부터 실행된다다
    }
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;  // 필터로 사용함함
   }
}
