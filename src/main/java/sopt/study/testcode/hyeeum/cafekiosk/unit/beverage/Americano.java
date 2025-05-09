package sopt.study.testcode.hyeeum.cafekiosk.unit.beverage;

public class Americano implements Beverage{
    @Override
    public String getName() {
        return "아메리카노";
    }

    @Override
    public int getPrice() {
        return 4000;
    }
}
