package com.ll.sb20231114;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
        // 이 함수의 리턴값을 그대로 브라우저에 전송하라는 의미, 값을 문자로 응답해준다.
    String showMain() {
        System.out.println("안녕하세요!!!"); // 이 메시지는 콘솔에 출력됨, 클라이언트에게 전송되지 않습니다., 서버에서 출력
        return "안녕하세요.";
    }

    @GetMapping("/about")
    @ResponseBody
    String showAbout() {
        return "개발자 커뮤니티";
    }

    @GetMapping("/calc") // /calc?a=10&b=20
    @ResponseBody
    String showCalc(int a, int b) { // 매개변수에 아무값이 안들어오면 null을 넣는다. 기본형이라서 불가능
        return "계산 결과 : %d".formatted(a + b);
    }

    @GetMapping("/calc2")
    @ResponseBody
    String showCalc2(Integer a, Integer b) { // 매개변수에 아무값이 안들어오면 null을 넣는다. 참조형이라서 가능
        return "a : " + a + ", b : " + b;
    }

    @GetMapping("/calc3") // /calc?a=10&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc3( // 값이 안 들어오면 0이 기본값으로 들어온다.
            @RequestParam(defaultValue = "0") int a,
            @RequestParam(defaultValue = "0") int b
    ) {
        return "계산 결과 : %d".formatted(a + b); // %d 10진수 정수
    }

    @GetMapping("/calc4") // /calc?a=10.5&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc4( // 값이 안 들어오면 0이 기본값으로 들어온다.
                      @RequestParam(defaultValue = "0") double a,
                      @RequestParam(defaultValue = "0") double b
    ) {
        return "계산 결과 : %f".formatted(a + b); // %f 10진수 정수
    }

    @GetMapping("/calc5") // /calc?a=안녕&b=하세요, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc5( // 값이 안 들어오면 0이 기본값으로 들어온다.
                      @RequestParam(defaultValue = "-") String a,
                      @RequestParam(defaultValue = "-") String b
    ) {
        return "계산 결과 : %s".formatted(a + b); // %s 문자열
    }
}
