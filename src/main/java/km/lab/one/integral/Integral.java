package km.lab.one.integral;

/**
 *         Date: 07.05.14
 *         Time: 13:33
 */

/**
   Приближенно вычислить интеграл от функции LN(2+SIN(X)) в про-
   межутке от A до B (где A<B), используя составную формулу пря-
   моугольников для заданного шага H>=100 :
   интеграл от A до B (где A<B) функции приближенно равен
   H*[F(X1)+F(X2)+...+F(XN)], Где H=(B-A)/N, XI=A+I*H-H/2

   http://www.cyberforum.ru/java/thread214272.html
 */
public class Integral {

  public static void main(final String[] args) {

    final double valA = 5.0;
    final double valB = 15.0;
    final double valL = 15.0;
    final double valN = 100.0;
    final double valH = (valB - valA) / valN;

    double result = 0.0;
    for (int i = 0; i < valN; i++) {
      result += calc(valL, valN, calcItemX(valA, valH, i));
    }
    System.out.println("result " + result);
  }

  public static double calcItemX(final double valA, final double valH, final double valIndex) {
    return valA + valIndex*valH - valH / 2;
  }

  public static double calc(final double valL, final double valN, final double x) {
    return valL*valN*(2 + Math.sin(x));
  }



}
