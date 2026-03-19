import java.util.Scanner;

public class exercise1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the radius of the circle r = ");
        double r = sc.nextDouble(); 
        double step = r / 20000, rSquared = r * r;
        long cellCount = 0;

        for (double i = 0; i < r; i += step) {
            for (double j = 0; j < r; j += step) {
                if (i * i + j * j <= rSquared) {
                    ++cellCount;
                }
            }
        }

        double approximateArea = cellCount * step * step * 4;
        System.out.println("The approximate area of the circle is: " + approximateArea);
        
    }


}
