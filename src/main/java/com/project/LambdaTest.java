package com.project;

import java.util.stream.Stream;

public class LambdaTest {

    private int value = 100;

    public int run() {
         Stream.of("Red", "Green", "Blue")
                .forEach(c -> {
//                    this.value = 5;
                });

         return value;
    }
}
