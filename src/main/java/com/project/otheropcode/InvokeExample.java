package com.project.otheropcode;

import java.util.ArrayList;
import java.util.List;

public class InvokeExample {
    public static void main(String[] args) {
        // invokevirtual
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Hello");

        // invokeinterface
        List<String> list = arrayList;
        list.size();

        // invokestatic
//        List<String> immutableList = List.of("A", "B", "C");

        // invokespecial
        InvokeExample example = new InvokeExample();
        example.privateMethod();
    }

    private void privateMethod() {
        System.out.println("This is a private method");
    }
}
