//Напишите программу, которая использует Stream API для обработки списка чисел.
//Программа должна вывести на экран среднее значение всех четных чисел в списке.



import java.util.Arrays;
import java.util.List;

public class Task_1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3);
        String avg = String.valueOf(list.stream().filter(num -> num % 2 == 0).mapToInt(num -> num).average().stream().average().getAsDouble());
        System.out.printf("Average value of even numbers for list %s is %s", list, avg);
    }
}
