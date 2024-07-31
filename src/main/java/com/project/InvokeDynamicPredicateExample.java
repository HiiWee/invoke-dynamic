package com.project;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import java.util.function.Predicate;

public class InvokeDynamicPredicateExample {
    public static void main(String[] args) throws Throwable {
        // 람다 표현식에 해당하는 메서드 핸들 생성
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType predicateType = MethodType.methodType(boolean.class, List.class);
        MethodHandle target = lookup.findStatic(InvokeDynamicPredicateExample.class, "lambda$main$0", predicateType);

        // 부트스트랩 메서드 정의
        MethodType bootstrapMethodType = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                String.class,
                MethodType.class,
                MethodType.class,
                MethodHandle.class,
                MethodType.class
        );
        MethodHandle bootstrapMethod = lookup.findStatic(LambdaMetafactory.class, "metafactory", bootstrapMethodType);

        // invokedynamic 호출 사이트 생성
        CallSite callSite = (CallSite) bootstrapMethod.invoke(
                lookup,
                "test",
                MethodType.methodType(Predicate.class),
                MethodType.methodType(boolean.class, Object.class),
                target,
                MethodType.methodType(boolean.class, List.class)
        );

        // 생성된 Predicate 인스턴스 획득
        Predicate<List<String>> predicate = (Predicate<List<String>>) callSite.getTarget().invoke();

        // 테스트
        List<String> testList1 = List.of("A", "B", "C");
        List<String> testList2 = List.of("A", "B", "C", "D", "E");

        System.out.println("testList1 (size 3): " + predicate.test(testList1));
        System.out.println("testList2 (size 5): " + predicate.test(testList2));
    }

    // 람다 표현식의 실제 구현
    private static boolean lambda$main$0(List<String> nameList) {
        return nameList.size() > 3;
    }
}
