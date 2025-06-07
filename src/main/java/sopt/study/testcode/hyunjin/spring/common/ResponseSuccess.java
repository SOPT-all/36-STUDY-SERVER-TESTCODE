package sopt.study.testcode.hyunjin.spring.common;

import lombok.Getter;

@Getter
public enum ResponseSuccess {
    OK(200, "요청이 성공했습니다."),
    CREATED(201, "성공적으로 생성했습니다.");

    private final int code;
    private final String message;

    ResponseSuccess(int code, String message) {
        this.code = code;
        this.message = message;
    }

}