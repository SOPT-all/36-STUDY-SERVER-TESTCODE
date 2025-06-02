package sopt.study.testcode.kyongmin.spring.domain.api.contoller.order;

import static org.junit.jupiter.api.Assertions.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import sopt.study.testcode.kyongmin.spring.domain.api.contoller.order.request.OrderCreateRequest;
import sopt.study.testcode.kyongmin.spring.domain.api.service.order.OrderService;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
	// 48:55부터 이거 작성하고 듣기
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private OrderService orderService;

	@Test
	@DisplayName("신규 주문을 등록한다.")
	void createOrder() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
				.productNumbers(List.of("001"))
				.build();

		// when // then
		mockMvc.perform(
						post("/api/v1/orders/new")
								.content(objectMapper.writeValueAsString(request))
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200"))
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.message").value("OK"));
	}

	@Test
	@DisplayName("신규 주문을 등록할 때 상품번호는 1개 이상이어야 한다.")
	void createOrderWithEmptyProductNumbers() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
				.productNumbers(List.of())
				.build();

		// when // then
		mockMvc.perform(
						post("/api/v1/orders/new")
								.content(objectMapper.writeValueAsString(request))
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
}