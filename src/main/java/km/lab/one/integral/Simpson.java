package km.lab.one.integral;

/**
 * @author phpusr
 *         Date: 06.05.14
 *         Time: 13:50
 */

/**
 * Нахождение определенного интеграла методом Симпсона
 * http://hashcode.ru/questions/124117
 *
 * Еще реализации
 * http://www.cyberforum.ru/java/thread214272.html
 * http://volfar.com/%D0%B8%D1%81%D1%87%D0%B8%D1%81%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F-%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D0%BB%D0%B0-%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%BE%D0%BC-%D0%BF%D1%80%D1%8F%D0%BC%D0%BE%D1%83/
 */
public class Simpson {

    private static double f(double x){
        return Math.pow(x*Math.log(x), 2);
    }

    public static double integral(int n, double a, double b){
        double sum=0,sum2=0;
        double[] x=new double[n];
        double h=(b-a)/n;
        for(int i=0;i<n;i++){
            x[i]=a+i*h;
        }
        for(int i=1;i<n;i++){
            sum+=f(x[i]);
            sum2+=f((x[i-1]+x[i])/2);
        }
        return h/6*(f(a)+f(b)+2*sum+4*(sum2+b));
    }

    public static void main(String[] args) {
        Simpson s=new Simpson();
        System.out.println(integral(52, 1, Math.E));
    }
}
