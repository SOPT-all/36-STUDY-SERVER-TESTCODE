package sopt.study.testcode.hyunjin.spring.common;

import lombok.Getter;

@Getter
public enum ResponseError {

    OUT_OF_BUSINESS_HOURS("영업시간아님!! 집으로 가셈!!"),
    INVALID_QUANTITY("음료는 1잔 이상만 주문 가능함!!"),
    OUT_OF_QUANTITY("재고가 부족한 상품이 있습니다.");

    private final String message;

    ResponseError(String message) {
        this.message = message;
    }
}