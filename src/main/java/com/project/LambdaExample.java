package com.project;

public class LambdaExample {

    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println("Hello World!");
        runnable.run();
    }
}
