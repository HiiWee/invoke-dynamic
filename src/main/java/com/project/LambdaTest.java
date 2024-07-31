package com.project;

import java.util.stream.Stream;

public class LambdaTest {

    private int value = 100;

    public void run() {
        Stream.of("Red", "Green", "Blue")
                .anyMatch(color -> {
                    this.value = 10;
                    return color.equals("Green");
                });
    }
}
