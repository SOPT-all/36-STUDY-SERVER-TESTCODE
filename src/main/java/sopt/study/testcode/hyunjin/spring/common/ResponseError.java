package sopt.study.testcode.hyunjin.spring.common;

import lombok.Getter;

@Getter
public enum ResponseError {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    OUT_OF_BUSINESS_HOURS(400, "영업시간아님!! 집으로 가셈!!"),
    INVALID_QUANTITY(400, "음료는 1잔 이상만 주문 가능함!!"),
    OUT_OF_QUANTITY(400, "재고가 부족한 상품이 있습니다.");

    private final int code;
    private final String message;

    ResponseError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}