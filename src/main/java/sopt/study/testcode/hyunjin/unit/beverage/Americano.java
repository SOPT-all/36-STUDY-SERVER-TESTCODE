package sopt.study.testcode.hyunjin.unit.beverage;

import sopt.study.testcode.hyunjin.unit.Beverage;

public class Americano implements Beverage {
    @Override
    public String getName() {
        return "Americano";
    }

    @Override
    public int getPrice() {
        return 4000;
    }
}
