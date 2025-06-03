package sopt.study.testcode.jaeheon.spring.api.controller.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sopt.study.testcode.jaeheon.spring.api.controller.product.dto.request.ProductCreateRequest;
import sopt.study.testcode.jaeheon.spring.api.service.product.ProductService;
import sopt.study.testcode.jaeheon.spring.api.service.product.response.ProductResponse;
import sopt.study.testcode.jaeheon.spring.domain.product.ProductType;
import sopt.study.testcode.jaeheon.spring.domain.product.SellingStatus;


@WebMvcTest(controllers = ProductController.class) // 테스트하고자 하는 컨트롤러 명시
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean // 컨테이너에 Mockito로 만든 빈을 넣어주는 역할을 함
	private ProductService productService;

	@DisplayName("신규 상품을 등록함.")
	@Test
	void createProduct() throws Exception {
	    // given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingStatus(SellingStatus.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		// when // then
		mockMvc.perform(
			post("/products/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andDo(print())
			.andExpect(status().isOk());

	}

	@DisplayName("신규 상품을 등록 시 상품 타입이 없는 경우 예외 핸들링을 함.")
	@Test
	void createProductWithoutType() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.sellingStatus(SellingStatus.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		// when // then
		mockMvc.perform(
				post("/products/new")
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

	@DisplayName("신규 상품을 등록 시 상품 판매상태는 필수값임.")
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
				post("/products/new")
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

	@DisplayName("신규 상품을 등록 시 상품 이름은 필수값임.")
	@Test
	void createProductWithoutName() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingStatus(SellingStatus.SELLING)
			.price(4000)
			.build();

		// when // then
		mockMvc.perform(
				post("/products/new")
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

	@DisplayName("신규 상품을 등록 시 상품 가격은 양수임.")
	@Test
	void createProductWithoutPrice() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.name("아메리카노")
			.sellingStatus(SellingStatus.SELLING)
			.price(-4000)
			.build();

		// when // then
		mockMvc.perform(
				post("/products/new")
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

	@DisplayName("판매 상품을 조회한다")
	@Test
	void getSellingProduct() throws Exception {
		// given

		List<ProductResponse> result = List.of();
		when(productService.getSellingProducts()).thenReturn(result);


		// when // then
		mockMvc.perform(
				get("/products/selling")
					// .queryParam("name", "이름") // 쿼리 파라미터가 있는 경우.
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andExpect(jsonPath("$.data").isArray());
	}


}