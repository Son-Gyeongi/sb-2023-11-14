package com.ll.sb20231114.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 보고서 양식 - 다른데서도 쓴다.
// 어떤 클래스 미완성인게 좋다.
// 서비스가 RsData〈?〉 형태로 응답을 하면 호출자가 다양한 정보를 한번에 얻을 수 있어서 좋습니다.
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class RsData<T> { // ResultData라는 뜻
    private final String resultCode;
    private final String msg;
    private T data;

    public boolean isSuccess() {
        return resultCode.startsWith("S-"); // resultCode가 S로 시작하면 성공이다.
    }

    public boolean isFail() {
        return isSuccess() == false;
    }
}
