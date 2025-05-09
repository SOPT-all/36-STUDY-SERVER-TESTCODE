package sopt.study.testcode.hyunjin.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt.study.testcode.hyunjin.unit.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {
    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;
}
