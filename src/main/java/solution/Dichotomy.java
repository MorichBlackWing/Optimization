package solution;

interface Expression {
    double func(double x);
}

public class Dichotomy {

    public static double findMin(double a, double b, double eps, Expression expression) {
        return findMax(a, b, eps, x -> -expression.func(x));
    }

    public static double findMax(double a, double b, double eps, Expression expression) {
        double center;
        double left = a;
        double right = b;
        double f1;
        double f2;
        int iteration = 0;
        do {
            center = (left + right) / 2;
            System.out.printf("%1d:\t[%2$01.10f; %3$01.10f]\teps = %4$01.10f\tx = %5$01.10f\ty = %6$01.10f%n", iteration, left, right, Math.abs(right - left), center, expression.func(center));
            f1 = expression.func(center - eps);
            f2 = expression.func(center + eps);
            if (f1 < f2)
                left = center;
            else
                right = center;
            iteration++;
        } while (Math.abs(right - left) >= eps);
        System.out.printf("Answer:\t[%1$01.10f; %2$01.10f]\tx = %3$01.10f\t", left, right, center);
        return center;
    }
}
