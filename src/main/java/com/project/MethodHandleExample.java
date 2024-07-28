package com.project;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodHandleExample {

    public static void main(String[] args) throws Throwable {

        // 0. 메소드 접근을 위한 Lookup 객체 만들기 (public, protected and private 메소드가 나뉜다.)
        // public method에 대한 액세스를 제공하는 lookup을 만듦
        Lookup publicLookup = MethodHandles.publicLookup();

        // private, protected method에 대한 액세스를 제공하는 lookup을 만듦
        Lookup otherLookup = MethodHandles.lookup();

        // Object[]를 인자로 주었을때 List를 반환하는 MethodType 생성
//        MethodType methodType = MethodType.methodType(List.class, Object[].class);



        // 1. MethodHandle을 만들기
        // 메소드 유형을 정의한 후 MethodHandle을 생성하려면 원본 클래스와 메소드 이름을 제공하는 lookup 또는 publicLookup 객체를 통해 찾아야 합니다.
        // 1-1. 일반 메소드에 대한 MethodHandle 생성하기 -> 일반 메소드는 bytecode에서 메서드를 호출하는 opCode인 invokevirtual을 사용함
        MethodType basicMethodType = MethodType.methodType(String.class, String.class);
        MethodHandle concatMethodHandle = publicLookup.findVirtual(String.class, "concat", basicMethodType);

        // 1-2. 정적 메소드에 대한 MethodHandle 생성하기 -> 정적 메소드는 bytecode에서 메소드를 호출하는 opCode인 invokestatic을 사용함
        MethodType staticMethodType = MethodType.methodType(List.class, Object[].class);
        MethodHandle asListMethodHandle = publicLookup.findStatic(Arrays.class, "asList", staticMethodType);

        // 1-3. 생성자에 대한 MethodHandle 생성하기 -> 생성자는 bytecode에서 메소드 호출 opcode인 invokespecial을 사용함
        MethodType constructorMethodType = MethodType.methodType(void.class, String.class);
        MethodHandle constructorMethodHandle = publicLookup.findConstructor(String.class, constructorMethodType);

        // 1-4. 필드에 대한 Method 핸들 생성하기
        MethodHandle getterMethodHandle = otherLookup.findGetter(Book.class, "title", String.class);

        // 1-5. private 메소드에 대한 MethodHandle
        // formatBook() 메소드와 똑같이 동작하는 메소드 핸들을 만들 수 있다.
        Method formatBookMethod = Book.class.getDeclaredMethod("formatBook");
        formatBookMethod.setAccessible(true);
        MethodHandle formatBookMethodHandle = otherLookup.unreflect(formatBookMethod);




        // 2. MethodHandle 사용하기: 3가지 메소드 핸들 실행 방법
        // 2-1. invoke(): 메소드의 인자의 갯수를 강제한다. 인자 및 반환 유형의 캐스팅 및 boxing/unboxing을 수행을 허용
        MethodType mt1 = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMethodHandle = publicLookup.findVirtual(String.class, "replace", mt1);

        String output = (String) replaceMethodHandle.invoke("jovo", Character.valueOf('o'), 'a');// auto UnBoxing을 알아서 해준다.
        System.out.println(output);

        // 2-2. invokeWithArguments(): 가장 제한이 적은 메소드, 인자가 있는 메소드 핸들을 호출합니다.: 인자와 반환 유형의 캐스팅 및 boxing / unboxing 외에도 변수 arity 호출을 허용
        MethodType mt2 = MethodType.methodType(List.class, Object[].class);
        MethodHandle asList = publicLookup.findStatic(Arrays.class, "asList", mt2);

        List<Integer> list = (List<Integer>) asList.invokeWithArguments(1, 2);
        System.out.println(list);

        // 2-3 invokeExact(): MethodHandle(인자와 인자들의 타입)을 실행하는 방식을 보다 제한할 수 있음, 제공된 클래스에 대한 캐스팅을 제공하지 않고 고정된 수의 인수가 필요함
        MethodType mt3 = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMethodHandle = otherLookup.findStatic(Integer.class, "sum", mt3);

        int sum = (int) sumMethodHandle.invokeExact(1, 11); // 메서드 핸들의 타입과 정확히 일치해야 합니다. 인자나 반환값에 대한 변환은 허용되지 않습니다.
        // int sum = (int) sumMethodHandle.invokeExact(Integer.valueOf(1), 11); // 메서드 핸들의 타입과 정확히 일치해야 합니다. 인자나 반환값에 대한 변환은 허용되지 않습니다. -> boxing, unboxing도 허용 X

        // 2-4 배열에서도 동작할 수 있다.
        MethodType mt4 = MethodType.methodType(boolean.class, Object.class);
        MethodHandle equals = publicLookup.findVirtual(String.class, "equals", mt4);

        MethodHandle methodHandle = equals.asSpreader(Object[].class, 2);

        boolean isSame = (boolean) methodHandle.invoke(new Object[]{"java", "java"});
        System.out.println(isSame);
    }

    static class Book {

        private String id;
        private String title;

        public Book(final String id, final String title) {
            this.id = id;
            this.title = title;
        }

        private String formatBook() {
            return id + " > " + title;
        }
    }
}
