package sopt.study.testcode.jaeheon.spring.api.service.order.reqeust;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {
	private List<String> productNumbers;

	@Builder
	private OrderCreateServiceRequest(List<String> productNumbers){
		this.productNumbers = productNumbers;
	}
}
