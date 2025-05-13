package sopt.study.testcode.dajin.unit.beverage;

public class Americano implements Beverage {
    @Override
    public int getPrice() {
        return 4000;
    }

    @Override
    public String getName() {
        return "아메리카노";
    }
}
