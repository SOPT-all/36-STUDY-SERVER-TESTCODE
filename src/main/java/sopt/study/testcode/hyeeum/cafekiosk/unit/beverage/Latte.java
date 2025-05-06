package sopt.study.testcode.hyeeum.cafekiosk.unit.beverage;

public class Latte implements Beverage{
    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4500;
    }
}
