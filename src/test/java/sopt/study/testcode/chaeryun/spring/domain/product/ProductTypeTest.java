package sopt.study.testcode.chaeryun.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType1(){
        // Given
        ProductType givenType = ProductType.HADMADE;

        // When
        boolean result = ProductType.containsStockType(givenType);

        // Then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2(){
        // Given
        ProductType givenType = ProductType.BAKERY;

        // When
        boolean result = ProductType.containsStockType(givenType);

        // Then
        assertThat(result).isTrue();
    }
}