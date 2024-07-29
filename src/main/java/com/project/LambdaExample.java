package com.project;

import java.util.stream.Stream;

public class LambdaExample {

    public static void main(String[] args) {
        long lengthyColors = Stream.of("Red", "Green", "Blue")
                .filter(c -> c.length() > 3)
                .count();

        System.out.println("lengthyColors = " + lengthyColors);
    }
}
