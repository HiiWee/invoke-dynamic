package com.project.example;

import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {

    private int value = 100;

    public void run() {
        // 람다식을 사용하여 Predicate 생성
        Predicate<List<String>> predicate = nameList -> {
            return nameList.size() > 3;
        };

        // 테스트
        List<String> testList1 = List.of("A", "B", "C");
        List<String> testList2 = List.of("A", "B", "C", "D", "E");

        System.out.println(predicate.test(testList1));
        System.out.println(predicate.test(testList2));
    }
}
