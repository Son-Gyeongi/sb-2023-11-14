package com.ll.sb20231114.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 보고서 양식 - 다른데서도 쓴다.
// 어떤 클래스 미완성인게 좋다.
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class RsData<T> { // ResultData라는 뜻
    private final String resultCode;
    private final String msg;
    private T data;

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    public boolean isFail() {
        return isSuccess() == false;
    }
}
