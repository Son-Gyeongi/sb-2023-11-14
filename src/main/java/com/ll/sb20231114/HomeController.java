package com.ll.sb20231114;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller // 개발자가 스프링부트에게 HomeController 클래스가 컨트롤러라는 걸 알려준다.
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
    String showCalc(int a, int b) {
        // 매개변수에 아무값이 안들어오면 null을 넣는다. int가 기본형이라서 불가능
        return "계산 결과 : %d".formatted(a + b);
    }

    @GetMapping("/calc2")
    @ResponseBody
    String showCalc2(Integer a, Integer b) {
        // 매개변수에 아무값이 안들어오면 null을 넣는다. Integer가 참조형이라서 가능
        return "a : " + a + ", b : " + b;
    }

    @GetMapping("/calc3") // /calc?a=10&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc3( // 값이 안 들어오면 0이 기본값으로 들어온다.
                      // int는 정수 허용
                      @RequestParam(defaultValue = "0") int a,
                      @RequestParam(defaultValue = "0") int b
    ) {
        return "계산 결과 : %d".formatted(a + b); // %d 10진수 정수
    }

    @GetMapping("/calc4") // /calc?a=10.5&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc4( // 값이 안 들어오면 0이 기본값으로 들어온다.
                      // double은 정수, 실수 허용
                      @RequestParam(defaultValue = "0") double a,
                      @RequestParam(defaultValue = "0") double b
    ) {
        return "계산 결과 : %f".formatted(a + b); // %f 실수
    }

    @GetMapping("/calc5") // /calc?a=안녕&b=하세요, url은 무조건 다 문자열이다.
    @ResponseBody
    String showCalc5( // 값이 안 들어오면 "-"이 기본값으로 들어온다.
                      // String은 문자열, 정수, 실수 다 허용
                      @RequestParam(defaultValue = "-") String a,
                      @RequestParam(defaultValue = "-") String b
    ) {
        return "계산 결과 : %s".formatted(a + b); // %s 문자열
    }

    @GetMapping("/calc6") // /calc?a=10&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    int showCalc6(
            // int는 자바에서만 이해할 수 있는 개념, 브라우저에서 이해할 수 없다. 만국공통어는 String 문자열이다.
            int a, int b
    ) {
        return a + b;
    }

    @GetMapping("/calc7") // /calc?a=10&b=20, url은 무조건 다 문자열이다.
    @ResponseBody
    boolean showCalc7(
            // boolean은 자바에서만 이해할 수 있는 개념, 브라우저에서 이해할 수 없다. 만국공통어는 String 문자열이다.
            int a, int b
    ) {
        return a > b;
    }

    @GetMapping("/calc8")
    @ResponseBody
    Person showCalc8(String name, int age) {
        // 값이 없으면 기본형인 int로 인해서 오류 난다. String은 값이 없으면 null이 나온다.
        return new Person(name, age); // 브라우저가 이해할 수 있게 번역된다.
    }

    @GetMapping("/calc9")
    @ResponseBody
    Person2 showCalc9(String name, int age) {
        return new Person2(name, age); // 브라우저가 이해할 수 있게 번역된다.
    }

    @GetMapping("/calc10")
    @ResponseBody
    Map<String, Object> showCalc10(
            String name, int age
    ) {
        // 한번에 데이터가 2개 있는 Map을 생성
        Map<String, Object> psersonMap = Map.of(
                // 큰 개념 Object / int, String을 다 품는다.
                "name", name,
                "age", age
        );

        // 객체랑 Map이랑 비슷하게 전송된다. json은 객체랑 Map을 구분할 방법이 없다.
        return psersonMap;
    }

    @GetMapping("/calc11")
    @ResponseBody
    List<Integer> showCalc11() {
        // 숫자 3개가 화면에 나온다.
        List<Integer> nums = new ArrayList<>() {{
            // 익명 클래스 안에 생성자
            add(10);
            add(-510);
            add(10010);
        }};

        return nums;
    }

    @GetMapping("/calc12")
    @ResponseBody
    int[] showCalc12() {
        int[] nums = new int[]{10, -510, 10010};

        return nums; // List<Integer>와 int[] 배열을 브라우저에 번역해서 보냈을 때 구분을 할 수 없다.
    }

    @GetMapping("/calc13")
    @ResponseBody
    List<Person2> showCalc13(
            String name, int age
    ) {
        List<Person2> persons = new ArrayList<>() {{
            // 익명클래스로 이름이 없는 생성자, 객체가 만들어지고나서 3개가 추가된다
            // 객체 리모콘 3개로 채워진 리스트를 만들 수 있다.
            add(new Person2(name, age));
            add(new Person2(name + "!", age + 1));
            add(new Person2(name + "!!", age + 2));
        }};

        // 객체랑 Map이랑 비슷하게 전송된다. json은 객체랑 Map을 구분할 방법이 없다.
        return persons;
    }

    @GetMapping("/calc14")
    @ResponseBody
    String showCalc14() {
        String html = "";

        // \(역슬래시) 이스케이프 문자
        html += "<div>";
        html += "<input type=\"text\" placeholder=\"내용\">";
        html += "</div>";

        return html;
    }

    // 긴 문자열은 String 보다는 StringBuilder 또는 TextBlock 를 이용하기, 문자열의 불변성 때문
    @GetMapping("/calc15")
    @ResponseBody
    String showCalc15() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div>");
        sb.append("<input type=\"text\" placeholder=\"내용\">");
        sb.append("</div>");

        return sb.toString();
    }

    @GetMapping("/calc16")
    @ResponseBody
    String showCalc16() {
        String html = "<div><input type=\"text\" placeholder=\"내용\"></div>";

        return html;
    }

    @GetMapping("/calc17")
    @ResponseBody
    String showCalc17() {
        // html 잘 보이게 하기 위해서, 소스 코드 예쁘게 보일 수 있다.
        // 자바에서 """ (쌍따옴표 세 개)는 여러 줄의 문자열을 표현하는 기능
        String html = """
                <div>
                    <input type = "text" placeholder="내용">
                </div>
                """;

        return html;
    }

    @GetMapping("/calc18")
    @ResponseBody
    String showCalc18() {
        // placeholder는 input 상자 안에 주석같은 역할, value는 값이 채워진 상태로 나온다.
        String html = """
                <div>
                    <input type = "text" placeholder="내용" value="반가워요.">
                </div>
                """;

        return html;
    }

    @GetMapping("/calc19")
    @ResponseBody
    String showCalc19(
            // 값이 안 들어올 경우 null이 주어지는데, defaultValue를 사용함으로써 값이 없을 때 빈칸이 들어온다.
            @RequestParam(defaultValue = "") String subject,
            @RequestParam(defaultValue = "") String content
    ) {
        String html = """
                <div>
                    <input type = "text" placeholder="제목" value="%s">
                </div>
                <div>
                    <input type = "text" placeholder="내용" value="%s">
                </div>
                """.formatted(subject, content);

        return html;
    }

    @GetMapping("/calc20")
    String showCalc20() {
        // calc20.html 가기위해서 타임리프를 써야한다.
        // "src/main/resources/templates/" + calc20 +".html"
        return "calc20";
    }

    @GetMapping("/calc21") // /calc21?v1=11&v2=22
    String showCalc21(Model model) {
        model.addAttribute("v1", "안녕");
        model.addAttribute("v2", "반가워");
        return "calc21";
    }
    // 컨트롤러에서 뷰를 다루면 MVC 정책위반입니다.
    // 타임리프를 통해서 뷰를 호출해주세요. 뷰 데이터는 Model 객체에 담아주세요.

    // 매번 같은 HomeController가 나의 요청을 처리해준다.
    // 컨트롤러 객체(bean)은 딱 1개만 생성되어 사용된다. / 싱글톤
    int num = 0; // 인스턴스 변수, HomeController가 죽기 전까지 기억된다.
    @GetMapping("/calc22")
    @ResponseBody
    int showCalc22() {
//        int num = 0; // 지역변수, showCalc22() 함수가 실행이 되고 함수가 끝나면 사라진다.

        num++;

        return num;
    }

    @AllArgsConstructor
    class Person {
        // 클래스 필드에 접근하는 방법 : public으로 바꿔주기
        public String name;
        public int age;
    }

    @AllArgsConstructor
    class Person2 {
        // 클래스 필드에 접근하는 방법 : 필드가 private일 경우 @Getter를 사용
        @Getter
        private String name;
        @Getter
        private int age;
    }
}
