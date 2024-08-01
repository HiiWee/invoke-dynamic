package com.project.example;

import java.util.List;
import java.util.function.Predicate;

public class LambdaPredicateTest {

    public static void main(String[] args) {
        // 람다식을 사용하여 Predicate 생성
        Predicate<List<String>> predicate = nameList -> nameList.size() > 3;

        // 테스트
        List<String> testList1 = List.of("A", "B", "C");
        List<String> testList2 = List.of("A", "B", "C", "D", "E");

        // 결과 출력
        boolean result1 = predicate.test(testList1);
        boolean result2 = predicate.test(testList2);
        System.out.println(result1);
        System.out.println(result2);
    }
}
