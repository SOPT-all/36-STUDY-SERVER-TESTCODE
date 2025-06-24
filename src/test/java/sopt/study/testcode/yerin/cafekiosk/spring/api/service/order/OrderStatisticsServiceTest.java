package sopt.study.testcode.yerin.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import sopt.study.testcode.yerin.cafekiosk.spring.api.service.mail.MailService;
import sopt.study.testcode.yerin.cafekiosk.spring.client.mail.MailSendClient;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.order.Order;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.order.OrderRepository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.order.OrderStatus;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.Product;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductRepository;
import sopt.study.testcode.yerin.cafekiosk.spring.domain.product.ProductType;
import sopt.study.testcode.yerin.spring.api.service.order.OrderProductRepository;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockitoBean
    private MailSendClient mailSendClient;


    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,3,5,0,0);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2023, 3,4,23,59,59), products);
        Order order2 = createPaymentCompletedOrder(now, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,5,23,59,59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,6,0,0), products);

        // stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원입니다.");
    }

    private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registerDateTime(now)
                .build();
        return orderRepository.save(order);
    }
    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}