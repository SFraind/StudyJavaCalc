/*
Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами.
Данные передаются в одну строку! Решения, в которых каждое число и арифмитеческая операция передаются с новой строки
считаются неверными.
Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.
Калькулятор принимает на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по
величине и могут быть любыми.
Калькулятор умеет работать только с целыми числами.
Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.
При вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение
выбрасывает исключение и завершает свою работу.
Результатом операции деления является целое число, остаток отбрасывается.
Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль.
Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше
единицы, выбрасывается исключение
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private enum RomanNum {
        I(1), IV(4), V(5), IX(9), X(10);

        private int value;

        RomanNum(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static List getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNum e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    private static final String[] romanNumbers = new String[] {
            "I", "II", "III","IV","V","VI","VII","VIII","IX","X"
    };

    private static final String[] arabicNumbers = new String[] {
            "1", "2", "3","4","5","6","7","8","9","10"
    };
    public static void main(String[] args)
    {
        try
        {
            while (true)
            {
                System.out.println("введите число:");
                Scanner scanner = new Scanner(System.in);
                String input = (scanner.nextLine()).replace(" ", "");
                String result = calc(input);
                System.out.println("результат:" + result);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input) throws Exception
    {
        boolean isRomanCalc = false;
        String[] values = input.split("\\+|-|\\*|/");
        checkInputError(values);
        String oneNum = values[0];
        String twoNum = values[1];
        if (isRoman(oneNum) && isRoman(twoNum))
        {
            oneNum = converterToArabic(oneNum);
            twoNum = converterToArabic(twoNum);
            isRomanCalc = true;
        }
        else if ((isArabic(oneNum) && isRoman(twoNum)) || (isRoman(oneNum) && isArabic(twoNum)))
        {
            throw new Exception("нельзя использовать разные системы счисления");
        }

        int one = Integer.parseInt(oneNum);
        int two = Integer.parseInt(twoNum);
        int result = 0;

        if ((one > 10) || (two > 10)) {
            if (arabicNumbers[1].equals("+"))
                result =  one + two;
            else if (arabicNumbers[1].equals("-"))
                result =  one - two;
            else if (arabicNumbers[1].equals("*"))
                result =  one * two;
            else if (arabicNumbers[1].equals("/"))
                result =  one / two;
            else
                throw new Exception("вводимое число не может быть больше 10");
        }

        if (input.contains("+"))
        {
            result = one + two;
        }
        else if (input.contains("-"))
        {
            result = one - two;
        }
        else if (input.contains("/"))
        {
            result = one / two;
        }
        else if (input.contains("*"))
        {
            result = one * two;
        }
        if (isRomanCalc)
        {
            if (result < 0)
            {
                throw new Exception("в римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        }
        return String.valueOf(result);
    }

    private static void checkInputError(String[] values) throws Exception
    {
        if (values.length < 2)
        {
            throw new Exception("строка не является математической операцией");
        }
        if (values.length > 2)
        {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один " +
                    "оператор (+, -, /, *)");
        }
    }

    public static String converterToArabic(String number)
    {
        for (int i = 0; i < romanNumbers.length; i++)
        {
            if (romanNumbers[i].equals(number))
            {
                return arabicNumbers[i];
            }
        }
        return null;
    }

    public static boolean isArabic(String value){
        for (String s: arabicNumbers)
        {
            if (s.equals(value)) return true;
        }
        return false;
    }

    public static boolean isRoman(String value){
        for (String s: romanNumbers)
        {
            if (s.equals(value)) return true;
        }
        return false;
    }

    public static String arabicToRoman(int number) {
        List<RomanNum> romanNumerals = RomanNum.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNum currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}