//Используя Reflection API,
//напишите программу, которая выводит на экран все методы класса String.

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Method[] strMethods = String.class.getMethods();
        for (Method strMethod : strMethods) {
            System.out.println(strMethod);
        }
    }
}