package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController  // 화면 없이 RestAPI 형태로 서비스하기 위해 @RestController 사용
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {

    // application.yml 파일에 등록된 환경설정 정보를 가져오기 위해 Environment 객체를 사용
    Environment env;

    // env 변수에 바로 @Autowired 를 적용하지 않고 생성자를 통해 의존성을 주입함
    // Spring Context 에 의해 Bean 이 생성될 때 Environment 객체를 같이 얻어온다.
    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

    // 전통적인 Spring MVC 에서 HttpServletRequest 와 HttpServletResponse 는 모든 Controller 클래스 안에서 매개변수로 선언만 함으로써 사용 가능
    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());  // request 에서 port 번호를 얻거나

        return String.format("Hi, there. This is a message from First Service on PORT %s"
                , env.getProperty("local.server.port"));  // 환경변수로 부터 실제 할당된 port 번호를 얻을 수 있음
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service.";
    }

}
