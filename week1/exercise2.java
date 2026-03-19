import java.util.Scanner;

public class exercise2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long totalPoints = 10000000;
        
        long pointsInsideCircle = 0; 

        for (long i = 0; i < totalPoints; i++) {
            double x = Math.random();
            double y = Math.random();
            
            if (x * x + y * y <= 1) {
                ++pointsInsideCircle;
            }
        }
        
        double approximatePI = 4.0 * pointsInsideCircle / totalPoints;
        
        System.out.println("The approximate value of PI is: " + approximatePI);
    }
}