package sopt.study.testcode.seongjae.api.controller.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sopt.study.testcode.seongjae.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.seongjae.api.service.product.ProductService;
import sopt.study.testcode.seongjae.api.service.product.response.ProductResponse;
import sopt.study.testcode.seongjae.domain.product.ProductSellingStatus;
import sopt.study.testcode.seongjae.domain.product.ProductType;

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
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("아메리카노")
        .price(4000)
        .build();

    // when & then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
  @Test
  void createProductWithoutType() throws Exception {
    // given
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(null)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("아메리카노")
        .price(4000)
        .build();

    // when & then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
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
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(null)
        .name("아메리카노")
        .price(4000)
        .build();

    // when & then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
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
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name(null)
        .price(4000)
        .build();

    // when & then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("신규 상품을 등록할 때 상품 가격은 양수이다.")
  @Test
  void createProductWithZeroPrice() throws Exception {
    // given
    final ProductCreateRequest request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("아메리카노")
        .price(0)
        .build();

    // when & then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("상품 가격은 양수여야합니다."))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("판매 상품을 조회한다.")
  @Test
  void getSellingProducts() throws Exception {
    // given
    List<ProductResponse> result = List.of();
    when(productService.getSellingProducts()).thenReturn(result);

    // when & then
    mockMvc.perform(
            get("/api/v1/products/selling")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.data").isEmpty());
  }
}