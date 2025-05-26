package sopt.study.testcode.kyongmin.unit.beverage;

public class Latte implements Beverage {
	private final String name = "라떼";
	private final int price = 4500;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getPrice() {
		return this.price;
	}
}
