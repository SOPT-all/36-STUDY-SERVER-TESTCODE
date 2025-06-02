package sopt.study.testcode.kyongmin.spring.domain.api.contoller.product;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import sopt.study.testcode.kyongmin.spring.domain.api.contoller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.ProductService;
import sopt.study.testcode.kyongmin.spring.domain.api.service.product.response.ProductResponse;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductSellingStatus;
import sopt.study.testcode.kyongmin.spring.domain.product.ProductType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * 기존 mokbean은 지원 중단되어 사용 불가
	 * mock을 사용해도 됨
	 * 스프링 컨테이너에 mock으로 만든 객체를 등록해주는 역할을 수행
	 * ProductService가 테스트 과정에서 직접 호출되지는 않지만
	 * controller가 ProductService에 의존하므로 mockitoBean으로 빈 등록
	 */
	@MockitoBean
	private ProductService productService;

	@Test
	@DisplayName("신규 상품을 등록한다.")
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
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
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
								.contentType(MediaType.APPLICATION_JSON)
				)
				// 요청에 대한 로그 출력
				.andDo(print())
				// 응답에 대한 검증
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	@DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수값이다.")
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
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	@DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
	void createProductWithoutName() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
				.type(ProductType.HANDMADE)
				.sellingStatus(ProductSellingStatus.SELLING)
				.price(4000)
				.build();

		// when // then
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

	@Test
	@DisplayName("신규 상품을 등록할 때 상품 가격은 양수이다.")
	void createProductWithZeroPrice() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
				.type(ProductType.HANDMADE)
				.sellingStatus(ProductSellingStatus.SELLING)
				.name("아메리카노")
				.price(0)
				.build();

		// when // then
		mockMvc.perform(
						post("/api/v1/products/new")
								.content(objectMapper.writeValueAsString(request))
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	@DisplayName("판매 상품을 조회한다.")
	void getSellingProducts() throws Exception {
		// given
		List<ProductResponse> result = List.of();

		when(productService.getSellingProducts()).thenReturn(result);

		// when // then
		mockMvc.perform(
						get("/api/v1/products/selling")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200"))
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data").isArray());
	}
}