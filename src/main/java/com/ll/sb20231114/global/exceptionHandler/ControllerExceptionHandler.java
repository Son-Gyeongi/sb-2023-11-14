package com.ll.sb20231114.global.exceptionHandler;

import com.ll.sb20231114.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 124강, ControllerExceptionHandler 를 믿고,
 * 오직 성공 시나리오로만 코딩을 한다. 그러면 코드가 간결해 집니다.
 *
 * ControllerExceptionHandler 를 도입하여
 *
 * @Controller에서 발생한 RuntimeException 에 대한
 * 처리로 rq.historyBack 이 실행되도록 연결
 */
@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final Rq rq;

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        System.out.println("ControllerExceptionHandler.handleException");
        return rq.historyBack(ex);
    }
}
