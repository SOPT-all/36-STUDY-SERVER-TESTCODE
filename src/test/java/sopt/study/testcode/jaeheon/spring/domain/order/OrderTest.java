package sopt.study.testcode.jaeheon.spring.domain.order;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sopt.study.testcode.jaeheon.spring.domain.product.Product;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산함")
    @Test
    void calculateTotalPrice(){
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 1000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(2000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT 임")
    @Test
    void init(){
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 1000)
        );

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }


    @DisplayName("주문 생성 시 등록 시간을 기록함.")
    @Test
    void registeredDateTime(){
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 1000)
        );

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }



    private Product createProduct(String productNumber, int price){
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .name("메뉴명")
                .build();
    }

}