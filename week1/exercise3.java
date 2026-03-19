import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class exercise3 {
    static class Point {
        private int x;
        private int y;

        public Point (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Point p){
            int dx = this.x - p.x;
            int dy = this.y - p.y;
            return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        }
    }

    static class GrahamScan {
        static Point p0; //Anchor Point

        static int orientation(Point p1, Point p2, Point p3) {
            int dx1 = p2.x - p1.x;
            int dy1 = p2.y - p1.y;
        
            int dx2 = p3.x - p2.x;
            int dy2 = p3.y - p2.y;

            int value = (dy1 * dx2) - (dy2 * dx1);

            if (value == 0) return 0; 
            return (value > 0) ? 1 : 2; //Counter-ClockWise
        }

        static int comparator(Point p1, Point p2) {
            // Luôn dùng p0 làm gốc để xét hướng quay của p1 và p2
            int order = orientation(p0, p1, p2);

            if (order == 0) { // Thẳng hàng
                if (p0.distance(p1) == p0.distance(p2)) return 0;
                return (p0.distance(p1) < p0.distance(p2)) ? -1 : 1;
            }

            // Nếu là Ngược chiều KĐH (CCW = 2), p1 "nhỏ hơn" p2 -> trả về -1
            // Nếu là Cùng chiều KĐH (CW = 1), p1 "lớn hơn" p2 -> trả về 1
            return (order == 2) ? -1 : 1;
        }

        static void findConvexHull(Point[] points) {
            int n = points.length;
            if (n < 3) return;

            //Find Anchor Point
            int yMin = 0;
            for (int i = 1; i < n; i++) {
                if ((points[i].y < points[yMin].y) || (points[i].y == points[yMin].y && points[i].x < points[yMin].x)) {
                    yMin = i;
                }
            }

            //Swap Anchor Point to first element in the Array
            Point temp = points[0];
            points[0] = points[yMin];
            points[yMin] = temp;
            p0 = points[0]; 

            // Sort the remaining points
            Arrays.sort(points, 1, n, (p1, p2) -> comparator(p1, p2));

            // Initialize the Stack
            Stack<Point> stack = new Stack<>();
            stack.push(points[0]);
            stack.push(points[1]);
            
            for (int i = 2; i < n; i++) {
                while (stack.size() > 1 && orientation(stack.get(stack.size() - 2), stack.peek(), points[i]) != 2) {
                    stack.pop();
                }
                stack.push(points[i]);
            }

            while (!stack.isEmpty()) {
                Point p = stack.pop();
                System.out.println(p.x + " " + p.y);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (sc.hasNextInt()) {
            int n = sc.nextInt();
            Point[] points = new Point[n];
            
            for (int i = 0; i < n; i++) {
                points[i] = new Point(sc.nextInt(), sc.nextInt());
            }

            GrahamScan.findConvexHull(points);
        }
        
        sc.close();
    }
}