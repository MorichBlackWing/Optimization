package solution;

import java.util.ArrayList;


public class Main {
    final static String FORMAT = "y = %1$01.10f\n%n";
    final static double E = Math.pow(10, -4);
    static ArrayList<Expression> expressions = new ArrayList<>(5);

    public static void main(String[] args) {
        System.out.println("Поиск минимального значения методом дихотомии");
        System.out.printf("Точность: %1f\n", E);
        System.out.println("----- Функция 1: sin(x) - 2 * x^2 + 1/2 -----");
        //Одна точка максимума в x = 0.243, y = 0.623
        expressions.add(x -> Math.sin(x) - (2 * Math.pow(x, 2)) + 0.5);
        solve(0, -1, 1, true);
        System.out.println("----- Функция 2: sqrt(1 - x^2) - e^x + 0.1 -----");
        //Весь график в промежутке [-1; 1] и Одна точка максимума в x = -0.513, y = 0.36
        expressions.add(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1);
        solve(1, -1, 1, true);
        System.out.println("----- Функция 3: x^6 - 5 * x^3 + 3x + 2 -----");
        //Две точки минимума и одна точка максимума
        //Первая точка минимума на промежутке [-1; 0], x = -0.44, y = 1.113
        //Вторая точка минимума на промежутке [1; 1.5], x= 1.302, y = -0.258
        //Точка максимума на промежутке [0; 1], x =  0.456, y = 2.903
        expressions.add(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) + 3 * x + 2);
        solve(2, -1, 0, false);
        solve(2, 1, 1.5, false);
        solve(2, 0, 1, true);
        System.out.println("----- Функция 4: ln(3x/2) + 5/(1 + x^2) -----");
        //Точка максимума и точка минимума
        //Точка минимума на промежутке [2; 3], x = 2.806, y = 2.001
        //Точка максимума на промежутке [0; 1], x = 0.356, y = 3.81
        expressions.add(x -> Math.log(3 * x / 2) + (5 / (1 + Math.pow(x, 2))));
        solve(3, 2, 3, false);
        solve(3, 0, 1, true);
        System.out.println("----- Функция 5: 3^x - 5x + 1 -----");
        //Точка минимума на промежутке [0; 2], x = 1.379, y = -1.346
        expressions.add(x -> Math.pow(3, x) - (5 * x) + 1);
        solve(4, 0, 2, false);
    }

    public static void solve(int index, double start, double end, boolean max) {
        if (max)
            System.out.printf(FORMAT, expressions.get(index).func(Dichotomy.findMax(start, end, E, expressions.get(index))));
        else
            System.out.printf(FORMAT, expressions.get(index).func(Dichotomy.findMin(start, end, E, expressions.get(index))));
    }

}