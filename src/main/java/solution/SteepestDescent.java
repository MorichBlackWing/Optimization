package solution;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SteepestDescent {

    public static double minimize(Function<double[], Double> f, double[] x0, double alpha, double eps, boolean output) {
        int n = x0.length;
        double[] x = Arrays.copyOf(x0, n);
        double[] xPrev;
        double[] grad = new double[n];
        double step = 0.00001;
        int iteration = 0, i;
        double approx, fx, old;
        do {
            iteration++;
            fx = f.apply(x);
            xPrev = Arrays.copyOf(x, n);
            for (i = 0; i < n; i++) {
                old = x[i];
                x[i] = old + step;
                grad[i] = (f.apply(x) - fx) / step;
                x[i] = old;
            }
            for (i = 0; i < n; i++)
                x[i] = x[i] - alpha * grad[i];
            approx = maxNorm(x, xPrev);
            if (output) {
                System.out.printf("[%1$d]\tX: { ", iteration);
                Arrays.stream(x).forEach(el -> System.out.printf("%1$01.10f ", el));
                System.out.printf("}\tf: %1$01.16f\tApproximation: %2$01.16f\n", f.apply(x), approx);
            }
            if (iteration >= 200000)
                break;
        } while (approx >= eps);

        return f.apply(x);
    }
    public static double maxNorm(double[] x, double[] y) {
        return Arrays.stream(Arrays.stream(x).map(Math::abs).toArray()).max().getAsDouble() -
                Arrays.stream(Arrays.stream(y).map(Math::abs).toArray()).max().getAsDouble();
    }

    public static void outToFile(){
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("output.txt", false), true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.setOut(out);
    }

    public static void main(String[] args) {
        outToFile();
        // Инициализация
        double alpha = 0.01;
        double eps = Math.pow(10, -10);
        //double[] x0 = {0, 0};
        List<double[]> x0 = new ArrayList<double[]>(8);
        List<Function<double[], Double>> functionList = new ArrayList<Function<double[], Double>>(8);
        List<String> taskList = new ArrayList<>(8);
        // --- 1 ---
        functionList.add(x -> x[0] * x[0] + x[1] * x[1] - 2 * x[0] - 4 * x[1] + 5);
        taskList.add("x1^2 + x2^2 - 2*x1 - 4*x2 + 5");
        x0.add(new double[]{0, 0});
        // --- 2 ---
        functionList.add(x -> 2 * x[0] * x[0] - 2 * x[0] * x[1] + x[1] * x[1] + 2 * x[0] - 2 * x[1]);
        taskList.add("2*x1^2 - 2*x1*x2 + x2^2 + 2*x1 - 2*x2");
        x0.add(new double[]{0, 0});
        // --- 3 ---
        functionList.add(x -> x[0] * x[0] + 2 * x[1] * x[1] + x[2] * x[2] + 2 * x[0] * x[1] - x[0] + 2 * x[2]);
        taskList.add("x1^2 + 2*x2^2 + x3^2 + 2*x1*x2 - x1 + 2*x3");
        x0.add(new double[]{0, 0, 0});
        // --- 4 ---
        functionList.add(x -> x[0] * x[0] + x[1] * x[1] + x[2] * x[2] - x[0] * x[1] + x[0] - 2 * x[2]);
        taskList.add("x1^2 + x2^2 + x3^2 - x1*x2 + x1 - 2*x3");
        x0.add(new double[]{0, 0, 0});
        // --- 5 ---
        functionList.add(x -> x[0]);
        taskList.add("Skip");
        x0.add(new double[]{0, 0, 0, 0, 0, 0});
        // --- 6 ---
        functionList.add(x -> x[0] * x[0] + x[1] * x[1] - 0.2 * x[0] * x[1] + 2.2 * x[0] + 2.2 * x[1] + 2.2);
        taskList.add("x1^2 + x2^2 - 0.2*x1*x2 + 2.2*x1 + 2.2*x2 + 2.2");
        x0.add(new double[]{0, 0});
        // --- 7 ---
        functionList.add(x -> 5 * x[0] * x[0] + 4.075 * x[1] * x[1] - 9 * x[0] * x[1] + x[0] + 2);
        taskList.add("5*x1^2 + 4.075*x2^2 - 9*x1*x2 + x1 + 2");
        x0.add(new double[]{0, 0});
        // --- 8 ---
        double k = 0;
        functionList.add(x -> Math.pow(x[0] + 10 * x[1], 2) + 5 * Math.pow(x[2] - x[3] + 2 * k, 2) + Math.pow(x[1] - 2 * x[2], 4) + 100 * Math.pow(x[0] - x[3] + 11 * k, 4));
        taskList.add("(x1 + 10*x2)^2 + 5*(x3 - x4 + 2*k)^2 + (x2 - 2*x3)^4 + 100*(x1 - x4 + 11*k)^4");
        x0.add(new double[]{0, 0, 0, 0});
        // Есть вариант того, что расчёт долгий за счёт нормы. Если её поменять кол-во итераций может снизиться, да и время повысится.
        for (int i = 0; i < 8; i++) {
            if (i != 4) {
                System.out.printf("--- Solution %d: %s ---\n", i + 1, taskList.get(i));
                System.out.printf("Answer: %1$01.20f\n", minimize(functionList.get(i), x0.get(i), alpha, eps, true));
            }
        }
    }

}