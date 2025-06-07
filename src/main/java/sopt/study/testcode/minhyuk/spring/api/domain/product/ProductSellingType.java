package sopt.study.testcode.minhyuk.spring.api.domain.product;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellingType {
	SELLING("판매 중"),
	HOLD("판매 보류"),
	STOP_SELLING("판매중지");

	private  final String text;

	public static List<ProductSellingType> forDisplay() {
		return List.of(SELLING,HOLD);
	}
}
