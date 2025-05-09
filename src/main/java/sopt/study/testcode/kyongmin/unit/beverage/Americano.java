package sopt.study.testcode.kyongmin.unit.beverage;

public class Americano implements Beverage {
	private final String name = "아메리카노";
	private final int price = 4000;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getPrice() {
		return this.price;
	}
}
