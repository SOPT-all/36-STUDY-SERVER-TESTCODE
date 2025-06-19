package sopt.study.testcode.jaeheon.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import sopt.study.testcode.jaeheon.spring.api.controller.order.OrderController;
import sopt.study.testcode.jaeheon.spring.api.controller.product.ProductController;
import sopt.study.testcode.jaeheon.spring.api.service.order.OrderService;
import sopt.study.testcode.jaeheon.spring.api.service.product.ProductService;

@WebMvcTest(controllers = {
	OrderController.class,
	ProductController.class
})
public abstract class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;

	@MockitoBean
	protected OrderService orderService;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockitoBean // 컨테이너에 Mockito로 만든 빈을 넣어주는 역할을 함
	protected ProductService productService;

}
