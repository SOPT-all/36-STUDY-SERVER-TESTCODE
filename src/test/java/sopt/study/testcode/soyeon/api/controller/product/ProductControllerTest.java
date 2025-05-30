package sopt.study.testcode.soyeon.api.controller.product;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import sopt.study.testcode.soyeon.api.controller.product.request.ProductCreateRequest;
import sopt.study.testcode.soyeon.api.service.product.ProductService;
import sopt.study.testcode.soyeon.domain.product.ProductSellingStatus;
import sopt.study.testcode.soyeon.domain.product.ProductType;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                           .type(ProductType.HANDMADE)
                                           .sellingStatus(ProductSellingStatus.SELLING)
                                           .name("아메리카노")
                                           .price(4000)
                                           .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
    @Test
    void createProductWithoutType() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                           .sellingStatus(ProductSellingStatus.SELLING)
                                           .name("아메리카노")
                                           .price(4000)
                                           .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때 상품 판매 상태는 필수값이다.")
    @Test
    void createProductWithoutSellingStatus() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                           .type(ProductType.HANDMADE)
                                           .name("아메리카노")
                                           .price(4000)
                                           .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 판매 상태는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                           .sellingStatus(ProductSellingStatus.SELLING)
                                           .price(4000)
                                           .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

}