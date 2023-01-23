import java.util.ArrayList;
import java.util.Scanner;

public class ALG3B {

    static int dp[][][];
    static ArrayList<Integer> buyday, sellday;

    public static void main(String[] args) {
        Scanner input = null;
        try {
            // input = new Scanner(System.in); // Create a Scanner object
            // System.out.println("Enter m and n : ");
            // int m = input.nextInt();
            // int n = input.nextInt();

            // // Declartion of the matrix based on user input
            // int stockPrices[][] = new int[m][n];

            // // Read the matrix values
            // System.out.println("Enter the elements of the matrix : ");
            // for (int i = 0; i < m; i++)
            //     for (int j = 0; j < n; j++)
            //         stockPrices[i][j] = input.nextInt();

            // // Display the elements of the matrix
            // System.out.println("Elements of the matrix are : ");
            // for (int i = 0; i < m; i++) {
            //     for (int j = 0; j < n; j++)
            //         System.out.print(stockPrices[i][j] + "  ");
            //     System.out.println();
            // }
            //maxProfit(stockPrices);
            input = new Scanner(System.in);
            int m = 100;
            int n = 1000;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfit(stockPrices);
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }
    }

    public static void maxProfit(int[][] stockPrices) {
        int m = stockPrices.length;
        Result result = new Result();
        result.maxProfit = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            int k = 1;
            Result resultEach = maxProfitEach(k, stockPrices[i]);
            resultEach.indexShare = i;
            if (resultEach.maxProfit > result.maxProfit) {
                result = new Result(resultEach);
            }
        }
        System.out.println("Max profit :" + result.maxProfit);
        result.indexShare = result.indexShare + 1;
        result.buyIndexDay = result.buyIndexDay + 1;
        result.sellIndexDay = result.sellIndexDay + 1;
        System.out.println(result.indexShare + " " + result.buyIndexDay + " " + result.sellIndexDay);
    }

    public static Result maxProfitEach(int k, int[] stockPricesEach) {
        int n = stockPricesEach.length;

        // Base case
        if (n <= 0 || k <= 0) {
            return new Result();
        }

        // dp[day][used_k][ishold] = balance
        // ishold: 0 nothold, 1 hold
        dp = new int[n][k + 1][2];
        buyday = new ArrayList<>();
        sellday = new ArrayList<>();

        // initialize the array with -infinity
        // we use -1e9 here to prevent overflow
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j][0] = -1000000000;
                dp[i][j][1] = -1000000000;
            }
        }

        // set starting value
        dp[0][0][0] = 0;
        dp[0][1][1] = -stockPricesEach[0];

        // fill the dp array
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                // transition equation
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + stockPricesEach[i]);
                // you can't hold stock without any transaction
                if (j > 0) {
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - stockPricesEach[i]);
                }
            }
        }

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, dp[n - 1][j][0]);
        }

        int maxk = 0;
        for (int i = 0; i <= k; i++) {
            if (res == dp[n - 1][i][0]) {
                maxk = i;
            }
        }
        Result resultantValue = new Result();
        findTransactionDays(stockPricesEach, n - 1, maxk, 0);
        for (int i = 0; i < buyday.size(); i++) {
            resultantValue.buyIndexDay = buyday.get(i);
            resultantValue.sellIndexDay = sellday.get(i);
        }
        resultantValue.maxProfit = res;
        return resultantValue;
    }

    public static void findTransactionDays(int[] prices, int day, int k, int holdstate) {
        if (day == 0)
            return;

        if (holdstate == 0) {

            // dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);

            if (dp[day][k][0] == dp[day - 1][k][1] + prices[day]) {
                sellday.add(day);
                findTransactionDays(prices, day - 1, k, 1);
            } else {
                findTransactionDays(prices, day - 1, k, 0);
            }
        } else if (k > 0) {

            // dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);

            if (dp[day][k][1] == dp[day - 1][k - 1][0] - prices[day]) {
                buyday.add(day);
                findTransactionDays(prices, day - 1, k - 1, 0);
            } else {
                if (day - 1 == 0) {
                    buyday.add(day);
                }
                findTransactionDays(prices, day - 1, k, 1);
            }
        }

    }

}