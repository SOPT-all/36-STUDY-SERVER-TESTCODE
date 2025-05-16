package sopt.study.testcode.minhyuk.spring.api.controller.order.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

	private List<String> productNumber;

	@Builder
	private OrderCreateRequest(List<String> productNumber){
		this.productNumber = productNumber;
	}
}
