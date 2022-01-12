package org.ggyool.onlinelecturerestfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(name);
    }

    // 다국어 처리 예제
    // 1. resources directory에 준비되어 있는 message 파일을 만들어놓음
    // 2. main 클래스에 LocaleResolver 등록해놓음
    // 3. 클라이언트에서 요청할 때 Accept-Language 를 전달해주고 (en, fr) 지원하는 언어이면 준비된 메시지를 준다.
    // 해당 헤더가 없거나 지원하지 않는 헤더값이면 빈등록시 default로 설정해놓은 한국어로 반환.
    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {

        return messageSource.getMessage("greeting.message", null, locale);
    }
}
