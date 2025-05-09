package sopt.study.testcode.unit.order;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt.study.testcode.unit.beverage.Beverage;

@Getter
@RequiredArgsConstructor
public class Order {

	private final LocalDateTime localDateTime;
	private final List<Beverage> beverages;

}
