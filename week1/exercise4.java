import java.util.Scanner;
import java.util.Arrays;

public class exercise4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();

        int[] dp = new int[k + 1];
        int[] prev = new int[k + 1];
        int[] trace = new int[k + 1];

        for (int i = 0; i <= k; i++) dp[i] = -1;
        dp[0] = 0;

        // DP
        for (int i = 0; i < n; i++) {
            for (int s = k; s >= a[i]; s--) {
                if (dp[s - a[i]] != -1 && dp[s - a[i]] + 1 > dp[s]) {
                    dp[s] = dp[s - a[i]] + 1;
                    prev[s] = i;
                    trace[s] = s - a[i];
                }
            }
        }

        if (dp[k] == -1) {
            System.out.println("Khong co day con");
            return;
        }

        // truy vết (in ngược)
        int s = k;
        int[] res = new int[n];
        int idx = 0;

        while (s > 0) {
            int i = prev[s];
            res[idx++] = a[i];
            s = trace[s];
        }

        // in đúng thứ tự
        for (int i = idx - 1; i >= 0; i--) {
            System.out.print(res[i] + " ");
        }
    }
}