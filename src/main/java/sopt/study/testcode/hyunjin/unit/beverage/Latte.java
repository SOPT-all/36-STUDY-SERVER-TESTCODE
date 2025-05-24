package sopt.study.testcode.hyunjin.unit.beverage;

import sopt.study.testcode.hyunjin.unit.Beverage;

public class Latte implements Beverage {
    @Override
    public String getName() {
        return "Latte";
    }

    @Override
    public int getPrice() {
        return 4500;
    }
}
